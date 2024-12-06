package com.example.polarshuttle.ui.screen.student

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun StudentProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: StudentViewModel = hiltViewModel(),
    navHome: () -> Unit,
    navProfile: () -> Unit,
    navDashboard: () -> Unit
) {

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp), // Add space for BottomAppBar height
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text(text = "Student Profile Screen", style = MaterialTheme.typography.headlineLarge)
        }

        BottomAppBar(
            modifier = Modifier
                .align(Alignment.BottomCenter), // Align at the bottom
            containerColor = Color.Black
        ) {

            IconButton(
                onClick = { navHome() },
                modifier = Modifier.weight(1f)

            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White
                )
            }

            IconButton(
                onClick = { navDashboard() },
                modifier = Modifier.weight(1f)

            ) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = "Dashboard",
                    tint = Color.White
                )
            }

            IconButton(
                onClick = { navProfile() },
                modifier = Modifier.weight(1f)

            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = Color.White
                )
            }

        }
    }
}
