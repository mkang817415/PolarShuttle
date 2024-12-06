package com.example.polarshuttle.ui.screen.driver

import android.location.Location
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.polarshuttle.data.Request
import com.example.polarshuttle.data.ShuttleLocation
import com.example.polarshuttle.data.Status
import com.example.polarshuttle.location.LocationManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class DriverViewModel @Inject constructor(
    private val locationManager: LocationManager
) : ViewModel() {
    private val db = FirebaseFirestore.getInstance().collection("requests")
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    // Location monitoring
    var locationState = mutableStateOf<Location?>(null)

    fun startLocationMonitoring() {
        viewModelScope.launch {
            locationManager.fetchUpdates().collect { location ->
                locationState.value = location
            }
        }
    }

    // All Requests Flow
    private val _requests = MutableStateFlow<List<Request>>(emptyList())
    val requests: StateFlow<List<Request>> get() = _requests

    init {
        viewModelScope.launch {
            getRequestsFlow().collect { requestList ->
                _requests.value = requestList
            }
        }
    }

    // Accepted Requests Flow
    val acceptedRequests: StateFlow<List<Request>> = _requests.map { requests ->
        userId?.let { id ->
            requests.filter { it.driverId == id && it.status == Status.ACCEPTED }
        } ?: emptyList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Current Request Flow
    val currentRequest: StateFlow<Request?> = acceptedRequests.map { requests ->
        requests.firstOrNull()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    // Accept a request
    fun acceptRequest(request: Request) {
        if (request.id.isEmpty()) {
            Log.e("DriverViewModel", "Request ID is empty. Cannot accept the request.")
            return
        }
        updateRequestStatus(request, Status.ACCEPTED)
    }

    // Complete a request
    fun completeRequest(request: Request) {
        if (request.id.isEmpty()) {
            Log.e("DriverViewModel", "Request ID is empty. Cannot complete the request.")
            return
        }
        updateRequestStatus(request, Status.COMPLETED)
    }

    // Pend a request
    fun pendRequest(request: Request) {
        if (request.id.isEmpty()) {
            Log.e("DriverViewModel", "Request ID is empty. Cannot pend the request.")
            return
        }
        updateRequestStatus(request, Status.PENDING)
    }

    // Update the status of a request in Firestore
    private fun updateRequestStatus(request: Request, status: Status) {
        viewModelScope.launch {
            try {
                val updatedRequest = request.copy(status = status, driverId = userId ?: "")
                db.document(updatedRequest.id).set(updatedRequest).await()
                Log.d("DriverViewModel", "Request updated to status: $status")

                // Update the state flow
                _requests.value = _requests.value.map {
                    if (it.id == updatedRequest.id) updatedRequest else it
                }
            } catch (e: Exception) {
                Log.e("DriverViewModel", "Error updating request: ${e.message}")
            }
        }
    }

    // Flow to fetch requests from Firestore
    private fun getRequestsFlow(): Flow<List<Request>> = callbackFlow {
        val query = db.orderBy("time")
        val listener = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val requests = snapshot?.documents?.mapNotNull { document ->
                document.toObject(Request::class.java)?.apply {
                    id = document.id // Set Firestore document ID
                }
            } ?.filter{ request ->
                request.status == Status.PENDING || request.status == Status.ACCEPTED
            } ?.sortedBy { request ->
                request.time
            } ?: emptyList()

            trySend(requests).isSuccess
        }

        awaitClose { listener.remove() }
    }
}