package com.example.exo2

sealed class Destination(val route:String) {
    object Profile:Destination("Profile")
    object VueCarte:Destination("map")

    object MesReservation:Destination("MesReservation")
    object Splash:Destination("splash")
    object SignIn:Destination("signIn")
    object SignUp:Destination("signUp")
    object ParkingList:Destination("parking_list")
    object ParkingSlot:Destination("ParkingSlot")
    object ParkingDetails:Destination("parking_details/{parkingId}"){
        fun getRoute (id:Int) = "parking_details/$id"
    }
    object ParkingMap : Destination("parking_map/{latitude}/{longitude}") {
        fun getRoute(latitude: Double, longitude: Double) = "parking_map/$latitude/$longitude"
    }
}