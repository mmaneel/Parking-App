package com.example.auth.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.auth.R
import com.example.exo2.Destination


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavScaffold(
    navController : NavHostController,
    content: @Composable () -> Unit,
)
{
    val navColors =NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF7136ff),
                selectedTextColor = Color(0xFF7136ff),
            )
    val currentindex =navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentindex !in listOf(
                    Destination.Splash.route,
                    Destination.SignIn.route,
                    Destination.SignUp.route
                )
            ) {

                NavigationBar(
                    modifier = Modifier
                        .clip(RectangleShape)
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp)
                        )
                        .background(Color.White),
                    containerColor = Color.White,
                    contentColor = Color(0xFF7136ff)
                ) {
                    NavigationBarItem(label = { Text(text = "Home") },
                        selected = currentindex == Destination.Home.route,
                        onClick = { navController.navigate(Destination.Home.route) },
                        colors = navColors,
                        icon = {
                            Icon(
                                Icons.Default.Home,
                                contentDescription = "Home"
                            )
                        })


                    NavigationBarItem(label = { Text(text = "Carte") },
                        selected = false,
                        onClick = { },
                        colors = navColors,
                        icon = {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = "Profile"
                            )
                        })


                    NavigationBarItem(label = { Text(text = "Reservation ") },
                        selected = currentindex == Destination.MesReservation.route,

                        onClick = { navController.navigate(Destination.MesReservation.route) },
                        colors = navColors,
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.note),
                                contentDescription = "Mes Reservation"
                            )
                        })


                    NavigationBarItem(label = { Text(text = "Profile") },
                        selected = currentindex == Destination.Profile.route,
                        onClick = { navController.navigate(Destination.Profile.route) },
                        colors = navColors,
                        icon = {
                            Icon(
                                Icons.Default.AccountCircle,
                                contentDescription = "Profile"
                            )
                        })
                }
            }
        },
    ){
        content()
    }
}