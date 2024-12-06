package com.example.polarshuttle.ui.navigation

sealed class MainNavigation(val route: String) {
    object Login: MainNavigation("Login")

    object StudentHome: MainNavigation("StudentHome")
    object StudentProfile: MainNavigation("StudentProfile")
    object StudentDashboard: MainNavigation("StudentRequest")


    object DriverHome: MainNavigation("DriverHome")
    object DriverProfile: MainNavigation("DriverProfile")
    object DriverDashboard: MainNavigation("DriverDashboard")


}