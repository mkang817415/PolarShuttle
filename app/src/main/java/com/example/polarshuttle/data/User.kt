package com.example.polarshuttle.data

data class User(
    var id: String = "",
    var email: String = "",
    var userType: UserType
)

enum class UserType{
    STUDENT, DRIVER
}