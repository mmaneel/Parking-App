package com.example.auth.Model

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.auth.AuthManager
import com.example.auth.Repo.ReservationRepository
import com.example.auth.data.Reservation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReservationModel (private val reservationRepository: ReservationRepository): ViewModel() {

    var allReservations = mutableStateOf(listOf<Reservation>())

    val error = mutableStateOf(false)

    fun getAllReservations(context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                allReservations.value = reservationRepository.getReservations(AuthManager.getUserId(context))
            }
        }
    }

    fun addReservation(reservation: Reservation, context: Context)
    {
        val userId = AuthManager.getUserId(context);
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val success =  reservationRepository.addReservations(reservation)
                if(!success){
                    error.value = true
                }


            }
        }
    }

    fun deleteReservation(reservation: Reservation,context : Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                reservationRepository.deleteReservation(reservation)
                getAllReservations(context)
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