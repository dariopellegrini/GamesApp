package com.dariopellegrini.gamesapp.rows

import android.view.View
import com.bumptech.glide.Glide
import com.dariopellegrini.declarativerecycler.Row
import com.dariopellegrini.gamesapp.R
import com.dariopellegrini.gamesapp.model.Game
import kotlinx.android.synthetic.main.row_game.view.*

class GameRow(val game: Game): Row {
    override val layoutID = R.layout.row_game

    override val configuration: ((View, Int) -> Unit) = {
        itemView, _ ->

        Glide.with(itemView).load(game.user?.image?.url).into(itemView.userImageView)
        itemView.userTextView.text = "${game.user?.name} ${game.user?.surname}"

        Glide.with(itemView).load(game.image?.url).into(itemView.gameImageView)
        itemView.titleTextView.text = game.name
        itemView.platformTextView.text = game.platform
        itemView.captionTextView.text = game.caption
    }
}