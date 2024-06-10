package com.example.auth.Repo

import com.example.auth.Retrofit.Endpoints

class ParkingRepository(private val endpoints: Endpoints) {

    suspend fun getAllParks(limit: Int) = endpoints.getAllParks(limit)

    suspend fun getPark(id:Int) = endpoints.getPark(id)
    suspend fun getNearbyParkings(latitude: Double, longitude: Double, radius: Double) =
        endpoints.getNearbyParkings(latitude, longitude, radius)



}