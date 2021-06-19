package com.dariopellegrini.gamesapp.network

import com.dariopellegrini.gamesapp.model.Game
import com.dariopellegrini.gamesapp.model.User
import com.dariopellegrini.spike.mapping.mappingThrowable
import com.dariopellegrini.spike.network.SpikeMethod
import com.dariopellegrini.spike.request

class Network {
    val gamesBaseURL = "https://gamesapp.dariopellegrini.com/api/v1/"

    suspend fun login(username: String, password: String): User {
        val result = request<User> {
            baseURL = gamesBaseURL
            path = "users/auth/login"
            method = SpikeMethod.POST
            parameters = mapOf("username" to username, "password" to password)
        }
        val user = result.mappingThrowable()
        val token = result.headers?.get("Authorization") ?: throw Exception("Non autorizzato")
        user.token = token
        return user
    }

    suspend fun signUp(username: String,
                       password: String,
                       name: String,
                       surname: String,
                       nickname: String): User {
        val result = request<User> {
            baseURL = gamesBaseURL
            path = "users"
            method = SpikeMethod.POST
            parameters = mapOf("username" to username,
                "password" to password,
                "name" to name,
                "surname" to surname,
                "nickname" to nickname)
        }
        val user = result.mappingThrowable()
        val token = result.headers?.get("Authorization") ?: throw Exception("Non autorizzato")
        user.token = token
        return user
    }

    suspend fun signUpShort(username: String,
                       password: String,
                       name: String,
                       surname: String,
                       nickname: String) = request<User> {
        baseURL = gamesBaseURL
        path = "users"
        method = SpikeMethod.POST
        parameters = mapOf("username" to username,
            "password" to password,
            "name" to name,
            "surname" to surname,
            "nickname" to nickname)
    }.let {
        it.mappingThrowable().apply {
            token = it.headers?.get("Authorization") ?: throw Exception("Non autorizzato")
        }
    }

    suspend fun getGames(token: String): List<Game> {
        val result = request<List<Game>> {
            baseURL = gamesBaseURL
            path = "games?_sort=dateCreated"
            method = SpikeMethod.GET
            headers = mapOf("Authorization" to "Bearer $token")
        }
        val games = result.mappingThrowable()
        return games
    }

    suspend fun getGamesShort(token: String) = request<List<Game>> {
        baseURL = gamesBaseURL
        path = "games"
        method = SpikeMethod.GET
        headers = mapOf("Authorization" to token)
    }.mappingThrowable()
}