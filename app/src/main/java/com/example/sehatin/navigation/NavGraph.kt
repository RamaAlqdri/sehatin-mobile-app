package com.example.sehatin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sehatin.view.screen.onboarding.OnBoardingScreen

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
            OnBoardingScreen(navController = navController)
        }
        composable(route = Screen.Auth.route) {
//            AuthScreen()
        }
    }
}