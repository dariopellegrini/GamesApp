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
        val currentUser = database.readUser()
        val token = currentUser?.token ?: throw Exception("Non autorizzato")
        val games = network.getGames(token)
        return games
    }

    suspend fun loggedUserExists(): Boolean {
        return database.readUser()?.token != null
    }

    suspend fun deleteUser() {
        database.deleteUsers()
    }
}