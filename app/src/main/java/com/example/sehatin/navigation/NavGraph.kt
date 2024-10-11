package com.example.sehatin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun SetupNavGraph(
    startDestination: String
) {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(route = Screen.Welcome.route) {
//            OnBoardingScreen(navController = navController)
        }
        composable(route = Screen.Auth.route) {
//            AuthScreen()
        }
    }
}