package com.example.auth.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.auth.Parking
import java.util.Date

@Entity(tableName = "reservations")
data class Reservation(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val parking:Parking,
    val arrivalTime : Date,
    var duration : Int,
    val userId : Int,
    val payee : Boolean,

)
