package com.example.auth.Screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.auth.AuthManager
import com.example.auth.ViewModels.ReservationModel
import com.example.auth.Destination
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.util.Date
import java.util.Hashtable

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayTicket(navController: NavHostController, id: Int, reservationModel: ReservationModel){

    val context = LocalContext.current

    reservationModel.getReservations(id, context)
    val details =   reservationModel.details.value

    val departureTime =  if(details != null) Date(details.arrivalTime.time + details.duration * 60*60*1000) else Date()


        Column (
            modifier = Modifier
                .background(Color(0xFFF6F6F6))
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
                    onClick = { navController.navigate(Destination.MesReservation.route) }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                    )
                }

                Text(
                    text = "E-Ticket",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                IconButton(
                    modifier = Modifier.scale(.8f),
                    onClick = { }
                ) {
                }
            }


            Column (
                modifier = Modifier
                    .padding(20.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
            ){
                Image(
                    painter = BitmapPainter(generateQRCode(id.toString(),1024).asImageBitmap()),
                    contentDescription = "QR Code"
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Box(
                        modifier = Modifier
                            .offset((-20).dp, 0.dp)
                            .height(40.dp)
                            .padding(start = 2.dp)
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .background(Color(0xFFF6F6F6))
                    )

                    Box(
                        modifier = Modifier
                            .offset(20.dp, 0.dp)
                            .height(40.dp)
                            .padding(start = 2.dp)
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .background(Color(0xFFF6F6F6))
                    )

                }

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                ){
                    Column(
                        modifier = Modifier.weight(1f)
                    ){
                        Text(
                            text = "Parking:",
                            color = Color.Gray,
                            fontSize = 20.sp,
                        )
                        Text(
                            modifier = Modifier.height(26.dp),
                            text = "${details?.parking?.name?.replace("Parking", "")}",
                            color = Color.Black,
                            fontSize = 20.sp,
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Entré:",
                            color = Color.Gray,
                            fontSize = 20.sp,
                        )
                        Text(
                            text = "${details?.arrivalTime?.hours}:${if(details?.arrivalTime?.minutes in 0..9)  "0${details?.arrivalTime?.minutes}" else "${details?.arrivalTime?.minutes}"}",
                            color = Color.Black,
                            fontSize = 20.sp,
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Date:",
                            color = Color.Gray,
                            fontSize = 20.sp,
                        )
                        Text(
                            text = "${details?.arrivalTime?.let { formatTime(it).split("à")[0] } }",
                            color = Color.Black,
                            fontSize = 20.sp,
                        )

                    }
                    Column(
                        modifier = Modifier.weight(1f)
                    ){
                        Text(
                            text = "Conducteur",
                            color = Color.Gray,
                            fontSize = 20.sp,
                        )
                        Text(
                            text = "${AuthManager.getUserName(context)}",
                            color = Color.Black,
                            fontSize = 20.sp,
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Sortie:",
                            color = Color.Gray,
                            fontSize = 20.sp,
                        )
                        Text(
                            text = "${departureTime.hours}:${if(departureTime.minutes in 0..9)  "0${departureTime.minutes}" else "${departureTime.minutes}"}",
                            color = Color.Black,
                            fontSize = 20.sp,
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Montant:",
                            color = Color.Gray,
                            fontSize = 20.sp,
                        )
                        Text(
                            text = "${(details?.parking?.price ?: 100 )* (details?.duration?: 1)} DA",
                            color = Color.Black,
                            fontSize = 20.sp,
                        )
                    }
                }

            }



        }
}



fun generateQRCode(content: String, size: Int): Bitmap {
    val hints = Hashtable<EncodeHintType, Any>()
    hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
    val writer = QRCodeWriter()
    val bitMatrix: BitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size, hints)
    val width = bitMatrix.width
    val height = bitMatrix.height
    val pixels = IntArray(width * height)

    for (y in 0 until height) {
        for (x in 0 until width) {
            pixels[y * width + x] = if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE
        }
    }

    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    bmp.setPixels(pixels, 0, width, 0, 0, width, height)
    return bmp
}
