package com.dariopellegrini.gamesapp.model

import com.dariopellegrini.storagedone.PrimaryKey

data class Game(val _id: String,
                val name: String?,
                val players: Int?,
                val platform: String?,
                val caption: String?,
                val image: Image?,
                val user: User?): PrimaryKey {
    override fun primaryKey() = "_id"
}