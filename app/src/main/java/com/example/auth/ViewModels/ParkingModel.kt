package com.example.auth.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.auth.Parking
import com.example.auth.Repo.ParkingRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ParkingModel (private val parkingRepo : ParkingRepository) : ViewModel(){

    val parks = mutableStateOf(listOf<Parking>())
    val recomended = mutableStateOf(listOf<Parking>())
    val details = mutableStateOf<Parking?>(null)
    val failed = mutableStateOf(false)

    val loading = mutableStateOf(false)
    val error =  mutableStateOf("")



    val nearbyParks = mutableStateOf(listOf<Parking>())

    var currentLocation = mutableStateOf<LatLng?>(null)

    var mapInit = currentLocation.value

    fun getAllParks(limit: Int = 50, nearby: Boolean = true){
        if(failed.value)
            return
        loading.value = true
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                try {
                    val response =
                        if (nearby && currentLocation.value != null)
                            parkingRepo.getAllParks(limit,currentLocation.value)
                        else parkingRepo.getAllParks(limit)


                    if(response.isSuccessful){
                        val data = response.body()
                        if(data != null)
                            if(nearby)
                                parks.value = data
                            else
                                recomended.value = data
                        }
                }catch (_:Exception)
                {
                    failed.value = true
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
        class Factory(private val reservationRepository: ParkingRepository) :
            ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ParkingModel(reservationRepository) as T
            }
        }

    }