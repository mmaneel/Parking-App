package com.example.auth
import androidx.room.Entity
import androidx.room.PrimaryKey


data class Parking(

    val id: Int,
    val name: String,
    val emptyBlocks : Int,
    val city : String,
    val price : Int,
    val adress: String,
    val description: String,
    val img:String,
    val latitude: Double,
    val longitude: Double

)


