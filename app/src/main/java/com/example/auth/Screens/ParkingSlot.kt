package com.example.auth.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.auth.data.ParkingSlotdata

@Composable
fun DisplayParkingSlot(navController: NavHostController){
    val parkingSlots = listOf(
        ParkingSlotdata("Slot A", 5, 3), // Slot A avec 5 places au total et 3 places réservées
        ParkingSlotdata("Slot B", 7, 2), // Slot B avec 7 places au total et 2 places réservées
        ParkingSlotdata("Slot C", 10, 5) // Slot C avec 10 places au total et 5 places réservées
        // Ajoutez autant de slots que nécessaire
    )

    Column(
        modifier = Modifier.background(Color(0xFFF6F6F6)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                modifier = Modifier
                    .scale(.8f)
                    .border(width = 1.dp, color = Color.LightGray, shape = CircleShape)
                    .background(Color.White, shape = CircleShape),
                onClick = {  }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                )
            }
            Text(
                text = "Select Parking Slot ",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            IconButton(
                modifier = Modifier
                    .scale(.8f)
                    .border(width = 1.dp, color = Color.LightGray, shape = CircleShape)
                    .background(Color.White, shape = CircleShape),
                onClick = {  }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Sélectionnez votre place de parking",
                style = androidx.compose.ui.text.TextStyle(fontSize = 20.sp),
                modifier = Modifier.padding(16.dp)
            )

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Column (
                    modifier = Modifier
                        .weight(.5f),
                       // .background(Color(0xFFF30206)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    repeat(4) {
                        Box (
                            modifier = Modifier
                                .width(170.dp)
                                .padding(5.dp)
                                .border(BorderStroke(2.dp, Color.LightGray))
                                .height(130.dp),

                            contentAlignment = Alignment.Center
                        ){
                            Button(onClick = { /*TODO*/ }) {
                                Text(text = "BOOK")
                            }
                        }}

                }
               Column(
                   modifier = Modifier
                       .weight(.5f),
                       //.background(Color(0xFFF656F6)),
                   horizontalAlignment = Alignment.CenterHorizontally
               ) {
                   repeat(4) {
                       Box (
                           modifier = Modifier
                               .width(170.dp)
                               .padding(5.dp)
                               .border(BorderStroke(2.dp, Color.LightGray))
                               .height(130.dp),
                           contentAlignment = Alignment.Center
                       ){
                           Button(onClick = { /*TODO*/ }) {
                               Text(text = "BOOK")
                           }
                       }}

               }

            }
        }
    }
}
