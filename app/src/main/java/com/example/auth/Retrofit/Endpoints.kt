package com.example.auth.Retrofit

import com.example.auth.BaseURL
import com.example.auth.Parking
import com.example.auth.data.Reservation
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoints {

    @GET("parking")
    suspend fun getAllParks(@Query("limit") limit: Int): Response<List<Parking>>


    @GET("parking/{id}")
    suspend fun getPark(@Path("id") id: Int): Response<Parking>

    @POST("register")
    @FormUrlEncoded
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<Void>

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<ResponseBody>


    @POST("reservation")
    suspend fun  createReservation(@Body reservation: Reservation): Response<ResponseBody>

    @DELETE("reservation/{id}")
    suspend fun deleteReservation(@Path("id") id: Int): Response<Void>

    @PUT("reservation")
    @FormUrlEncoded
    suspend fun payReservation(
        @Field("id") id: Int,
        @Field("duration") duration: Int
    ): Response<ResponseBody>


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