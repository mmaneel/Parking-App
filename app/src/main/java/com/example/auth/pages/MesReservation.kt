package com.example.auth.pages

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.auth.AuthManager
import com.example.auth.R
import com.example.exo2.Destination
import com.example.exo2.Parking

@Composable
fun DisplayMesReservation(parks:List<Parking>, navController: NavHostController) {
    val context = LocalContext.current
    val isLoggedIn = AuthManager.isLoggedIn(context)
    if (!isLoggedIn) {
        LaunchedEffect(Unit) {
            navController.navigate(Destination.SignIn.route)
        }
    }
    if (isLoggedIn) {
        Column(
            modifier = Modifier.background(Color(0xFFF6F6F6))
            /*modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center*/
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
                    text = "Mes Reservation",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                /*IconButton(
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
                }*/
                Button(
                    onClick = {
                        AuthManager.clearCredentials(context)

                        navController.navigate(Destination.ParkingList.route)
                    }
                ) {
                    Text(text = "Déconnexion")
                }
            }
            Column (
                modifier =  Modifier.padding(5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                        .height(140.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color.White)
                        /*.clickable {
                            navController.navigate(Destination.ParkingDetails.getRoute(it.id))
                        },*/

                    )
                {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1.5f)
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(10.dp)),
                            painter = painterResource(id = R.drawable.parking1),
                            contentDescription = "Parking Image",
                            contentScale = ContentScale.Crop,
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
                            text = "sunset Parking",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row (verticalAlignment = Alignment.Bottom){

                            Text(
                                text = "2000DA",
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                        .height(140.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color.White)
                    /*.clickable {
                        navController.navigate(Destination.ParkingDetails.getRoute(it.id))
                    },*/

                )
                {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1.5f)
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(10.dp)),
                            painter = painterResource(id = R.drawable.park1),
                            contentDescription = "Parking Image",
                            contentScale = ContentScale.Crop,
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
                            text = "Metro Plaza",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row (verticalAlignment = Alignment.Bottom){

                            Text(
                                text = "1500DA",
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
            }





        }
    }
}