package com.example.polarshuttle.ui.screen.student

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.polarshuttle.data.Request
import com.example.polarshuttle.data.ShuttleLocation
import com.example.polarshuttle.data.Status
import com.example.polarshuttle.ui.navigation.MainNavigation
import com.example.polarshuttle.ui.screen.driver.DriverViewModel
import java.util.Date


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StudentHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: StudentViewModel = hiltViewModel(),
    navHome: () -> Unit,
    navDashboard: () -> Unit,
    navProfile: () -> Unit
) {

    var showAddDialog by rememberSaveable { mutableStateOf(false) }

    var requestToEdit: Request? by rememberSaveable { mutableStateOf(null) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Text(text = "Student Home Screen",
            style = MaterialTheme.typography.titleLarge
        )

        if (!showAddDialog){
            Button(
                onClick = { showAddDialog = true },
                modifier = Modifier.align(Alignment.Center)
            ){
                Text("New Shuttle Request")
            }
        } else {
            RequestDialog(
                viewModel = viewModel,
                requestToEdit = requestToEdit,
                onCancel = { showAddDialog = false }
            )
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RequestDialog(
    viewModel: StudentViewModel,
    requestToEdit: Request? = null,
    onCancel: () -> Unit
) {
    // State variables for form inputs
    var requester by rememberSaveable { mutableStateOf(requestToEdit?.requester ?: "") }
    var numRiders by rememberSaveable { mutableStateOf(requestToEdit?.numberOfRiders?.toString() ?: "") }
    var pickUpLocation by rememberSaveable { mutableStateOf(requestToEdit?.pickUpLocation ?: ShuttleLocation._52HARPSWELL) }
    var dropOffLocation by rememberSaveable { mutableStateOf(requestToEdit?.dropOffLocation ?: ShuttleLocation._52HARPSWELL) }
    var requesterErrorState by rememberSaveable { mutableStateOf(true) } // Default to invalid
    var numRidersErrorState by rememberSaveable { mutableStateOf(true) } // Default to invalid

    val shuttleLocationList = ShuttleLocation.entries

    // Validation Functions
    fun validateName(input: String) {
        requesterErrorState = !(input.matches(Regex("^[a-zA-Z ]*$")) && input.isNotEmpty())
    }

    fun validateNumber(input: String) {
        numRidersErrorState = !(input.toIntOrNull()?.let { it in 1..8 } ?: false)
    }

    // Initial Validation
    LaunchedEffect(requester, numRiders) {
        validateName(requester)
        validateNumber(numRiders)
    }

    Dialog(onDismissRequest = { onCancel() }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(6.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = if (requestToEdit == null) "New Ride Request" else "Edit Ride Request",
                    style = MaterialTheme.typography.titleMedium
                )

                // Requester Name Input
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Requester Name") },
                    value = requester,
                    onValueChange = {
                        requester = it
                        validateName(it)
                    },
                    isError = requesterErrorState,
                    supportingText = {
                        if (requesterErrorState) {
                            Text("Please enter a valid name with only letters.")
                        }
                    },
                    trailingIcon = {
                        if (requesterErrorState) {
                            Icon(
                                Icons.Filled.Warning,
                                contentDescription = "Error",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )

                // Number of Riders Input
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Number of Riders") },
                    value = numRiders,
                    onValueChange = {
                        numRiders = it
                        validateNumber(it)
                    },
                    isError = numRidersErrorState,
                    supportingText = {
                        if (numRidersErrorState) {
                            Text("Please enter a number between 1 and 8.")
                        }
                    },
                    trailingIcon = {
                        if (numRidersErrorState) {
                            Icon(
                                Icons.Filled.Warning,
                                contentDescription = "Error",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                // Pick-Up and Drop-Off Locations
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Spinner(
                        label = "Pick Up Location",
                        list = shuttleLocationList,
                        preselected = pickUpLocation,
                        onSelectionChanged = { selected -> pickUpLocation = selected },
                        modifier = Modifier.weight(1f)
                    )

                    Spinner(
                        label = "Drop Off Location",
                        list = shuttleLocationList,
                        preselected = dropOffLocation,
                        onSelectionChanged = { selected -> dropOffLocation = selected },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Action Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { onCancel() }) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = {
                            viewModel.addRequest(
                                requester = requester,
                                numberOfRiders = numRiders.toInt(),
                                pickUpLocation = pickUpLocation,
                                dropOffLocation = dropOffLocation
                            )
                            onCancel()
                        },
                        enabled = !requesterErrorState && !numRidersErrorState // Enabled only if both fields are valid
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    }
}




@Composable
fun Spinner(
    modifier: Modifier = Modifier,
    label: String,
    list: List<ShuttleLocation>,
    preselected: ShuttleLocation = ShuttleLocation.BOC,
    onSelectionChanged: (ShuttleLocation) -> Unit
) {
    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) }

    OutlinedCard(
        modifier = modifier.clickable {
            expanded = !expanded
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {

            Text(
                selected.name,
                style = MaterialTheme.typography.bodySmall,
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            list.forEach { listEntry ->
                DropdownMenuItem(
                    onClick = {
                        selected = listEntry
                        expanded = false
                        onSelectionChanged(listEntry) // Notify parent
                    },
                    text = {
                        Text(
                            text = listEntry.name,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                )
            }
        }
    }

    LaunchedEffect(expanded) {
        if (!expanded && selected == preselected) {
            onSelectionChanged(preselected)
        }
    }
}