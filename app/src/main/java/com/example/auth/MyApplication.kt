package com.example.auth

import android.app.Application
import com.example.auth.Repo.ReservationRepository

class MyApplication:Application() {

    private val dataBase by lazy { ParkingDatabase.getDatabase(this) }
    private val reservationDao by lazy { dataBase!!.getReservationDao()  }
    val reservationRepository by lazy { ReservationRepository(reservationDao) }


}