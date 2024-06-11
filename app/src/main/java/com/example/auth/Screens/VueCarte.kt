package com.example.auth.Screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.health.connect.datatypes.ExerciseRoute
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.auth.Destination

import com.example.auth.R
import com.example.auth.ViewModels.ParkingModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun maps(parkingViewModel: ParkingModel, navController: NavHostController) {

    val context = LocalContext.current
    val initialPosition = LatLng(28.0339, 1.6596)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialPosition, 10f)
    }

    var uiSettings by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
    }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.TERRAIN))
    }
    val nearbyParks = parkingViewModel.parks.value

    LaunchedEffect(Unit) {
        if (parkingViewModel.parks.value.isEmpty() || parkingViewModel.parks.value.size <= 3)
            parkingViewModel.getAllParks()
    }



    Column (
        modifier = Modifier
            .background(Color(0xFFF6F6F6))
            .fillMaxHeight(),
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
                onClick = { /*hhh*/ }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                )
            }

            Text(
                text = "Map",
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


        GoogleMap(
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
            modifier = Modifier.fillMaxSize()

        ) {
            parkingViewModel.currentLocation.value?.let { location ->
                val Myicon = bitmapDescriptorFromVector(context, R.drawable.carmark)
                val Parkicon = bitmapDescriptorFromVector(context, R.drawable.parkmark)
                Marker(
                    state = MarkerState(position = location),
                    title = "Current Location",
                    icon = Myicon
                )

                // Update the camera position to the current location
                LaunchedEffect(location) {
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(parkingViewModel.mapInit?: location, 15f)
                    parkingViewModel.mapInit = location
                }

                // Display markers for nearby parking
                nearbyParks.forEach { parking ->

                    Marker(
                        state = MarkerState(position = LatLng(parking.latitude, parking.longitude)),
                        title = parking.name,
                        icon = Parkicon,
                        onClick = {
                            navController.navigate(
                                Destination.ParkingDetails.getRoute(parking.id))
                            true // Consume the click event
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun bitmapDescriptorFromVector(context: android.content.Context, vectorResId: Int): BitmapDescriptor {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
    vectorDrawable?.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(
        vectorDrawable?.intrinsicWidth ?: 0,
        vectorDrawable?.intrinsicHeight ?: 0,
        Bitmap.Config.ARGB_8888
    )
    val canvas = android.graphics.Canvas(bitmap)
    vectorDrawable?.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}