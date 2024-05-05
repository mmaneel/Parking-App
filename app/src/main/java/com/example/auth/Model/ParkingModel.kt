package com.example.auth.Model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.auth.Parking
import com.example.auth.Repo.ParkingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ParkingModel (private val parkingRepo : ParkingRepository) : ViewModel(){

    val parks = mutableStateOf(listOf<Parking>())
    val details = mutableStateOf<Parking?>(null)

    val loading = mutableStateOf(false)
    val error =  mutableStateOf("")



    fun getAllParks(){
        loading.value = true
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                val response =  parkingRepo.getAllParks()
                if(response.isSuccessful){
                    val data = response.body()
                    if(data != null)
                        parks.value = data
                    }
                else{
                        error.value = "impossible de récupérer les données"
                }

            }
            loading.value = false
        }

    }


    fun getPark(id: Int){
        loading.value = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response =  parkingRepo.getPark(id)
                if(response.isSuccessful){
                    val data = response.body()
                    if(data != null)
                        details.value = data
                }
                else{
                    error.value = "impossible de récupérer les données"
                }
                loading.value = false
            }
        }
    }


    @Suppress("UNCHECKED_CAST")
    class Factory(private val reservationRepository: ParkingRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ParkingModel(reservationRepository) as T
        }
    }

}