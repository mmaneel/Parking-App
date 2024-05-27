package com.example.auth
import DisplayProfile
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image

//import androidx.compose.foundation.layout.BoxScopeInstance.matchParentSize
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.auth.Model.AuthModel
import com.example.auth.Model.ParkingModel
import com.example.auth.Model.ReservationModel
import com.example.auth.pages.DisplayMesReservation
import com.example.auth.pages.DisplayParkingSlot
import com.example.auth.pages.DisplaySignIn
import com.example.auth.pages.DisplaySignUP
import com.example.auth.pages.NavScaffold
import com.example.auth.ui.theme.AuthTheme
import com.example.auth.pages.firstPage
import com.example.auth.pages.maps
import com.example.exo2.Destination
import com.example.exo2.ParkingDetails
import com.example.exo2.ParkingList
import com.google.android.libraries.places.api.Places


class MainActivity : ComponentActivity() {

    private val reservationModel: ReservationModel by viewModels {
        ReservationModel.Factory((application as MyApplication).reservationRepository)
    }
    private val authModel: AuthModel by viewModels {
        AuthModel.Factory((application as MyApplication).authRepository)

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

                     //   maps( parkingModel)

                    ParkingApp(navController = rememberNavController(), reservationModel, parkingModel,authModel)
                }
            }
        }
    }





}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ParkingApp(navController: NavHostController, reservationModel: ReservationModel, parkingModel: ParkingModel,authModel: AuthModel)
{




    NavHost(navController = navController, startDestination = Destination.Splash.route){
        composable(Destination.Splash.route) {firstPage(navController) }
      //  composable(Destination.VueCarte.route) { maps(parkingModel ,navController) }
        composable(Destination.SignIn.route) { DisplaySignIn(navController, authModel) }
        composable(Destination.SignUp.route) { DisplaySignUP(navController, authModel) }
        composable(Destination.ParkingSlot.route) { DisplayParkingSlot(navController) }
        composable(Destination.MesReservation.route) {
                    NavScaffold(navController = navController) {
                        DisplayMesReservation(reservationModel, navController)
                    }

        }
        composable(Destination.VueCarte.route) {
            NavScaffold(navController = navController) {
                maps(parkingModel ,navController)
            } }
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