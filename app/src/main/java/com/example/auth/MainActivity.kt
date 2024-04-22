package com.example.auth

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavArgsLazy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.RoomDatabase
import com.example.auth.data.Reservation
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
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
                     val db = ParkingDatabase.getDatabase(context = applicationContext)
                     ParkingApp(navController = rememberNavController(), db)
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ParkingApp(navController: NavHostController, db : ParkingDatabase?)
{
    //val context = LocalContext.current
    //val isLoggedIn = AuthManager.isLoggedIn(context)


    NavHost(navController = navController, startDestination = Destination.Splash.route){
        composable(Destination.Splash.route) {firstPage(navController) }
        composable(Destination.SignIn.route) { DisplaySignIn(navController) }
        composable(Destination.SignUp.route) { DisplaySignUP(navController) }
        composable(Destination.MesReservation.route) {

            val dao = db?.getReservationDao()
            var reservations:MutableState<List<Reservation>?> =  remember { mutableStateOf(null) }
            CoroutineScope(Dispatchers.IO).launch {
                reservations.value = dao?.getReservations()
            }
                if(reservations.value != null)
                    NavScaffold(navController = navController) {
                        DisplayMesReservation(reservations.value, navController, db)
                    }

        }
        composable(Destination.Profile.route) {  NavScaffold(navController = navController) { DisplayProfile(navController) }}
        composable(Destination.ParkingList.route) { NavScaffold(navController = navController) {
            ParkingList(getData(), navController) }
        }
        composable(Destination.ParkingDetails.route) {navBack->
            val id = navBack.arguments?.getString("parkingId")?.toInt()
            if(id != null)
                ParkingDetails(id,navController, db)

    }

}


}