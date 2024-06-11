package com.example.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.auth.SkeletonLoading.ParkingSkeleton
import com.example.auth.ViewModels.ParkingModel
import com.example.auth.Screens.TextWithIcon
import com.example.auth.SkeletonLoading.VerticalParkingSkeleton
import com.google.android.gms.maps.model.LatLng


@Composable
fun CityDropdownMenu(
    cities: List<String>,
    onCitySelected: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    DropdownMenu(
        expanded = true, // Toujours ouvert
        onDismissRequest = onDismissRequest
    ) {
        cities.forEach { city ->
            DropdownMenuItem(
                text = { Text(text = city) },
                onClick = {
                    onCitySelected(city)
                    onDismissRequest()
                })
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(parkingModel: ParkingModel, navController: NavHostController)

{

    var address by remember { mutableStateOf<String?>("Posision non reconu!") }


    val context = LocalContext.current

    val searchValue = remember {
        mutableStateOf("")
    }



    LaunchedEffect(Unit) {
        if (parkingModel.parks.value.isEmpty())
            parkingModel.getAllParks(3)
        if (parkingModel.recomended.value.isEmpty())
            parkingModel.getAllParks(3,false)
    }

    address =  getAddressFromLocation(context, parkingModel.currentLocation.value)

    val parks = parkingModel.parks.value

    Column (
        modifier = Modifier
            .background(Color(0xFFF6F6F6))
            .fillMaxHeight(.92f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .clip(RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp))
                .background(Color(0xFF7136ff))
                .padding(10.dp),
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                text = "Localisation",
                fontSize = 13.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(5.dp))

            TextWithIcon(
                text = "$address",
                fontSize = 20.sp,
                color = Color.White,
                Icon = Icons.Default.LocationOn
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(horizontal = 5.dp)
                    .padding(bottom = 12.dp)
                    .clip(RoundedCornerShape(15.dp)),

                value = searchValue.value,
                onValueChange = { searchValue.value = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "searchIcon",
                        tint = Color(0xFF7136ff)
                    )
                },
                placeholder = { Text("Search") },
                colors = TextFieldDefaults
                    .textFieldColors(containerColor = Color(0xFFF6F6F6)),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        navController.navigate(Destination.ParkingListSearch.getRoute(searchValue.value))
                    }
                )
            )


        }

        //Displayed if no parks were found
        if (parks.isEmpty() and !parkingModel.loading.value){
            Column(
                modifier = Modifier
                    .fillMaxHeight(.9f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(id = R.drawable.parking_ph) , contentDescription = "",
                    alpha = .7f,
                )
                Spacer(modifier = Modifier.height(10.dp),)
                Text(
                    text = "impossible de récupérer les parkings",
                    color = Color.Gray
                )
            }

            return
        }





        LazyColumn(
            modifier = Modifier
                .padding(5.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Parking les plus populaires: ",
                        fontSize = 15.sp,
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .clickable {
                                navController.navigate(Destination.ParkingList.route)
                            },
                        text = "voir tout",
                        color = Color.Blue,
                        fontSize = 15.sp,
                    )
                }

                if(parkingModel.loading.value)
                    VerticalParkingSkeleton()

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    items(parkingModel.recomended.value) {
                        Card(
                            modifier = Modifier
                                .width(300.dp)
                                .padding(8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(Color.White)
                                    .padding(5.dp)
                                    .clickable {
                                        navController.navigate(
                                            Destination.ParkingDetails.getRoute(
                                                it.id
                                            )
                                        )
                                    },
                            ) {
                                CoilAsyncImage(
                                    model = IMAGE_URL + it.img,
                                    modifier = Modifier
                                        .aspectRatio(1.5f)
                                        .clip(RoundedCornerShape(10.dp)),
                                    contentDescription = "Parking Image",
                                    contentScale = ContentScale.Crop,
                                    placeholder = R.drawable.parking_ph,
                                    error = R.drawable.parking_ph,
                                )

                                Box(
                                    modifier = Modifier
                                        .background(Color(0xFFF6F6F6))
                                        .padding(horizontal = 5.dp)
                                        .clip(RoundedCornerShape(10.dp)),
                                ) {
                                    Text(
                                        text = "car parking",
                                        fontSize = 15.sp,
                                        color = Color(0xFF7136ff),
                                    )
                                }


                                Text(
                                    text = it.name,
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.height(50.dp)
                                )

                                TextWithIcon(
                                    text = it.city,
                                    fontSize = 15.sp,
                                    color = Color.Gray,
                                    Icon = Icons.Default.LocationOn,
                                    modifier = Modifier.clickable {
                                        parkingModel.mapInit = LatLng(it.latitude, it.longitude)
                                        navController.navigate(Destination.VueCarte.route)
                                    }
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(verticalAlignment = Alignment.Bottom) {

                                    Text(
                                        text = "${it.price}DA",
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


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Parking les plus proches: ",
                        fontSize = 15.sp,
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .clickable {
                                navController.navigate(Destination.ParkingList.route)
                            },
                        text = "voir tout",
                        color = Color.Blue,
                        fontSize = 15.sp,
                    )
                }
                if(parkingModel.loading.value)
                    ParkingSkeleton()
            }


                items(parks.take(3)) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .padding(vertical = 8.dp, horizontal = 2.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(10.dp))
                                .background(color = Color.White)
                                .clickable {
                                    navController.navigate(Destination.ParkingDetails.getRoute(it.id))
                                },

                            )
                        {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1.2f)
                                    .padding(5.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CoilAsyncImage(
                                    model = IMAGE_URL + it.img,
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .clip(RoundedCornerShape(10.dp)),
                                    contentDescription = "Parking Image",
                                    contentScale = ContentScale.Crop,
                                    placeholder = R.drawable.parking_ph,
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
                                ) {
                                    Text(
                                        text = "car parking",
                                        fontSize = 15.sp,
                                        color = Color(0xFF7136ff),
                                    )
                                }


                                Text(
                                    modifier = Modifier.height(50.dp),
                                    text = it.name,
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold,
                                )

                                TextWithIcon(
                                    text = it.city,
                                    fontSize = 15.sp,
                                    color = Color.Gray,
                                    Icon = Icons.Default.LocationOn,
                                    modifier = Modifier.clickable {
                                        parkingModel.mapInit = LatLng(it.latitude, it.longitude)
                                        navController.navigate(Destination.VueCarte.route)
                                    }
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(verticalAlignment = Alignment.Bottom) {

                                    Text(
                                        text = "${it.price}DA",
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

}