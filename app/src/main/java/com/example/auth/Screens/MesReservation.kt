package com.example.auth.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.auth.AuthManager
import com.example.auth.BaseURL
import com.example.auth.CoilAsyncImage
import com.example.auth.IMAGE_URL
import com.example.auth.R
import com.example.auth.ViewModels.ReservationModel
import com.example.exo2.Destination
import com.example.exo2.TextWithIcon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DisplayMesReservation(reservationModel: ReservationModel, navController: NavHostController) {
    val context = LocalContext.current
    val logout = remember {
        mutableStateOf (false)
    }

    LaunchedEffect(Unit) {
            reservationModel.getAllReservations(context)
    }

    val reservations = reservationModel.allReservations.value
    
    var type by remember { mutableIntStateOf(0) }
    

    // Check login status
    val isLoggedIn = AuthManager.isLoggedIn(context)
    if (isLoggedIn) {
        val exists = (reservations.size ?:0) >0

        Column (
            modifier = Modifier
                .background(Color(0xFFF6F6F6))
                .fillMaxHeight(.92f),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
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
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                    )
                }

                Text(
                    text = "Mes Reservations",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                IconButton(
                    modifier = Modifier
                        .scale(.8f)
                        .border(width = 1.dp, color = Color.LightGray, shape = CircleShape)
                        .background(Color.White, shape = CircleShape),
                    onClick = {
                        logout.value = true
                        AuthManager.clearCredentials(context)
                        navController.navigate(Destination.ParkingList.route)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(
                    modifier = Modifier.width(120.dp) ,
                    onClick = {
                            type = 0
                }) {
                    Text(
                        text = "Validé",
                        color = if (type == 0) Color(0xFF7136ff) else Color.Black,
                        fontSize = 17.sp,
                    )
                }
                TextButton(
                    modifier = Modifier.width(120.dp),
                    onClick = {
                            type = 1
                }) {
                    Text(
                        text = "En Attente",
                        color = if (type == 1) Color(0xFF7136ff) else Color.Black,
                        fontSize = 17.sp,
                    )
                }
                TextButton(
                    modifier = Modifier.width(120.dp),
                    onClick = {
                            type = 2
                }) {
                    Text(
                        text = "Complété",
                        color = if (type == 2) Color(0xFF7136ff) else Color.Black,
                        fontSize = 17.sp,
                    )
                }

            }
            

            if(reservations.isEmpty())
                Text(
                    text = "+ Reserver dans un parking",
                    color = Color(0x770000FF),
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Destination.ParkingList.route)
                        }
                )


            LazyColumn (
                modifier =  Modifier
                    .padding(5.dp)
            ) {
                if(exists)
                 items(reservations.filter {
                     (it.payee == (type != 1)) &&
                             ((type == 2 && Calendar.getInstance().timeInMillis > it.arrivalTime.time) || (type != 2 && Calendar.getInstance().timeInMillis <= it.arrivalTime.time)  )
                 } ) {
                     Column (
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding(bottom = 20.dp)

                             .clip(RoundedCornerShape(10.dp))
                             .background(color = Color.White)
                             .clickable {

                             },
                     ){
                         Row(
                             modifier = Modifier
                                .height(140.dp)
                             )
                         {
                             Box(
                                 modifier = Modifier
                                     .fillMaxHeight()
                                     .weight(1.5f)
                                     .padding(5.dp),
                                 contentAlignment = Alignment.Center
                             ) {
                                 CoilAsyncImage(
                                     model = IMAGE_URL + it.parking.img,
                                     modifier = Modifier
                                         .aspectRatio(1f)
                                         .clip(RoundedCornerShape(10.dp)),
                                     contentDescription = "Parking Image",
                                     contentScale = ContentScale.Crop,
                                     placeholder =  R.drawable.parking_ph,
                                     error = R.drawable.parking_ph,
                                 )
                             }

                             Column(
                                 modifier = Modifier
                                     .weight(2f)
                                     .fillMaxHeight(),
                                 verticalArrangement = Arrangement.Center,
                             ) {

                                 Box(
                                     modifier = Modifier
                                         .background(Color(0xFFF6F6F6))
                                         .padding(horizontal = 5.dp)
                                         .clip(RoundedCornerShape(10.dp)),
                                 ){
                                     Text(
                                         text = "car parking",
                                         fontSize = 15.sp,
                                         color = Color(0xFF7136ff),
                                     )
                                 }


                                 Text(
                                     text = it.parking.name,
                                     fontSize = 19.sp,
                                     fontWeight = FontWeight.Bold,
                                 )

                                 TextWithIcon(
                                     text = formatTime(it.arrivalTime),
                                     fontSize = 15.sp,
                                     color = Color.Gray,
                                     Icon = Icons.Default.DateRange
                                 )

                                 Spacer(modifier = Modifier.height(10.dp))

                                 Row (verticalAlignment = Alignment.Bottom){

                                     Text(
                                         text = "${it.parking.price}DA",
                                         fontSize = 18.sp,
                                         color = Color(0xFF7136ff),
                                     )
                                     Spacer(modifier = Modifier.width(2.dp))
                                     Text(
                                         text = "/hr",
                                         fontSize = 15.sp,
                                         color = Color.Gray,
                                     )
                                 }




                             }
                         }

                         Row (
                             modifier = Modifier
                                 .fillMaxWidth(),
                             horizontalArrangement = Arrangement.SpaceAround
                         ){
                             Spacer(modifier = Modifier.weight(.05f))
                             Button(
                                 modifier = Modifier.weight(.4f),
                                 colors = ButtonDefaults.buttonColors(
                                     containerColor = Color(0x22000000)
                                 ),
                                 onClick = {
                                     CoroutineScope(Dispatchers.IO).launch {
                                         reservationModel.deleteReservation(reservation = it, context)
                                     }
                                 }) {
                                 Text(color = Color(0xFF7136ff), text = "Annuler")
                             }
                             Spacer(modifier = Modifier.weight(.05f))

                             if(it.payee)
                             {
                                 Button(
                                     modifier = Modifier.weight(.4f),
                                     colors = ButtonDefaults.buttonColors(
                                         containerColor = Color(0xFF7136ff)
                                     ),
                                     onClick = { navController.navigate(Destination.Ticket.getRoute(it.id)) }) {
                                     Text(text = "E-Ticket")
                                 }
                             }
                             else {
                                 Button(
                                     modifier = Modifier.weight(.4f),
                                     colors = ButtonDefaults.buttonColors(
                                         containerColor = Color(0xFF7136ff)
                                     ),
                                     onClick = {
                                         reservationModel.getAllReservations(context)
                                         navController.navigate(Destination.Payment.getRoute(it.id))
                                     }) {
                                     Text(text = "Valider")
                                 }
                             }

                             Spacer(modifier = Modifier.weight(.05f))
                         }
                     }

                }
            }
        }

    } else if (!logout.value) {
        // Redirect to Sign In if not logged in
        LaunchedEffect(Unit) {
            navController.navigate(Destination.SignIn.route)
        }
    }

}




@RequiresApi(Build.VERSION_CODES.O)
fun formatTime(localDate: Date):String
{
    val day = localDate.date
    val month = localDate.month+1
    val year = localDate.year + 1900

    val hour = if(localDate.hours in 0..9)  "0${localDate.hours}" else "${localDate.hours}"
    val minutes = if(localDate.minutes in 0..9)  "0${localDate.minutes}" else "${localDate.minutes}"

    return "${day}/${month}/${year} " +
            "à ${hour}:${minutes}"
}