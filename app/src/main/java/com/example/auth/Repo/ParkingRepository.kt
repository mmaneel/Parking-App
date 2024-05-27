package com.example.auth.Repo

import com.example.auth.Endpoints

class ParkingRepository(private val endpoints: Endpoints) {

    suspend fun getAllParks() = endpoints.getAllParks()

    suspend fun getPark(id:Int) = endpoints.getPark(id)
    suspend fun getNearbyParkings(latitude: Double, longitude: Double, radius: Double) =
        endpoints.getNearbyParkings(latitude, longitude, radius)



}