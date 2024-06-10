package com.example.auth.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.auth.R.drawable
import com.example.auth.ViewModels.ReservationModel
import com.example.auth.Destination

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayPayment(navController: NavHostController, id: Int, reservvationModel : ReservationModel){

    val context = LocalContext.current

    val selected = remember {
        mutableIntStateOf(0)
    }


    reservvationModel.getReservations(id, context)
    val details =   reservvationModel.details.value


    var pickerValue by remember { mutableIntStateOf(1) }


    Scaffold(
        bottomBar = {
            BottomAppBar (
                containerColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RectangleShape)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp)
                    )
                    .height(80.dp)
            ){

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF7136ff)
                        ),
                        onClick = {
                            reservvationModel.payReservation(id, pickerValue, context)
                            if(!reservvationModel.error.value)
                                navController.navigate(Destination.Ticket.getRoute(id))
                        }
                    ) {
                        Text(text = "Valider", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
            }
        }
    )
    {
        Column (
            modifier = Modifier
                .fillMaxHeight(.92f),
            horizontalAlignment = Alignment.CenterHorizontally
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
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                    )
                }

                Text(
                    text = "Page de Payment",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                IconButton(
                    modifier = Modifier.scale(.8f),
                    onClick = { }
                ) {
                }
            }
            Column(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 10.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color(0xFFF6F6F6)),
                horizontalAlignment = Alignment.Start
            ) {

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){

                    NumberPicker(
                        value = pickerValue,
                        range = 1..24,
                        onValueChange = {
                            pickerValue = it
                        }
                    )
                    Text(text = "heures")

                }


                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(top = 10.dp, bottom = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ){

                    Text(
                        text = "Montant total:",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${pickerValue * (details?.parking?.price?:100)} DA"
                    )

                }






                Text(
                    text = "Portfeille Electronique",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (selected.value == 1) Color(0x227136ff) else Color.White)
                        .padding(15.dp)
                        .clickable { selected.intValue = 1 },
                ) {
                    Icon(
                        painter = painterResource(id = drawable.wallet),
                        contentDescription = "wallet",
                        modifier = Modifier.padding(end = 20.dp),
                        tint = Color(0xFF7136ff),
                    )

                    Text(
                        text = "Portfeille ( 1521.00 DA )",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                    )
                }

                Text(
                    text = "Cartes",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (selected.value == 2) Color(0x227136ff) else Color.White)
                        .padding(15.dp)
                        .clickable { selected.value = 2 },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = drawable.dhahabia),
                        contentDescription = "wallet",
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .height(30.dp),
                    )

                    Text(
                        text = "Dhahbia",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (selected.value == 3) Color(0x227136ff) else Color.White)
                        .padding(15.dp)
                        .clickable { selected.value = 3 },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = drawable.cib),
                        contentDescription = "wallet",
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .height(30.dp),
                    )

                    Text(
                        text = "CIB",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                    )
                }
            }
        }

    }
}