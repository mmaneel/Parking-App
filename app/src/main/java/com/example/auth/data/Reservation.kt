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
    val reservationTime : Date,
    val userId : Int

)
