package com.example.auth.Screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.TextUnit
import androidx.navigation.NavHostController
import com.example.auth.CoilAsyncImage
import com.example.auth.Destination
import com.example.auth.IMAGE_URL
import com.example.auth.SkeletonLoading.ParkingSkeleton
import com.example.auth.ViewModels.ParkingModel
import com.example.auth.R
import androidx.compose.material3.IconButton as IconButton1



@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onClickIcone: ()->Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = searchQuery,
        onValueChange = {
            Log.d("SearchBar", "Avant onValueChange: $it")
            onSearchQueryChanged(it)
            Log.d("SearchBar", "Après onValueChange: $it")
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                BorderStroke(1.dp, Color(0xFF4E4AF2)),
                shape = RoundedCornerShape(16.dp)
            ),
        placeholder = { Text("Rechercher un parking...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color(0xFF4E4AF2),
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onClickIcone() }
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton1(
                    onClick = { onSearchQueryChanged("") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear search",
                        tint = Color(0xFF4E4AF2),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = Color.Black,
            unfocusedLeadingIconColor = Color(0xFF4E4AF2),
            unfocusedTrailingIconColor = Color(0xFF4E4AF2),
            unfocusedPlaceholderColor = Color.Gray ,
            focusedPlaceholderColor = Color.Gray,
        ),
        singleLine = true,
        shape = RoundedCornerShape(16.dp)
    )
}





@Composable
fun TextWithIcon(text:String, fontSize:TextUnit,color:Color ,Icon:ImageVector, iconeColor:Color = color)
{
    Row {
        Icon(
            imageVector = Icon,
            contentDescription = null, // Set to null if the icon is purely decorative
            tint = iconeColor,
        )

        Text(
            text = text,
            fontSize = fontSize,
            color = color,
        )
    }

}







@Composable
fun ParkingList(parkingModel: ParkingModel, navController: NavHostController, searched: String = "") {

    val searchQuery = remember { mutableStateOf(searched) }
    val searching = remember {
        mutableStateOf(searched.isNotEmpty())
    }

    var priceRange by remember { mutableStateOf(50f..500f) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (parkingModel.parks.value.isEmpty() || parkingModel.parks.value.size <= 3)
            parkingModel.getAllParks()
    }
    val parks = parkingModel.parks.value.filter {
        (it.name.contains(searchQuery.value, ignoreCase = true) ||
                it.city.contains(searchQuery.value, ignoreCase = true)) &&
                    it.price.toFloat() in priceRange
    }

    Column(
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
            IconButton1(
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

            if (searching.value)
                SearchBar(
                    searchQuery = searchQuery.value,
                    onSearchQueryChanged = {
                        Log.d("ParkingList", "Nouvelle valeur de recherche: $it")
                        searchQuery.value = it
                    },
                    onClickIcone = { searching.value = false },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            else {
                Text(
                    text = "List des Parkings",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                IconButton1(
                    modifier = Modifier
                        .scale(.8f)
                        .border(width = 1.dp, color = Color.LightGray, shape = CircleShape)
                        .background(Color.White, shape = CircleShape),
                    onClick = { searching.value = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                    )
                }
            }
        }

        Text(
            text= "prix:"
        )

        Column {
            RangeSlider(
                modifier =  Modifier
                    .fillMaxWidth(.9f),
                value = priceRange,
                steps = 15,
                onValueChange = { range -> priceRange = range },
                valueRange = 50f..500f,
                onValueChangeFinished = {

                },
            )
            Text(text = priceRange.toString())
        }


        //Displayed if no parks were found
        if (parks.isEmpty() and !parkingModel.loading.value)

            Column(
                modifier = Modifier
                    .fillMaxHeight(.9f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Aucun parking trouvé",
                    color = Color.Gray
                )
            }


        LazyColumn(
            modifier = Modifier
                .padding(5.dp)
        ) {
            items(parks) {


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
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
                                text = it.name,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                            )

                            TextWithIcon(
                                text = it.city,
                                fontSize = 15.sp,
                                color = Color.Gray,
                                Icon = Icons.Default.LocationOn
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

        if (parkingModel.loading.value)
            ParkingSkeleton()
        if (parkingModel.error.value.isNotEmpty()) {
            Toast.makeText(context, parkingModel.error.value, Toast.LENGTH_LONG).show()
        }

    }


}