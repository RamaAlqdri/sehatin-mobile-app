package com.example.sehatin.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen(route = "welcome_screen")
    object Auth : Screen(route = "auth_screen")
    object Dashboard : Screen(route = "dashboard_screen")
    object Diet : Screen(route = "diet_screen")
    object Workout : Screen(route = "workout_screen")
    object Consultation : Screen(route = "consultation_screen")
    object Profile : Screen(route = "profile_screen")
}