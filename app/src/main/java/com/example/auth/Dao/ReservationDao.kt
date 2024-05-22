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


    @Query("select * from reservations where userId= :userId")
    fun getReservations(userId: Int):List<Reservation>

    @Query("select * from reservations where id= :id")
    fun getReservation(id: Int):Reservation

    @Query("select * from reservations where arrivalTime = :date")
    fun getReservationsByDate(date: Date):List<Reservation>

    @Query("UPDATE reservations SET duration = :duration, payee = 1 WHERE id = :id")
    fun payReservation(duration: Int, id : Int)

    @Delete
    fun deleteReservation(reservation: Reservation)


}