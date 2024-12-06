package com.example.polarshuttle.ui.screen.student

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.polarshuttle.data.Request
import com.example.polarshuttle.data.ShuttleLocation
import com.example.polarshuttle.data.Status
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import java.time.LocalDateTime
import java.time.ZoneId
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.internal.EMPTY_REQUEST

import java.time.format.DateTimeFormatter

@HiltViewModel
class StudentViewModel @Inject constructor() : ViewModel() {
    private val db = FirebaseFirestore.getInstance().collection("requests")

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    private val _requests = MutableStateFlow<List<Request>>(emptyList())
    val requests: StateFlow<List<Request>> get() = _requests

    init {
        viewModelScope.launch {
            getRequestsFlow().collect { requestList ->
                _requests.value = requestList
            }
        }
    }

    // My Requests Flow
    val myRequests: StateFlow<List<Request>> = _requests.map { requests ->
        userId?.let { id ->
            requests.filter { it.userId == id }
        } ?: emptyList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )


    @RequiresApi(Build.VERSION_CODES.O)
    fun addRequest(
        requester: String,
        numberOfRiders: Int,
        pickUpLocation: ShuttleLocation,
        dropOffLocation: ShuttleLocation
    ) {
        viewModelScope.launch {
            val (currDate, currTime) = getDateAndTime()

            val newRequest = Request(
                requester = requester,
                userId = userId ?: "",
                numberOfRiders = numberOfRiders,
                driver = "",
                driverId = "",
                date = currDate,
                time = currTime,
                pickUpLocation = pickUpLocation,
                dropOffLocation = dropOffLocation,
                status = Status.PENDING
            )

            try {
                // Add the request to Firestore and get the generated document ID
                val documentRef = db.add(newRequest).await()
                val generatedId = documentRef.id

                db.document(generatedId).update("id", generatedId).await()
                Log.d("StudentViewModel", "Request added successfully")
            } catch (e: Exception) {
                Log.e("StudentViewModel", "Error adding request: ${e.message}")
            }
        }
    }

    fun cancelRequest(request: Request) {
        viewModelScope.launch {
            request.status = Status.CANCELED
            try {
                db.document(request.id).set(request).await()
                Log.d("StudentViewModel", "Request updated successfully")
            } catch (e: Exception) {
                Log.e("StudentViewModel", "Error updating request: ${e.message}")
            }
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDateAndTime(): Pair<String, String> {
        val currentDateTime = LocalDateTime.now(ZoneId.systemDefault())

        // Format for date
        val dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy") // Customize the pattern as needed
        val date = currentDateTime.format(dateFormatter)

        // Format for time
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm") // Customize the pattern as needed
        val time = currentDateTime.format(timeFormatter)

        return Pair(date, time)
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


