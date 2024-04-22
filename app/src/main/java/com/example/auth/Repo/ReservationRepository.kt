package com.example.auth.Repo

import com.example.auth.ReservationDao
import com.example.auth.data.Reservation
import java.util.Date

class ReservationRepository(private val resDao: ReservationDao) {

    fun addReservations(reservation: Reservation) = resDao.addReservations(reservation)


    fun getReservations() = resDao.getReservations()

    fun getReservationsByDate(date: Date) = resDao.getReservationsByDate(date)

    fun deleteReservation(reservation: Reservation) = resDao.deleteReservation(reservation)

}