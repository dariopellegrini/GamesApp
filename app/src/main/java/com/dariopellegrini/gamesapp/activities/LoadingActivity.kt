package com.dariopellegrini.gamesapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dariopellegrini.gamesapp.R
import com.dariopellegrini.gamesapp.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        load()
    }

    private fun load() = CoroutineScope(Dispatchers.Main).launch {
        try {
            Repository.deleteUser()

            val alreadyLogged = Repository.loggedUserExists()
            if (alreadyLogged) {
                // Go to games
                Log.i("LoadingActivity", "Games")
                val intent = Intent(this@LoadingActivity, GamesActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            } else {
                // Go to login
                Log.i("LoadingActivity", "Login")
                val intent = Intent(this@LoadingActivity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        } catch (e: Exception) {
            Log.i("LoadingActivity", "$e")
        }
    }
}