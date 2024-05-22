package com.example.auth.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.auth.AuthManager
import com.example.exo2.Destination

@Composable
fun DisplayProfile(navController: NavHostController) {
    val context = LocalContext.current

    val logout = remember {
        mutableStateOf (false)
    }

    // Check login status
    val isLoggedIn = AuthManager.isLoggedIn(context)
    if (isLoggedIn) {
        val username  = AuthManager.getUserName(context)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (username != null) {
                Text(text = username)
            }
            Button(
                onClick = {
                    logout.value = true
                    AuthManager.clearCredentials(context)
                    navController.navigate(Destination.SignIn.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7136ff),
                )
            ) {
                Icon(Icons.Default.ExitToApp , contentDescription = "deconnect")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Déconnexion")
            }
        }
    } else if(!logout.value) {
        // Redirect to Sign In if not logged in
        LaunchedEffect(Unit) {
            navController.navigate(Destination.SignIn.route)
        }
    }

}