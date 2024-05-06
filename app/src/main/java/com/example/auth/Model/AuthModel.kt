package com.example.auth.Model

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.auth.AuthManager
import com.example.auth.Repo.AuthRepository
import com.example.auth.Repo.ParkingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AuthModel(private val authRepository: AuthRepository ) :ViewModel(){

    val error = mutableStateOf(false)
    val loggedIn = mutableStateOf(false)
    fun login(email: String, password: String, context: Context, onComplete: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = authRepository.login(email, password)
                if (response.isSuccessful) {

                    loggedIn.value = true
                    val responseData = response.body()?.string()
                    val token = responseData?.let { JSONObject(it).getString("token") }
                    val username = responseData?.let { JSONObject(it).getString("username") }
                    val userId = responseData?.let { JSONObject(it).getInt("id") }
                    AuthManager.saveCredentials(context, token, username,userId)

                    launch(Dispatchers.Main) {
                        onComplete(true, null)
                    }
                } else {
                    error.value = true
                    launch(Dispatchers.Main) {
                    onComplete(false, "Failed to login")
                    }
                }
            }
        }
    }

    fun register(username: String, email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = authRepository.register(username, email, password)
                if (response.isSuccessful) {
                    loggedIn.value = true
                    launch(Dispatchers.Main) {
                        onComplete(true, null)
                    }
                } else {
                    error.value = true
                    launch(Dispatchers.Main) {
                        onComplete(false, "Failed to register")
                    }
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AuthModel(authRepository) as T
        }
    }


}