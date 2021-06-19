package com.dariopellegrini.gamesapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.dariopellegrini.declarativerecycler.RecyclerManager
import com.dariopellegrini.gamesapp.R
import com.dariopellegrini.gamesapp.repository.Repository
import com.dariopellegrini.gamesapp.rows.GameRow
import com.dariopellegrini.gamesapp.utilities.alert
import com.dariopellegrini.gamesapp.utilities.positiveButton
import kotlinx.android.synthetic.main.activity_games.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GamesActivity : AppCompatActivity() {
    val recycler by lazy {
        RecyclerManager(recyclerView, LinearLayoutManager(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

        load()
    }

    fun load() = CoroutineScope(Dispatchers.Main).launch {
        try {
            val games = Repository.games()
            recycler.push(
                    games.map {
                        GameRow(it)
                    }
            )
            recycler.reload()
        } catch (e: Exception) {
            Log.e("Games", "$e")
            alert {
                setTitle("Error")
                setMessage("Error loading games")
                positiveButton("OK")
            }
        }
    }
}