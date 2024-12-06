package com.example.polarshuttle.ui.screen.driver

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.polarshuttle.data.Request
import com.example.polarshuttle.data.Status

@Composable
fun DriverHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: DriverViewModel = hiltViewModel(),
    navHome: () -> Unit,
    navDashboard: () -> Unit,
    navProfile: () -> Unit
) {

    val allRequests by viewModel.requests.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp), // Add space for BottomAppBar height
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            if (allRequests.isEmpty()) {
                Text(
                    text = "No Incoming Requests!",
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            } else {
                LazyColumn {
                    items(allRequests) { item ->
                        RequestCard(
                            request = item,
                            onAccept = {
                                viewModel.acceptRequest(item)
                            }
                        )
                    }
                }
            }
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


@Composable
fun RequestCard(
    request: Request,
    onAccept: (Request) -> Unit,
){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp).fillMaxWidth()
    ) {

        var expanded by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.padding(20.dp).animateContentSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Request",
                    tint = Color.White,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 10.dp)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = "Time: ${request.time}",
                        style = TextStyle(
                            fontSize = 12.sp,
                        )
                    )

                    Text(
                        text = "Riders: ${request.numberOfRiders}",
                        style = TextStyle(
                            fontSize = 12.sp,
                        )
                    )

                    Text(
                        text = "Pick up: ${request.pickUpLocation}",
                        style = TextStyle(
                            fontSize = 12.sp,
                        )
                    )

                    Text(
                        text = "Drop off: ${request.dropOffLocation}",
                        style = TextStyle(
                            fontSize = 12.sp,
                        )
                    )




                    Text(
                        text = "Date: ${request.date}",
                        style = TextStyle(
                            fontSize = 12.sp,
                        )
                    )

                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Status: ${request.status}",
                        style = TextStyle(
                            fontSize = 12.sp,
                        )

                    )


                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Accept",
                        modifier = if (request.status == Status.PENDING) {
                            Modifier.clickable {
                                onAccept(request)
                            }
                        } else {
                            Modifier
                        },
                        tint = Color.Gray
                    )


                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = if (expanded) {
                                "Less"
                            } else {
                                "More"
                            }
                        )
                    }
                }
            }
            if (expanded) {
                Text(
                    text = "Requester: ${request.requester}",
                    style = TextStyle(
                        fontSize = 12.sp
                    )
                )

                Text(
                    text = "DriverID: ${request.driverId}",
                    style = TextStyle(
                        fontSize = 12.sp
                    )
                )


            }
        }
    }
}


