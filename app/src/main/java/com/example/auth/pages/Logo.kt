package com.example.auth.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.auth.MainActivity
import com.example.auth.R
import com.example.exo2.Destination
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun firstPage(navController:NavHostController){

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(color = Color(0xFF773FFF))
            .fillMaxWidth()
            .fillMaxHeight(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

        ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                //.fillMaxWidth()
                .height(110.dp)
                .width(110.dp),

            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(25.dp))
        Text(

            text = "Car Parking App ",
            letterSpacing = 3.sp,
            fontWeight = FontWeight.SemiBold,
            fontSize = 45.sp,
            color = Color(0xFFFFFFFF),


        )

        LaunchedEffect(navController) {
            delay(2000)
            navController.navigate(Destination.ParkingList.route)
        }

    }
}