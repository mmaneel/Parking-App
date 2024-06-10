package com.example.auth.Repo

import com.example.auth.ERROR_MESSAGE
import com.example.auth.Retrofit.Endpoints
import com.example.auth.Retrofit.ResultState
import kotlinx.coroutines.flow.flow

class AuthRepository(private val endpoints: Endpoints) {
    suspend fun register(username: String, email: String, password: String) = flow {
        emit(ResultState.Loading)
        try {
            endpoints.register(username, email, password)
            emit(ResultState.Success(true))
        }
        catch (e:Exception) {
            emit(ResultState.Error(ERROR_MESSAGE))
        }
    }

    suspend fun login(email: String, password: String) = flow {
        emit(ResultState.Loading)
        try {
            val response =  endpoints.login(email, password)
            emit(ResultState.Success(response))
        }
        catch (e:Exception) {
            emit(ResultState.Error(ERROR_MESSAGE))
        }
    }
    suspend fun checkEmail(email: Map<String, String>) = endpoints.checkEmail(email)



}