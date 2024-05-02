package com.example.auth.Dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.auth.data.user

import androidx.room.Query
import com.example.auth.data.Reservation

@Dao
interface AuthDao {
    @Insert
    fun addUser(vararg user: user)
    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    fun getUserByEmailAndPassword(email: String, password: String): user?
}