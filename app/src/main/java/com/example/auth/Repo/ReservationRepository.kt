package com.example.auth.Repo

import com.example.auth.Endpoints
import com.example.auth.ReservationDao
import com.example.auth.data.Reservation
import java.util.Date

class ReservationRepository(private val resDao: ReservationDao, private val endpoints: Endpoints) {

    suspend fun addReservations(reservation: Reservation): Boolean  {
        val response = endpoints.createReservation(reservation)
        if(response.isSuccessful)
        {
            resDao.addReservations(reservation)
            return true
        }
        return false
    }


    fun getReservations() = resDao.getReservations()

    fun getReservationsByDate(date: Date) = resDao.getReservationsByDate(date)

    fun deleteReservation(reservation: Reservation) = resDao.deleteReservation(reservation)

}