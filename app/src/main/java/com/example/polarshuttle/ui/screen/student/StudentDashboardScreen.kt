package com.example.polarshuttle.ui.screen.student

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.polarshuttle.ui.navigation.MainNavigation
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun StudentDashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: StudentViewModel = hiltViewModel(),
    navHome: () -> Unit,
    navDashboard: () -> Unit,
    navProfile: () -> Unit
) {

    val requests by viewModel.requests.collectAsState()
    val myRequests by viewModel.myRequests.collectAsState()

    val initialPosition = LatLng(43.907909233855804, -69.96402925715876)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialPosition, 14f)
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxWidth().weight(2f),
                cameraPositionState = cameraPositionState
            ) {

                Marker(
                    state = MarkerState(position = LatLng(43.907909233855804, -69.96402925715876)),
                    title = "Brunswick, ME",
                    snippet = "Bowdoin College"
                )
            }

            if (myRequests.isNotEmpty()){
                StudentRequestCard(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    currentRequest = myRequests[0],
                    queuePosition = requests.indexOf(myRequests[0]) + 1,
                    onCancel = { viewModel.cancelRequest(it) }
                )
            } else {
                StudentRequestCard(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    currentRequest = null,
                    queuePosition = -1,
                    onCancel = { }
                )
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
fun StudentRequestCard(
    modifier: Modifier,
    currentRequest: Request?,
    queuePosition: Int,
    onCancel: (Request) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = modifier
    ) {

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Request",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 20.dp)
                )
                Column(
                    modifier = Modifier.padding(20.dp).animateContentSize()
                ) {
                    if (currentRequest != null) {
                        Text(
                            text = "Request #$queuePosition",
                            style = TextStyle(
                                fontSize = 12.sp,
                            )
                        )
                        Text(
                            text = "Pick Up at: ${currentRequest.pickUpLocation}"
                        )
                        Text(
                            text = "Drop Off at: ${currentRequest.dropOffLocation}"
                        )
                    } else {
                        Text(
                            "Submit a Shuttle Request!",
                            style =  MaterialTheme.typography.titleMedium

                        )
                    }


                }

                Text(
                    text = if (currentRequest != null) {
                        "Status: ${currentRequest.status}"
                    } else {
                        "No Current Request"
                    },
                    style = TextStyle(
                        fontSize = 12.sp,
                    )

                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
            ){
                if (currentRequest != null) {
                    Button(
                        onClick = {
                            onCancel(currentRequest)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ){
                        Text(text = "Cancel")
                    }
                }
            }
        }
    }
}
