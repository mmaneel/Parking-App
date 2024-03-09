package com.example.auth.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth.R

@Composable
fun firstPage(){
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(color = Color(0xFF773FFF)),
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

    }
}