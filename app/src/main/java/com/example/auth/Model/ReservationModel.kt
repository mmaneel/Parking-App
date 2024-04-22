package com.example.auth.Model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.auth.Repo.ReservationRepository
import com.example.auth.data.Reservation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReservationModel (private val reservationRepository: ReservationRepository): ViewModel() {

    var allReservations = mutableStateOf(listOf<Reservation>())

    fun getAllReservations() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                allReservations.value = reservationRepository.getReservations()
            }
        }
    }

    fun addReservation(reservation: Reservation)
    {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                reservationRepository.addReservations(reservation)
            }
        }
    }

    fun deleteReservation(reservation: Reservation) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                reservationRepository.deleteReservation(reservation)
                getAllReservations()
            }
        }
    }


    @Suppress("UNCHECKED_CAST")
    class Factory(private val reservationRepository: ReservationRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ReservationModel(reservationRepository) as T
        }
    }

}