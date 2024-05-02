package com.example.auth

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.auth.data.Reservation
import java.util.Date

@Dao
interface ReservationDao {
    @Insert
    fun addReservations(vararg reservation: Reservation)


    @Query("select * from reservations")
    fun getReservations():List<Reservation>

    @Query("select * from reservations where reservationTime = :date")
    fun getReservationsByDate(date: Date):List<Reservation>

    @Delete
    fun deleteReservation(reservation: Reservation)


}