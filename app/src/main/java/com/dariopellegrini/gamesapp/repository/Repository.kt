package com.dariopellegrini.gamesapp.repository

import com.dariopellegrini.gamesapp.database.Database
import com.dariopellegrini.gamesapp.model.Game
import com.dariopellegrini.gamesapp.model.User
import com.dariopellegrini.gamesapp.network.Network

object Repository {
    val network = Network()
    val database = Database()

    suspend fun login(username: String, password: String): User {
        val user = network.login(username, password)
        database.saveUser(user)
        return user
    }

    suspend fun games(): List<Game> {
        try {
            val currentUser = database.readUser()
            val token = currentUser?.token ?: throw Exception("Non autorizzato")
            val games = network.getGames(token)

            database.saveGames(games)
            return games
        } catch (e: Exception) {

            val games = database.getGames()
            if (games.isNotEmpty()) {
                return games
            } else {
                throw e
            }
        }
    }

    suspend fun loggedUserExists(): Boolean {
        return database.readUser()?.token != null
    }

    suspend fun deleteUser() {
        database.deleteUsers()
    }
}