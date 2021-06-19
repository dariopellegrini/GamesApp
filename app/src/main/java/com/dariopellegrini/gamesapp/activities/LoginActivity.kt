package com.dariopellegrini.gamesapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.dariopellegrini.gamesapp.R
import com.dariopellegrini.gamesapp.exceptions.MissingPasswordException
import com.dariopellegrini.gamesapp.exceptions.MissingUsernameException
import com.dariopellegrini.gamesapp.repository.Repository
import com.dariopellegrini.gamesapp.utilities.alert
import com.dariopellegrini.gamesapp.utilities.positiveButton
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener {
            performLogin()
        }
    }

    fun performLogin() = CoroutineScope(Dispatchers.Main).launch {
        try {
            val username = usernameEditText.text.toString()
            if (username.isNullOrBlank()) throw MissingUsernameException()

            val password = passwordEditText.text.toString()
            if (password.isNullOrBlank()) throw MissingPasswordException()

            progressBar.visibility = View.VISIBLE

            val user = Repository.login(username, password)

            // Go to games
            val intent = Intent(this@LoginActivity, GamesActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)

            progressBar.visibility = View.INVISIBLE
        } catch (e: MissingUsernameException) {
            usernameEditText.error = "Missing username"
        } catch (e: MissingPasswordException) {
            passwordEditText.error = "Missing password"
        } catch (e: Exception) {
            Log.e("Login", "$e")
            progressBar.visibility = View.INVISIBLE

            alert {
                setTitle("Error")
                setMessage("Login error")
                positiveButton("OK")
            }
        }
    }
}