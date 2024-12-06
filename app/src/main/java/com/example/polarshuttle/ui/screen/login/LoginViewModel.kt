package com.example.polarshuttle.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.polarshuttle.data.User
import com.example.polarshuttle.data.UserType
import com.example.polarshuttle.ui.navigation.MainNavigation
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import kotlin.math.log

class LoginViewModel : ViewModel(){

    private lateinit var auth: FirebaseAuth

    init {
        auth = Firebase.auth
    }

    var loginUiState: LoginUiState by mutableStateOf(LoginUiState.Init)

    //Student
    fun registerStudent(email: String, password: String){
        loginUiState = LoginUiState.Loading

        try{
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {

                    // Add student user to database
                    val newStudent = User(
                        id = it.user!!.uid,
                        email = email,
                        userType = UserType.STUDENT
                    )

                    val userCollection = FirebaseFirestore.getInstance().collection("users")
                    userCollection.add(newStudent)
                        .addOnSuccessListener {
                            loginUiState = LoginUiState.RegisterSuccess
                        }
                        .addOnFailureListener {
                            loginUiState = LoginUiState.Error(it.message)
                        }
                }
                .addOnFailureListener {
                    loginUiState = LoginUiState.Error(it.message)
                }
        } catch (e: Exception){
            loginUiState = LoginUiState.Error(e.message)
            e.printStackTrace()
        }
    }

    suspend fun loginStudent(email: String, password: String): AuthResult? {
        loginUiState = LoginUiState.Loading

        try{
            val result = auth.signInWithEmailAndPassword(email, password).await()
            if (result.user != null){
                loginUiState = LoginUiState.LoginSuccess
            } else {
                loginUiState = LoginUiState.Error("Login failed")
            }
            return result
        } catch (e: Exception){
            loginUiState = LoginUiState.Error(e.message)
            e.printStackTrace()
            return null
        }
    }

    // Driver
    fun registerDriver(email: String, password: String, confirmationCode: String){
        loginUiState = LoginUiState.Loading

        if (confirmationCode != "1234"){
            loginUiState = LoginUiState.Error("Invalid confirmation code")
            return
        }

        try{
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {

                    // Add student user to database
                    val newDriver = User(
                        id = it.user!!.uid,
                        email = email,
                        userType = UserType.DRIVER
                    )

                    val userCollection = FirebaseFirestore.getInstance().collection("users")
                    userCollection.add(newDriver)
                        .addOnSuccessListener {
                            loginUiState = LoginUiState.RegisterSuccess
                        }
                        .addOnFailureListener {
                            loginUiState = LoginUiState.Error(it.message)
                        }

                }
                .addOnFailureListener {
                    loginUiState = LoginUiState.Error(it.message)
                }
        } catch (e: Exception){
            loginUiState = LoginUiState.Error(e.message)
            e.printStackTrace()
        }
    }

    suspend fun loginDriver(email: String, password: String): AuthResult? {
        loginUiState = LoginUiState.Loading

        try{
            val result = auth.signInWithEmailAndPassword(email, password).await()
            if (result.user != null){
                loginUiState = LoginUiState.LoginSuccess
            } else {
                loginUiState = LoginUiState.Error("Login failed")
            }
            return result
        } catch (e: Exception){
            loginUiState = LoginUiState.Error(e.message)
            e.printStackTrace()
            return null
        }
    }

}

sealed interface LoginUiState{
    object Init: LoginUiState
    object Loading: LoginUiState
    object RegisterSuccess: LoginUiState
    object LoginSuccess: LoginUiState
    data class Error(val errorMessage: String?): LoginUiState
}