package com.example.auth

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.auth.pages.DisplayMesReservation
import com.example.auth.pages.DisplayProfile
import com.example.auth.pages.DisplaySignIn
import com.example.auth.pages.DisplaySignUP
import com.example.auth.ui.theme.AuthTheme
import com.example.auth.pages.firstPage
import com.example.exo2.Destination
import com.example.exo2.ParkingDetails
import com.example.exo2.ParkingList
import com.example.exo2.getData



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            AuthTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                     ParkingApp(navController = rememberNavController())
                     //ParkingDetails(id = 2, navController = rememberNavController())
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ParkingApp(navController: NavHostController)
{
    //val context = LocalContext.current
    //val isLoggedIn = AuthManager.isLoggedIn(context)
    var currentindex =navController.currentBackStackEntryAsState().value?.destination?.route


Scaffold (
    bottomBar = {
        if (currentindex !in listOf(Destination.Splash.route, Destination.SignIn.route, Destination.SignUp.route)) {

            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                NavigationBar {
                    NavigationBarItem(label = { Text(text = "Home") },
                        selected = currentindex == Destination.ParkingList.route,
                        onClick = { navController.navigate(Destination.ParkingList.route) },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") })

                    NavigationBarItem(label = { Text(text = "Profile") },
                        selected = currentindex == Destination.Profile.route,
                        onClick = { navController.navigate(Destination.Profile.route) },
                        icon = {
                            Icon(
                                Icons.Default.AccountCircle,
                                contentDescription = "Profile"
                            )
                        })

                    NavigationBarItem(label = { Text(text = "Mes Reservation ") },
                        selected = currentindex == Destination.MesReservation.route,

                        onClick = {navController.navigate(Destination.MesReservation.route)

                        },
                        icon = {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = "Mes Reservation"
                            )
                        })
                }

            }
        }
    },){
    NavHost(navController = navController, startDestination = Destination.Splash.route){
        composable(Destination.Splash.route) { firstPage(navController)}
        composable(Destination.SignIn.route) { DisplaySignIn(navController) }
        composable(Destination.SignUp.route) { DisplaySignUP(navController) }
        composable(Destination.MesReservation.route) { DisplayMesReservation(navController) }
        composable(Destination.Profile.route) { DisplayProfile(navController) }
        composable(Destination.ParkingList.route) { ParkingList(parks = getData(), navController) }
        composable(Destination.ParkingDetails.route) {navBack->
            val id = navBack.arguments?.getString("parkingId")?.toInt()
            ParkingDetails(id,navController)
        }
    }

}

}