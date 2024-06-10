
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp

import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.auth.AuthManager
import com.example.auth.Destination

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.TextFieldDefaults



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfile(
    username: String,
    email: String,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Ajoute un padding de 16 dp autour de la colonne
        horizontalAlignment = Alignment.CenterHorizontally,

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
                onClick = {/*tt*/}
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                )
            }

            Text(
                text = "Profile",
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
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                )
            }
        }


        // Profile Icon
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = "Profile Icon",
            modifier = Modifier
                .size(64.dp) // Définit la taille de l'icône à 64 dp
                .clip(CircleShape) // Applique une forme circulaire à l'icône
                .background(Color.White) // Définit un arrière-plan gris pour l'icône
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "$username",
            modifier = Modifier
                .padding(bottom = 16.dp),

            style = TextStyle(
                fontSize = 20.sp, // Augmente la taille de la police
                fontWeight = FontWeight.Bold // Met le texte en gras
            )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(.9f)
        ) {
            TextField(value = "username: $username",
                onValueChange ={it},
                placeholder = {Text(text = "Leica  M Typ 240",
                    style = TextStyle(fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black))},
                colors = TextFieldDefaults
                    .textFieldColors(containerColor = Color.Transparent))

            TextField(value = "username: $username",
                onValueChange ={it},
                placeholder = {Text(text = "Leica  M Typ 240",
                    style = TextStyle(fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black))},
                colors = TextFieldDefaults
                    .textFieldColors(containerColor = Color.Transparent))



        }

        Text(
            text = "Email: $email",
            modifier = Modifier
                .padding(top = 16.dp, bottom = 32.dp),
            style = TextStyle(fontSize = 16.sp)
        )



        // Logout Button
        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF7136ff)
            ),
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(0.5f)
        ) {
            Icon(
                imageVector = Icons.Filled.ExitToApp,
                contentDescription = "Logout"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Logout")
        }
    }
}

@Composable
fun DisplayProfile(navController: NavHostController) {
    val context = LocalContext.current
    val isLoggedIn = AuthManager.isLoggedIn(context)

    if (isLoggedIn) {
        val username = AuthManager.getUserName(context)
        val email = AuthManager.getUserEmail(context)

        if (username != null) {
            UserProfile(
                username = username,
                email = email ?: "",
                onLogout = {
                    AuthManager.clearCredentials(context)
                    navController.navigate(Destination.SignIn.route)
                }
            )
        }
    } else {
        // Si l'utilisateur n'est pas connecté, le redirige vers l'écran de connexion
        LaunchedEffect(Unit) {
            navController.navigate(Destination.SignIn.route)
        }
    }
}