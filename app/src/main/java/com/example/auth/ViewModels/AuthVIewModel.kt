package com.example.auth.ViewModels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.auth.AuthManager
import com.example.auth.Repo.AuthRepository
import com.example.auth.Retrofit.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject

class AuthVIewModel(private val authRepository: AuthRepository ) :BaseModel(){

    private val loggedIn = mutableStateOf(false)
    fun login(email: String, password: String, context: Context, onComplete: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = authRepository.login(email, password)
                    .flowOn(Dispatchers.IO)
                    .collect {state ->
                        when(state){
                            is ResultState.Loading ->{
                                _loading.value = true
                            }
                            is ResultState.Success ->  {
                                _loading.value = false
                                loggedIn.value = true
                                val responseData = state.data.body()?.string()
                                try {
                                    val jsonResponse = JSONObject(responseData ?: "")
                                    val token = jsonResponse.getString("token")
                                    val username = jsonResponse.getString("username")
                                    val userId = jsonResponse.getInt("id")
                                    AuthManager.saveCredentials(context, token, username, userId)
                                    launch(Dispatchers.Main) {
                                        onComplete(true, null)
                                    }
                                } catch (e: JSONException) {
                                    launch(Dispatchers.Main) {
                                        onComplete(false, "informations invalides")
                                    }
                                }

                            }
                            is ResultState.Error -> {
                                _loading.value = false
                                _error.value = state.message
                                launch(Dispatchers.Main) {
                                    onComplete(false, state.message)
                                }
                            }
                        }

                    }
            }
        }
    }


    suspend fun checkEmail(email: String): Boolean {
        val response = authRepository.checkEmail(mapOf("email" to email))
        return if (response.isSuccessful) {
            response.body()?.get("exists") ?: false
        } else {
            false
        }
    }

    fun register(username: String, email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                authRepository.register(username, email, password)
                    .flowOn(Dispatchers.IO)
                    .collect {state ->
                        when(state){
                            is ResultState.Loading ->{
                                _loading.value = true
                            }
                            is ResultState.Success ->  {
                                _loading.value = false
                                launch(Dispatchers.Main) {
                                    onComplete(true, null)
                                }

                            }
                            is ResultState.Error -> {
                                _loading.value = false
                                _error.value = state.message
                                launch(Dispatchers.Main) {
                                    onComplete(false, state.message)
                                }
                            }
                        }

                    }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AuthVIewModel(authRepository) as T
        }
    }


}