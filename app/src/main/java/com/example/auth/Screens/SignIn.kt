package com.example.auth.Screens

import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.auth.AuthManager
import com.example.auth.R
import com.example.auth.ViewModels.AuthVIewModel
import com.example.auth.R.drawable
import com.example.auth.Destination
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplaySignIn(navController: NavHostController, authModel: AuthVIewModel){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val token = stringResource(id = R.string.client_id)
    val isLoggedIn = AuthManager.isLoggedIn(context)
    LaunchedEffect(Unit) {
        if (isLoggedIn) {
            navController.navigate(Destination.MesReservation.route)
        }
    }
    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { authResult, isNewUser ->
            if (!isNewUser) {
                //si le user est nouveau on l'insere dans la bdd et on conncte le
                val userEmail = authResult.user!!.email!!
                val firstname = authResult.user!!.displayName?.split(" ")?.get(0) ?: ""
                val lastname = authResult.user!!.displayName?.split(" ")?.get(1) ?: ""
                //val newUser = RegisterRequest(userEmail, generateRandomPassword(), firstname, lastname)
                //userModel.registerUser(newUser)
                user = authResult.user
            } else {
                // Utilisateur existant, connecte-le
                user = authResult.user

            }

        },
        onAuthError = {
            user = null
        },

        userModel = authModel
    )
    Column (
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),

    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(.8f)
                //.fillMaxHeight(.28f)
                .padding(top = 100.dp, start = 20.dp)


            //.absoluteOffset(x=10.dp,y=100.dp)
        ) {
            Text(

                text = "Login to your Account ",
                letterSpacing = 5.sp,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 45.sp,
                color = Color(0xFF00000F),
                style = TextStyle(lineHeight = 70.sp)
            )
            /*Text(
              // modifier = Modifier.absoluteOffset(x=10.dp,y=100.dp),
                text = " Account",
                letterSpacing = 5.sp,
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp,
                color = Color(0xFF00000F)
            )*/

        }
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(1f)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(1f),
                value = email,
                onValueChange = { email = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email, contentDescription = "emailIcon",
                        tint = Color(0xFF000000)
                    )
                },
                label = { Text("Email") },
                placeholder = {
                    Text(
                        text = "Email",
                        color = Color(0xFF000000),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )
                },
                colors = TextFieldDefaults
                    .textFieldColors(containerColor = Color(0xFFF6F6F6))
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(1f),
                value = password,
                onValueChange = { password = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "pwdIcon",
                        tint = Color(0xFF000000)
                    )
                },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                placeholder = {
                    Text(
                        text = "Password",
                        color = Color(0xFF000000),
                        style = TextStyle(
                            fontSize = 14.sp,

                            )
                    )
                },
                colors = TextFieldDefaults
                    .textFieldColors(containerColor = Color(0xFFF6F6F6))

            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            authModel.login(email, password, context) { success, error ->
                                if (success) {
                                    navController.popBackStack()
                                } else {
                                    errorMessage = error ?: "Failed to register"
                                }
                            }
                        } else {
                            errorMessage = "Please enter email and password"
                        }
                    },
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF773FFF)),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Sign In",
                        fontSize = 15.sp,
                    )
                }


            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 70.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Divider(
                        color = Color(0xFFAFB0AF),
                        thickness = 1.dp,
                        modifier = Modifier
                            .width(100.dp)
                        //.padding(vertical = 8.dp)
                    )
                    Text(
                        text = " Or Sign in with ",
                        color = Color(0xFFAFB0AF)
                    )

                    Divider(
                        color = Color(0xFFAFB0AF),
                        thickness = 1.dp,
                        modifier = Modifier
                            .width(100.dp)
                        //.padding(vertical = 8.dp)
                    )

                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (user == null) {
                        Spacer(modifier = Modifier.height(35.dp))
                        ElevatedButton(onClick = {
                            val gso =
                                GoogleSignInOptions
                                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken(token)
                                    .requestEmail()
                                    .build()
                            val googleSignInClient = GoogleSignIn
                                .getClient(context, gso)
                            launcher
                                .launch(googleSignInClient.signInIntent)
                        },
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxSize()
                                .padding(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            )
                        ) {
                            Image(painter = painterResource(id = R.drawable.google),
                                contentDescription ="",
                                modifier = Modifier.size(45.dp),
                                contentScale = ContentScale.Fit )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text("Sign in via Google",
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 15.sp,
                                letterSpacing = 0.1.em)
                        }
                    } else {
                        //navController.popBackStack()
                        navController.navigate(Destination.MesReservation.route) {

                        }

                        /*Text("Hi, ${user!!.displayName}!",
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp, color = Color.Black)
                        /*navController.navigate(DestinationPath.H.route) {
                            popUpTo(DestinationPath.Home.route)
                        }*/
                        Spacer(modifier = Modifier.height(30.dp))

                        Button(onClick = {
                            Firebase.auth.signOut()
                            user = null
                        },
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxSize()
                                .padding(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            )) {
                            Text("Log out",
                                fontFamily = FontFamily.SansSerif,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 15.sp,
                                letterSpacing = 0.1.em)
                        }*/
                    }

                Spacer(modifier = Modifier.height(50.dp))
                Row(


                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)

                ) {
                    Text(text = "Don't have an account? ")
                    Text(text = " Sing Up",
                        color = Color(0xFF773FFF),
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Destination.SignUp.route)
                            })
                }


            }


        }


    }
}
}
@Composable
    fun rememberFirebaseAuthLauncher(
        onAuthComplete: (AuthResult, Boolean) -> Unit, // Ajoutez un booléen pour indiquer si l'utilisateur est nouveau
        onAuthError: (ApiException) -> Unit,
        userModel: AuthVIewModel
    ): ManagedActivityResultLauncher<Intent, ActivityResult> {
        val scope = rememberCoroutineScope()
        return rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("GoogleAuth", "account $account")
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                scope.launch {
                    val authResult = Firebase.auth.signInWithCredential(credential).await()
                    val gmail = authResult.user!!.email!!
                    val isNewUser = userModel.checkEmail (gmail)
                    onAuthComplete(authResult, isNewUser) // Pass the boolean indicating if the user is new
                }
            } catch (e: ApiException) {
                Log.d("GoogleAuth", e.toString())
                onAuthError(e)
            }
        }
    }
fun generateRandomPassword(length: Int = 10): String {
    val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}
