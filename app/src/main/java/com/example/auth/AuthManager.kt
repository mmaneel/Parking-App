package com.example.auth

import android.content.Context
import android.content.SharedPreferences

object AuthManager {
    private const val PREF_NAME = "auth_pref"
    private const val KEY_EMAIL = "email"
    private const val KEY_PASSWORD = "password"

    // Initialise les SharedPreferences
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
    // Vérifie si l'utilisateur est connecté
    fun isLoggedIn(context: Context): Boolean {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.contains(KEY_EMAIL) && sharedPreferences.contains(KEY_PASSWORD)
    }
    // Enregistre les informations d'identification de l'utilisateur
    fun saveCredentials(context: Context, email: String, password: String) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_PASSWORD, password)
        editor.apply()
    }
    // Supprime les informations d'identification de l'utilisateur
    fun clearCredentials(context: Context) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.remove(KEY_EMAIL)
        editor.remove(KEY_PASSWORD)
        editor.apply()
    }

    // Obtient l'e-mail enregistré
    fun getEmail(context: Context): String? {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(KEY_EMAIL, null)
    }

    // Obtient le mot de passe enregistré
    fun getPassword(context: Context): String? {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(KEY_PASSWORD, null)
    }
}