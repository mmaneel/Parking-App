package com.example.exo2

sealed class Destination(val route:String) {

    object Splash:Destination("splash")
    object SignIn:Destination("signIn")
    object SignUp:Destination("signUp")
    object ParkingList:Destination("parking_list")
    object ParkingDetails:Destination("parking_details/{parkingId}"){
        fun getRoute (id:Int) = "parking_details/$id"
    }

}