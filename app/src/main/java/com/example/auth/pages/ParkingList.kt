package com.example.exo2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.TextUnit
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.auth.BaseURL
import com.example.auth.Model.ParkingModel

import com.example.auth.R


@Composable
fun TextWithIcon(text:String, fontSize:TextUnit,color:Color ,Icon:ImageVector,iconeColor:Color = color)
{
    Row {
        Icon(
            imageVector = Icon,
            contentDescription = null, // Set to null if the icon is purely decorative
            tint = iconeColor,
            modifier = Modifier
                .padding(end = 4.dp)

        )

        Text(
            text = text,
            fontSize = fontSize,
            color = color,

        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFE8F0FE))
            .border(
                BorderStroke(1.dp, Color(0xFF4E4AF2)),
                shape = RoundedCornerShape(16.dp)
            ),
        placeholder = { Text("Search parking...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color(0xFF4E4AF2),
                modifier = Modifier.size(24.dp)
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(
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
            focusedContainerColor = Color(0xFFE8F0FE),
            unfocusedContainerColor = Color(0xFFE8F0FE),
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedLeadingIconColor = Color(0xFF4E4AF2),
            unfocusedTrailingIconColor = Color(0xFF4E4AF2),
            unfocusedPlaceholderColor = Color.Gray ,
            focusedPlaceholderColor = Color.Gray
        ),
        singleLine = true,
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun ParkingList(parkingModel: ParkingModel, navController: NavHostController)

{
    var cityMenuExpanded by remember { mutableStateOf(false) }

    val cities = listOf("alger", "oran", "tizi")
    val selectedCity = remember { mutableStateOf<String?>(null) }


    val searchQuery = remember { mutableStateOf("") }

    val context = LocalContext.current

    if(parkingModel.parks.value.isEmpty() && parkingModel.error.value.isEmpty())
        parkingModel.getAllParks()

    val parks = parkingModel.parks.value

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
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                )
            }

            Text(
                text = "List des Parkings",
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

        SearchBar(
            searchQuery = searchQuery.value,
            onSearchQueryChanged = { searchQuery.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        //Displayed if no parks were found
        if(parks.isEmpty())
            Text(
                text = "Aucun parking trouvé",
                color = Color.Gray
            )


        LazyColumn (
            modifier =  Modifier.padding(5.dp)
        ) {
            items(items = parkingModel.parks.value.filter {
                it.name.contains(searchQuery.value, ignoreCase = true)
                        /*||
                        it.adress.contains(searchQuery.value, ignoreCase = true)*/
            }) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                        .height(140.dp)
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
                            .weight(1.5f)
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = BaseURL + it.img,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(10.dp)),
                            contentDescription = "Parking Image",
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.parking_ph)
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
                                text = it.name,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold,
                            )

                            TextWithIcon(
                                text = it.city,
                                fontSize = 15.sp,
                                color = Color.Gray,
                                Icon = Icons.Default.LocationOn,

                            )/*.clickable {

                                navController.navigate(
                                    Destination.ParkingMap.getRoute(
                                        it.latitude,
                                        it.longitude
                                    )
                                )
                            }*/
                        
                            Spacer(modifier = Modifier.height(10.dp))
                            
                            Row (verticalAlignment = Alignment.Bottom){
                                
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

        /*if(parkingModel.loading.value)
           // CircularProgressIndicator()*/
        if(parkingModel.error.value.isNotEmpty()){
            Toast.makeText(context, parkingModel.error.value, Toast.LENGTH_SHORT).show()
        }

    }
}