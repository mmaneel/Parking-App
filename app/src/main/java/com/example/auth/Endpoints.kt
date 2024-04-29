package com.example.auth

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoints {

    @GET("parking")
    suspend fun getAllParks(): Response<List<Parking>>


    @GET("parking/{id}")
    suspend fun getPark(@Path("id") id: Int): Response<Parking>




    companion object {
        var endpoint: Endpoints? = null
        fun createEndpoint(): Endpoints {
            if(endpoint ==null) {

                endpoint = Retrofit.Builder().baseUrl(BaseURL). addConverterFactory(
                    GsonConverterFactory.create()).build(). create(Endpoints::class.java)

            }
            return endpoint!!
        }

    }


}