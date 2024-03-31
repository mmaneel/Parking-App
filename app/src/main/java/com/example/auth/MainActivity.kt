package com.example.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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

@Composable
fun ParkingApp(navController: NavHostController)
{
    NavHost(navController = navController, startDestination = Destination.Splash.route){
        composable(Destination.Splash.route) { firstPage(navController)}
        composable(Destination.SignIn.route) { DisplaySignIn(navController) }
        composable(Destination.SignUp.route) { DisplaySignUP(navController) }
        composable(Destination.ParkingList.route) { ParkingList(parks = getData(), navController) }
        composable(Destination.ParkingDetails.route) {navBack->
            val id = navBack.arguments?.getString("parkingId")?.toInt()
            ParkingDetails(id,navController)
        }
    }
}