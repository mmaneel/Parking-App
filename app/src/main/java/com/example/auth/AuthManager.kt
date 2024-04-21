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
    private const val KEY_EMAIL = "email"
    private const val KEY_PASSWORD = "password"
    private const val KEY_NAME = "name"

    // Initialise les SharedPreferences
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
    // Vérifie si l'utilisateur est connecté
    fun isLoggedIn(context: Context): Boolean {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.contains(KEY_EMAIL) && sharedPreferences.contains(KEY_PASSWORD)
    }
    // Enregistre les informations
    fun saveCredentials(context: Context, email: String, password: String) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_PASSWORD, password)
        editor.apply()
    }
    private fun loadUsers(context: Context): List<user> {
        val inputStream = context.assets.open("users.json")
        val reader = BufferedReader(InputStreamReader(inputStream))
        val listType = object : TypeToken<List<user>>() {}.type
        return Gson().fromJson(reader, listType)
    }
    fun checkCredentials(email: String, password: String): Boolean {
        val savedEmail = "manel@gmail.com"
        val savedPassword = "1234"
        return email == savedEmail && password == savedPassword
        /*val users = loadUsers(context)
        return users.any { it.email == email && it.password == password }*/
    }
    // Supprime les informations
    fun clearCredentials(context: Context) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.remove(KEY_EMAIL)
        editor.remove(KEY_PASSWORD)
        editor.apply()
    }
    fun createUser(context: Context, name: String, email: String, password: String) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_PASSWORD, password)

        editor.putString(KEY_NAME, name)
        editor.apply()
    }
    /*fun createUser(context: Context, name: String, email: String, password: String) {
        val users = loadUsers(context).toMutableList()
        users.add(user(name, email, password))
        val json = Gson().toJson(users)
        context.openFileOutput("users.json", Context.MODE_PRIVATE).use { outputStream ->
            outputStream.write(json.toByteArray())
        }
    }*/










    // Obtient l'e-mail enregistré
    /*fun getEmail(context: Context): String? {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(KEY_EMAIL, null)
    }

    // Obtient le mot de passe enregistré
    fun getPassword(context: Context): String? {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(KEY_PASSWORD, null)
    }*/
}