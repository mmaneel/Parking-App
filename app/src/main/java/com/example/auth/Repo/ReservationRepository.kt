package com.example.auth.Repo

import com.example.auth.Retrofit.Endpoints
import com.example.auth.ReservationDao
import com.example.auth.data.Reservation
import org.json.JSONObject

class ReservationRepository(private val resDao: ReservationDao, private val endpoints: Endpoints) {

    suspend fun addReservations(reservation: Reservation): Int?  {
        val response = endpoints.createReservation(reservation)
        if(response.isSuccessful)
        {
            val responseData = response.body()?.string()
            val id = responseData?.let { JSONObject(it).getString("id") }?.toInt() ?: return null
            reservation.id = id;
            resDao.addReservations(reservation)
            return id
        }
        return null
    }


    fun getReservations(userId : Int) = resDao.getReservations(userId)

    fun getReservation(id : Int) = resDao.getReservation(id)

    suspend fun payReservation(duration: Int, id : Int) : Boolean  {
        val response = endpoints.payReservation(id, duration)
        if(response.isSuccessful)
        {
            resDao.payReservation(duration, id)
            return true
        }
        return false
    }

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