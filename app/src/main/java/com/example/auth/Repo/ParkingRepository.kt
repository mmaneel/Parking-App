package com.example.auth.Repo

import com.example.auth.Retrofit.Endpoints
import com.google.android.gms.maps.model.LatLng

class ParkingRepository(private val endpoints: Endpoints) {

    suspend fun getAllParks(limit: Int, location:LatLng? = null) =
        if(location == null) endpoints.getRecommendedParks(limit)
        else endpoints.getCloseParks(location.latitude,location.longitude,limit)


    suspend fun getPark(id:Int) = endpoints.getPark(id)
    suspend fun getNearbyParkings(latitude: Double, longitude: Double, radius: Double) =
        endpoints.getNearbyParkings(latitude, longitude, radius)



}