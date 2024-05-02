package com.example.auth.Repo

import com.example.auth.Endpoints

class AuthRepository(private val endpoints: Endpoints) {
    suspend fun register(username: String, email: String, password: String) =
        endpoints.register(username, email, password)
    suspend fun login(email: String, password: String) = endpoints.login(email, password)
}