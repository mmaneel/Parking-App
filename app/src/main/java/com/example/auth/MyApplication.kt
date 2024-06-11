package com.example.auth

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.auth.Repo.AuthRepository
import com.example.auth.Repo.ParkingRepository
import com.example.auth.Repo.ReservationRepository
import com.example.auth.Retrofit.Endpoints
import java.util.concurrent.TimeUnit

class MyApplication:Application() {

    private val dataBase by lazy { ParkingDatabase.getDatabase(this) }
    private val reservationDao by lazy { dataBase!!.getReservationDao()  }
    private val endpoints by lazy { Endpoints.createEndpoint()}


    val reservationRepository by lazy { ReservationRepository(reservationDao, endpoints) }
    val parkingRepository by lazy {ParkingRepository(endpoints)}
    val authRepository by lazy {AuthRepository(endpoints)}

    override fun onCreate() {
        super.onCreate()

        val workRequest = PeriodicWorkRequestBuilder<ReservationWorker>(5, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "ReservationNotificationWork",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }

}