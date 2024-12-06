package com.example.polarshuttle.ui.screen.driver

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.polarshuttle.data.Request
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.sql.Driver

@Composable
fun DriverDashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DriverViewModel = hiltViewModel(),
    navHome: () -> Unit,
    navProfile: () -> Unit,
    navDashboard: () -> Unit
) {
    val currentRequest by viewModel.currentRequest.collectAsState()
    val acceptedRequests by viewModel.acceptedRequests.collectAsState()

    val allRequests by viewModel.requests.collectAsState()


    val initialPosition = LatLng(43.907909233855804, -69.96402925715876)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialPosition, 14f)
    }


    Box(
        modifier = modifier.fillMaxSize(),
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
            ){

                Marker(
                    state = MarkerState(position = LatLng(43.907909233855804, -69.96402925715876)),
                    title = "Brunswick, ME",
                    snippet = "Bowdoin College"
                )
            }

            // Request Cards
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                // Current Request Card
                DriverCurrentRequestCard(
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    currentRequest = currentRequest,
                    onComplete = {
                        currentRequest?.let{
                            viewModel.completeRequest(it)
                        }
                    },
                    onCancel ={
                        currentRequest?.let{
                            viewModel.pendRequest(it)
                        }
                    }
                )

                // Info Card for Request Count
                DriverInfoCard(
                    modifier = Modifier.weight(1f).padding(start = 8.dp),
                    numRequests = allRequests.size,
                    numAcceptedRequests = acceptedRequests.size
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
fun DriverCurrentRequestCard(
    modifier: Modifier = Modifier,
    currentRequest: Request?,
    onComplete: () -> Unit,
    onCancel: () -> Unit
){

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "Current Request: ",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                if (currentRequest != null) "${currentRequest.pickUpLocation} -> ${currentRequest.dropOffLocation}"
                else "No Current Request"
                ,
                style = MaterialTheme.typography.bodySmall
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ){

                IconButton(
                    enabled = currentRequest != null,
                    onClick = { onComplete() },
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Completed",
                        tint = Color.Black
                    )
                }

                IconButton(
                    enabled = currentRequest != null,
                    onClick = { onCancel() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Cancel",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun DriverInfoCard(
    modifier: Modifier = Modifier,
    numRequests: Int,
    numAcceptedRequests: Int
){
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ){
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ){
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,

                ){
                    Text("Total Requests")
                    Text(numRequests.toString())
                }

                Column(
                    modifier = Modifier.weight(1f)
                ){
                    Text("Accepted Requests")
                    Text(numAcceptedRequests.toString())
                }
            }
        }
    }
}


