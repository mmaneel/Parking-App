package com.example.auth.Model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.auth.Repo.AuthRepository
import com.example.auth.Repo.ParkingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthModel(private val authRepository: AuthRepository ) :ViewModel(){

    val error = mutableStateOf(false)
    val loggedIn = mutableStateOf(false)
    fun login(email: String, password: String , onComplete: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = authRepository.login(email, password)
                if (response.isSuccessful) {

                    loggedIn.value = true
                    onComplete(true, null)
                } else {

                    error.value = true
                    onComplete(false, "Failed to login")
                }
            }
        }
    }

    fun register(username: String, email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = authRepository.register(username, email, password)
                if (response.isSuccessful) {
                    // Enregistrement réussi
                    loggedIn.value = true
                    onComplete(true, null)
                } else {
                    // Erreur d'enregistrement
                    error.value = true
                    onComplete(false, "Failed to register")
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