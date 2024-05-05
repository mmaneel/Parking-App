package com.example.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.platform.LocalContext
import com.example.auth.data.user
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader

object AuthManager {
    private const val PREF_NAME = "auth_pref"
    private const val KEY_TOKEN = "email"
    private const val KEY_NAME = "user_name"

    // Initialise les SharedPreferences
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
    // Vérifie si l'utilisateur est connecté
    fun isLoggedIn(context: Context): Boolean {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.contains(KEY_TOKEN)
    }
    // Enregistre les informations
    fun saveCredentials(context: Context, token: String?, username: String?) {
        if(token == null || username == null)
            return
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_TOKEN, token)
        editor.putString(KEY_NAME, username)
        editor.apply()
    }


    // Supprime les informations
    fun clearCredentials(context: Context) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.remove(KEY_NAME)
        editor.remove(KEY_TOKEN)
        editor.apply()
    }


    fun getUserName(context: Context): String? {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(KEY_NAME, null)
    }

}