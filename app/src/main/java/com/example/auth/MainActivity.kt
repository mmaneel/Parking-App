package com.example.auth

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.auth.ViewModels.AuthVIewModel
import com.example.auth.ViewModels.ParkingModel
import com.example.auth.ViewModels.ReservationModel
import com.example.auth.Screens.DisplayMesReservation
import com.example.auth.Screens.DisplayParkingSlot
import com.example.auth.Screens.DisplayPayment
import com.example.auth.Screens.DisplayProfile
import com.example.auth.Screens.DisplaySignIn
import com.example.auth.Screens.DisplaySignUP
import com.example.auth.Screens.DisplayTicket
import com.example.auth.Screens.NavScaffold
import com.example.auth.ui.theme.AuthTheme
import com.example.auth.Screens.firstPage
import com.example.exo2.Destination
import com.example.exo2.Home
import com.example.exo2.ParkingDetails
import com.example.exo2.ParkingList


class MainActivity : ComponentActivity() {

    private val reservationModel: ReservationModel by viewModels {
        ReservationModel.Factory((application as MyApplication).reservationRepository)
    }
    private val authModel: AuthVIewModel by viewModels {
        AuthVIewModel.Factory((application as MyApplication).authRepository)

    }
    private val parkingModel: ParkingModel by viewModels {
        ParkingModel.Factory((application as MyApplication).parkingRepository)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            AuthTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //LocationScreen()
                    ParkingApp(navController = rememberNavController(), reservationModel, parkingModel,authModel)
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ParkingApp(navController: NavHostController, reservationModel: ReservationModel, parkingModel: ParkingModel,authModel: AuthVIewModel)
{




    NavHost(navController = navController, startDestination = Destination.Splash.route){
        composable(Destination.Splash.route) {firstPage(navController) }
        composable(Destination.SignIn.route) { DisplaySignIn(navController, authModel) }
        composable(Destination.SignUp.route) { DisplaySignUP(navController, authModel) }
        composable(Destination.ParkingSlot.route) { DisplayParkingSlot(navController) }
        composable(Destination.MesReservation.route) {
                    NavScaffold(navController = navController) {
                        DisplayMesReservation(reservationModel, navController)
                    }

        }
        composable(Destination.Profile.route) {  NavScaffold(navController = navController) { DisplayProfile(navController) }}
        composable(Destination.Home.route) {
            NavScaffold(navController = navController) {
                Home(parkingModel , navController)
            }
        }
        composable(Destination.ParkingList.route) {
            NavScaffold(navController = navController) {
                ParkingList(parkingModel , navController)
            }
        }

        composable(Destination.ParkingListSearch.route) {navBack->
            val searched = navBack.arguments?.getString("searched")
            NavScaffold(navController = navController) {
                if(searched != null)
                    ParkingList(parkingModel , navController,searched)
            }
        }


        composable(Destination.ParkingDetails.route) {navBack->
            val id = navBack.arguments?.getString("parkingId")?.toInt()
            if(id != null)
                ParkingDetails(id,navController, reservationModel, parkingModel)

        }
        composable(Destination.Ticket.route) {navBack->
            val id = navBack.arguments?.getString("res-id")?.toInt()
            if(id != null)
                DisplayTicket(navController = navController, id = id,reservationModel)

        }
        composable(Destination.Payment.route) {navBack->
            val id = navBack.arguments?.getString("res-id")?.toInt()
            if(id != null)
                DisplayPayment(navController, id, reservationModel)
        }

}


}