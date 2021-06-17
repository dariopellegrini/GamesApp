package com.dariopellegrini.gamesapp.model

import com.dariopellegrini.storagedone.PrimaryKey

data class User(val _id: String,
                val username: String?,
                val name: String?,
                val surname: String?,
                val nickname: String?,
                val image: Image?): PrimaryKey {
    var token: String? = null

    override fun primaryKey() = "_id"
}