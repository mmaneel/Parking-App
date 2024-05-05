package com.example.auth.Repo

import com.example.auth.Endpoints
import com.example.auth.ReservationDao
import com.example.auth.data.Reservation
import org.json.JSONObject
import java.util.Date

class ReservationRepository(private val resDao: ReservationDao, private val endpoints: Endpoints) {

    suspend fun addReservations(reservation: Reservation): Boolean  {
        val response = endpoints.createReservation(reservation)
        if(response.isSuccessful)
        {
            val responseData = response.body()?.string()
            val id = responseData?.let { JSONObject(it).getString("id") }?.toInt() ?: return false
            reservation.id = id;
            resDao.addReservations(reservation)
            return true
        }
        return false
    }


    fun getReservations() = resDao.getReservations()

    fun getReservationsByDate(date: Date) = resDao.getReservationsByDate(date)

    suspend fun deleteReservation(reservation: Reservation): Boolean  {
        val response = endpoints.deleteReservation(reservation.id)
        if(response.isSuccessful)
        {
            resDao.deleteReservation(reservation)
            return true
        }
        return false
    }

}