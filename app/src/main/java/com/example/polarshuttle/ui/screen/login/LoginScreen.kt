package com.example.polarshuttle.ui.screen.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.polarshuttle.R
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(modifier: Modifier = Modifier,
                viewModel: LoginViewModel = viewModel(),
                onDriverLoginSuccess: () -> Unit = {},
                onStudentLoginSuccess: () -> Unit = {}
){
    var showDriverLogin by rememberSaveable { mutableStateOf(false) }
    var showStudentLogin by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.bowdoin_logo2),
            contentDescription = "Bowdoin Logo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )


        if (!showDriverLogin && !showStudentLogin){
            Row(
                modifier = modifier.fillMaxWidth().padding(top = 35.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                OutlinedButton(
                    border = BorderStroke(1.dp, Color.Gray),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    onClick = {
                        showDriverLogin = true
                    }
                ){
                    Text(text = "Driver Login")
                }

                OutlinedButton(
                    border = BorderStroke(1.dp, Color.Gray),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    onClick = {
                        showStudentLogin = true
                    }
                ){
                    Text(text = "Student Login")
                }

            }
        }

        if (showDriverLogin) {
            DriverLogin(
                modifier = modifier,
                viewModel = viewModel,
                onLoginSuccess = {
                    onDriverLoginSuccess()
                },
                back = {
                    showDriverLogin = false
                }
            )
        }

        if (showStudentLogin) {
            StudentLogin(
                modifier = modifier,
                viewModel = viewModel,
                onLoginSuccess = {
                    onStudentLoginSuccess()
                },
                back = {
                    showStudentLogin = false
                }
            )
        }
    }
}

@Composable
fun DriverLogin(modifier: Modifier = Modifier,
                viewModel: LoginViewModel = viewModel(),
                onLoginSuccess: () -> Unit = {},
                back: () -> Unit = {}
){
    var email by rememberSaveable { mutableStateOf("mkang2@bowdoin.edu") }
    var password by rememberSaveable { mutableStateOf("123456789") }
    var showPassword by rememberSaveable { mutableStateOf(false) }

    var confirmationCode by rememberSaveable { mutableStateOf("1234") }
    var showConfirmationCode by rememberSaveable { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Driver Login",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            modifier = modifier
                .padding(top = 10.dp),
            fontSize = 30.sp
        )

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(0.8f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,),
            label = {
                Text(text = "E-mail", color = Color.White) },
            value = email,
            onValueChange = {
                email = it },
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Email, null, tint = Color.White)
            }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,),
            label = {
                Text(text = "Password", color = Color.White) },
            value = password,
            onValueChange = {password = it},
            singleLine = true,
            visualTransformation =
            if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (showPassword){
                    Icon(Icons.Default.Clear, null, tint = Color.White)
                } else {
                    Icon(Icons.Default.Info, null, tint = Color.White)
                }
            }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,),
            label = {
                Text(text = "Confirmation Code", color = Color.White) },
            value = confirmationCode,
            onValueChange = {confirmationCode = it},
            singleLine = true,
            visualTransformation =
            if (showConfirmationCode) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (showConfirmationCode){
                    Icon(Icons.Default.Clear, null, tint = Color.White)
                } else {
                    Icon(Icons.Default.Info, null, tint = Color.White)
                }
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(0.8f)
                .padding(top = 10.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            OutlinedButton(
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                onClick = {
                    coroutineScope.launch{
                        val result = viewModel.loginDriver(email, password)
                        if (result?.user != null){
                            onLoginSuccess()
                        }
                    }
                }
            ){
                Text(text = "Login", color = Color.Black)
            }

            OutlinedButton(
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                onClick = {
                    viewModel.registerDriver(email, password, confirmationCode)
                }
            ){
                Text(text = "Register", color = Color.Black)
            }
        }

        OutlinedButton(
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            onClick = {
                back()
            }
        ){
            Text(text = "Back", color = Color.Black)
        }

        when (viewModel.loginUiState) {
            is LoginUiState.Init -> {}
            is LoginUiState.Loading -> CircularProgressIndicator()
            is LoginUiState.LoginSuccess -> Text("Login OK", color = Color.White)
            is LoginUiState.RegisterSuccess -> Text("Register OK", color = Color.White)
            is LoginUiState.Error -> Text(
                text = "Error : ${
                    (viewModel.loginUiState as LoginUiState.Error).errorMessage
                }",
                color = Color.White
            )
        }
    }

}


@Composable
fun StudentLogin(modifier: Modifier = Modifier,
                 viewModel: LoginViewModel = viewModel(),
                 onLoginSuccess: () -> Unit = {},
                 back: () -> Unit = {}
){
    var email by rememberSaveable { mutableStateOf("richiedix@gmail.com") }
    var password by rememberSaveable { mutableStateOf("123456789") }
    var showPassword by rememberSaveable { mutableStateOf(false) }


    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Student Login",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(top = 10.dp),
            fontSize = 30.sp
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
            ),
            label = {
                Text(text = "E-mail", color = Color.White)
            },
            value = email,
            onValueChange = {
                email = it
            },
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Email, null, tint = Color.White)
            }
        )


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
            ),
            label = {
                Text(text = "Password", color = Color.White)
            },
            value = password,
            onValueChange = { password = it },
            singleLine = true,
            visualTransformation =
            if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (showPassword) {
                    Icon(Icons.Default.Clear, null, tint = Color.White)
                } else {
                    Icon(Icons.Default.Info, null, tint = Color.White)
                }
            }
        )


        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                onClick = {
                    coroutineScope.launch {
                        val result = viewModel.loginStudent(email, password)
                        if (result?.user != null) {
                            onLoginSuccess()
                        }
                    }
                }
            ) {
                Text(text = "Login", color = Color.Black)
            }

            OutlinedButton(
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                onClick = {
                    viewModel.registerStudent(email, password)
                }
            ) {
                Text(text = "Register", color = Color.Black)
            }
        }

        OutlinedButton(
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            onClick = {
                back()
            }
        ){
            Text(text = "Back", color = Color.Black)
        }

        when (viewModel.loginUiState) {
            is LoginUiState.Init -> {}
            is LoginUiState.Loading -> CircularProgressIndicator()
            is LoginUiState.LoginSuccess -> Text("Login OK", color = Color.White)
            is LoginUiState.RegisterSuccess -> Text("Register OK", color = Color.White)
            is LoginUiState.Error -> Text(
                text = "Error : ${
                    (viewModel.loginUiState as LoginUiState.Error).errorMessage
                }", color = Color.White
            )
        }
    }
}

