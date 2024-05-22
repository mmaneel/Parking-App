package com.example.auth.ViewModels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.auth.AuthManager
import com.example.auth.Parking
import com.example.auth.Repo.ReservationRepository
import com.example.auth.data.Reservation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReservationModel (private val reservationRepository: ReservationRepository): ViewModel() {

    var allReservations = mutableStateOf(listOf<Reservation>())

    val details = mutableStateOf<Reservation?>(null)

    val error = mutableStateOf(false)

    fun getAllReservations(context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                allReservations.value = reservationRepository.getReservations(AuthManager.getUserId(context))
            }
        }
    }


    fun getReservations(id: Int, context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                details.value = reservationRepository.getReservation(id)
            }
        }
    }


    fun payReservation (id: Int,duration: Int, context: Context){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val success =  reservationRepository.payReservation(duration ,id)
                if(!success){
                    error.value = true
                }

            }
        }
    }

    fun addReservation(reservation: Reservation, callback: (Int) -> Unit)
    {
        var id:Int? = null
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                id =  reservationRepository.addReservations(reservation)
                if(id == null){
                    error.value = true
                }else{
                    withContext(Dispatchers.Main) {
                        callback(id!!)
                    }
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