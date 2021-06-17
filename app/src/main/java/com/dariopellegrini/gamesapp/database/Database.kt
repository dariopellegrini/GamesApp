package com.dariopellegrini.gamesapp.database

import com.dariopellegrini.gamesapp.model.Game
import com.dariopellegrini.gamesapp.model.User
import com.dariopellegrini.storagedone.*

class Database {
    private val database = StorageDoneDatabase("gamesapp")

    suspend fun saveUser(user: User) {
        database.suspending.insertOrUpdate(user)
    }

    suspend fun readUser(): User? {
        return database.suspending.get<User>().firstOrNull()
    }

    suspend fun saveGames(games: List<Game>) {
        database.suspending.insertOrUpdate(games)
    }

    suspend fun getGames(): List<Game> {
        return database.suspending.get<Game>()
    }

    suspend fun deleteUsers() {
        database.suspending.delete<User>()
    }
}