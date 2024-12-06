package com.example.polarshuttle.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.polarshuttle.ui.screen.driver.DriverDashboardScreen
import com.example.polarshuttle.ui.screen.driver.DriverHomeScreen
import com.example.polarshuttle.ui.screen.driver.DriverProfileScreen
import com.example.polarshuttle.ui.screen.login.LoginScreen
import com.example.polarshuttle.ui.screen.student.StudentDashboardScreen
import com.example.polarshuttle.ui.screen.student.StudentHomeScreen
import com.example.polarshuttle.ui.screen.student.StudentProfileScreen


@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainNavigation.Login.route
    ) {

        composable(route = MainNavigation.Login.route) {
            LoginScreen(
                onDriverLoginSuccess = {
                    navController.navigate(MainNavigation.DriverHome.route)
                },
                onStudentLoginSuccess = {
                    navController.navigate(MainNavigation.StudentHome.route)
                }
            )
        }

        // Student Screens
        composable(route = MainNavigation.StudentHome.route) {
            StudentHomeScreen(
                navHome = {
                    navController.navigate(MainNavigation.StudentHome.route)
                },
                navProfile = {
                    navController.navigate(MainNavigation.StudentProfile.route)
                },
                navDashboard = {
                    navController.navigate(MainNavigation.StudentDashboard.route)
                }
            )
        }
        composable(route = MainNavigation.StudentDashboard.route) {
            StudentDashboardScreen(
                navHome = {
                    navController.navigate(MainNavigation.StudentHome.route)
                },
                navProfile = {
                    navController.navigate(MainNavigation.StudentProfile.route)
                },
                navDashboard = {
                    navController.navigate(MainNavigation.StudentDashboard.route)
                }
            )
        }
        composable(route = MainNavigation.StudentProfile.route) {
            StudentProfileScreen(
                navHome = {
                    navController.navigate(MainNavigation.StudentHome.route)
                },
                navProfile = {
                    navController.navigate(MainNavigation.StudentProfile.route)
                },
                navDashboard = {
                    navController.navigate(MainNavigation.StudentDashboard.route)
                }
            )
        }



        // Driver Screens
        composable(route = MainNavigation.DriverHome.route) {
            DriverHomeScreen(
                navHome = {
                    navController.navigate(MainNavigation.DriverHome.route)
                },
                navProfile = {
                    navController.navigate(MainNavigation.DriverProfile.route)
                },
                navDashboard = {
                    navController.navigate(MainNavigation.DriverDashboard.route)
                }
            )
        }
        composable(route = MainNavigation.DriverProfile.route) {
            DriverProfileScreen(
                navHome = {
                    navController.navigate(MainNavigation.DriverHome.route)
                },
                navProfile = {
                    navController.navigate(MainNavigation.DriverProfile.route)
                },
                navDashboard = {
                    navController.navigate(MainNavigation.DriverDashboard.route)
                }
            )
        }
        composable(route = MainNavigation.DriverDashboard.route) {
            DriverDashboardScreen(
                navHome = {
                    navController.navigate(MainNavigation.DriverHome.route)
                },
                navProfile = {
                    navController.navigate(MainNavigation.DriverProfile.route)
                },
                navDashboard = {
                    navController.navigate(MainNavigation.DriverDashboard.route)
                }
            )
        }


    }
}
