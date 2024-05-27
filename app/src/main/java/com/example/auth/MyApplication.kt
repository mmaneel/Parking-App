package com.example.auth

import android.app.Application
import com.example.auth.Repo.AuthRepository
import com.example.auth.Repo.ParkingRepository
import com.example.auth.Repo.ReservationRepository
import com.example.auth.Retrofit.Endpoints

class MyApplication:Application() {

    private val dataBase by lazy { ParkingDatabase.getDatabase(this) }
    private val reservationDao by lazy { dataBase!!.getReservationDao()  }
    private val endpoints by lazy { Endpoints.createEndpoint()}


    val reservationRepository by lazy { ReservationRepository(reservationDao, endpoints) }
    val parkingRepository by lazy {ParkingRepository(endpoints)}
    val authRepository by lazy {AuthRepository(endpoints)}

}