package com.example.auth.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.auth.AuthManager
import com.example.exo2.Destination

@Composable
fun DisplayMesReservation(navController: NavHostController) {
    val context = LocalContext.current

    // Check login status
    val isLoggedIn = AuthManager.isLoggedIn(context)
    if (isLoggedIn) {
        // Display Mes Reservation content (you'll need to implement this)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Contenu de l'écran Mes Réservations")

            // Bouton de déconnexion
            Button(
                onClick = {
                    // Effacer les informations d'identification et rediriger vers l'écran d'accueil
                    AuthManager.clearCredentials(context)

                    navController.navigate(Destination.ParkingList.route)
                }
            ) {
                Text(text = "Déconnexion")
            }
        }
    } else {
        // Redirect to Sign In if not logged in
        LaunchedEffect(Unit) {
            navController.navigate(Destination.SignIn.route)
        }
    }




}