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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.auth.Model.ParkingModel
import com.example.auth.Model.ReservationModel
import com.example.auth.pages.DisplayMesReservation
import com.example.auth.pages.DisplayProfile
import com.example.auth.pages.DisplaySignIn
import com.example.auth.pages.DisplaySignUP
import com.example.auth.pages.NavScaffold
import com.example.auth.ui.theme.AuthTheme
import com.example.auth.pages.firstPage
import com.example.exo2.Destination
import com.example.exo2.ParkingDetails
import com.example.exo2.ParkingList


class MainActivity : ComponentActivity() {

    private val reservationModel: ReservationModel by viewModels {
        ReservationModel.Factory((application as MyApplication).reservationRepository)
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
                     ParkingApp(navController = rememberNavController(), reservationModel, parkingModel)
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ParkingApp(navController: NavHostController, reservationModel: ReservationModel, parkingModel: ParkingModel)
{


    NavHost(navController = navController, startDestination = Destination.Splash.route){
        composable(Destination.Splash.route) {firstPage(navController) }
        composable(Destination.SignIn.route) { DisplaySignIn(navController) }
        composable(Destination.SignUp.route) { DisplaySignUP(navController) }
        composable(Destination.MesReservation.route) {
                    NavScaffold(navController = navController) {
                        DisplayMesReservation(reservationModel, navController)
                    }

        }
        composable(Destination.Profile.route) {  NavScaffold(navController = navController) { DisplayProfile(navController) }}
        composable(Destination.ParkingList.route) {
            NavScaffold(navController = navController) {
                ParkingList(parkingModel , navController)
            }
        }
        composable(Destination.ParkingDetails.route) {navBack->
            val id = navBack.arguments?.getString("parkingId")?.toInt()
            if(id != null)
                ParkingDetails(id,navController, reservationModel, parkingModel)

    }

}


}