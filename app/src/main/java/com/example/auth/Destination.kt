package com.example.auth

sealed class Destination(val route:String) {
    object Profile:Destination("Profile")
    object VueCarte:Destination("map")

    object MesReservation:Destination("MesReservation")
    object Splash:Destination("splash")
    object SignIn:Destination("signIn")
    object SignUp:Destination("signUp")
    object ParkingList:Destination("parking_list")

    object ParkingListSearch:Destination("parking_list/{searched}") {
        fun getRoute (searched:String) = "parking_list/$searched"
    }

    object Home:Destination("home")
    object ParkingSlot:Destination("ParkingSlot")

    object Ticket:Destination("ticket/{res-id}"){
        fun getRoute (id:Int) = "ticket/$id"
    }
    object Payment:Destination("payment/{res-id}"){
        fun getRoute (id:Int) = "payment/$id"
    }

    object ParkingDetails:Destination("parking_details/{parkingId}"){
        fun getRoute (id:Int) = "parking_details/$id"
    }
    object ParkingMap : Destination("parking_map/{latitude}/{longitude}") {
        fun getRoute(latitude: Double, longitude: Double) = "parking_map/$latitude/$longitude"
    }
}