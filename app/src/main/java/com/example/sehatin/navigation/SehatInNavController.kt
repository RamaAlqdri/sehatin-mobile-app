package com.example.sehatin.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

object MainDestinations {
    const val DASHBOARD_ROUTE = "dashboard"
    const val ONBOARDING_ROUTE = "onboarding"
    const val WELCOME_ROUTE = "welcome"
    const val CHANGE_PASSWORD_ROUTE = "changePassword"
    const val INPUT_AGE_ROUTE = "inputAge"
    const val INPUT_NAME_ROUTE = "inputName"
    const val INPUT_GENDER_ROUTE = "inputGender"
    const val INPUT_HEIGHT_ROUTE = "inputHeight"
    const val INPUT_WEIGHT_ROUTE = "inputWeight"
    const val INPUT_GOAL_ROUTE = "inputGoal"
    const val INPUT_ACTIVITY_ROUTE = "inputActivity"
    const val OTP_ROUTE = "otp"
    const val LOGIN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
    const val USERNAME_ROUTE = "username"
    const val SNACK_DETAIL_ROUTE = "snack"
    const val SNACK_ID_KEY = "snackId"
    const val ORIGIN = "origin"
    const val CHECK_ROUTE = "check"
}

object DetailDestinations {
    const val WATER_DETAIL_ROUTE = "water"
    const val CALORIES_DETAIL_ROUTE = "calories"
    const val DIET_SCHEDULE_DETAIL_ROUTE = "dietSchedule"
    const val FOOD_RECOMENDATION_DETAIL_ROUTE = "foodRecomendation"
    const val FOOD_LIST_DETAIL_ROUTE = "foodList"
    const val FOOD_DETAIL_ROUTE = "foodDetail"
}

object QueryKeys {
    const val EMAIL = "email"
}

@Composable
fun rememberSehatInNavController(
    navController: NavHostController = rememberNavController()
): SehatInNavController = remember(navController) {
    SehatInNavController(navController)
}

@Stable
class SehatInNavController(
    val navController: NavHostController,
) {

    fun upPress() {
        navController.navigateUp()
    }

    fun navigateToBottomBarRoute(route: String) {
        if (route != navController.currentDestination?.route) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(findStartDestination(navController.graph).id) {
                    saveState = true
                }
            }
        }
    }

    fun navigateToNonBottomBarRoute(route: String, isPopBackStack: Boolean = false) {
        if (route != navController.currentDestination?.route) {
            if (isPopBackStack) {
                navController.popBackStack()
            }
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
            }
        }
    }



    fun navigateToSnackDetail(snackId: Long, origin: String, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.SNACK_DETAIL_ROUTE}/$snackId?origin=$origin")
        }
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}