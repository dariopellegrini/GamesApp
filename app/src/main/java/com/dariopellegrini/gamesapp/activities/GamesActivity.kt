package com.dariopellegrini.gamesapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.dariopellegrini.declarativerecycler.BasicRow
import com.dariopellegrini.declarativerecycler.RecyclerManager
import com.dariopellegrini.gamesapp.R
import com.dariopellegrini.gamesapp.network.Network
import com.dariopellegrini.gamesapp.repository.Repository
import com.dariopellegrini.gamesapp.rows.GameRow
import com.dariopellegrini.gamesapp.utilities.alert
import com.dariopellegrini.gamesapp.utilities.positiveButton
import kotlinx.android.synthetic.main.activity_games.*
import kotlinx.android.synthetic.main.row_game.view.*
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

        swipeRefreshLayout.setOnRefreshListener {
            load()
        }

        load()
    }

    fun load() = CoroutineScope(Dispatchers.Main).launch {
        try {
            swipeRefreshLayout.isRefreshing = true

            val games = Repository.games()

            recycler.clear()
            recycler.push(
                    games.map { game ->
                        GameRow(game)
                    }
            )
            recycler.reload()

            swipeRefreshLayout.isRefreshing = false
        } catch (e: Exception) {
            swipeRefreshLayout.isRefreshing = false
            alert {
                setTitle("Error")
                setMessage("Error loading games")
                positiveButton("OK")
            }
        }
    }
}