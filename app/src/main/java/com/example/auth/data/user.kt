package com.example.auth.data

import androidx.room.Entity

@Entity(tableName = "user")
data class user(
    val username: String,
    val email: String,
    val password: String
)
