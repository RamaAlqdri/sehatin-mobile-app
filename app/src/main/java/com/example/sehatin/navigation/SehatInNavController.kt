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
    const val DIET_ROUTE = "diet"
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
    const val FORGOT_OTP_ROUTE = "forgotOtp"
    const val LOGIN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
    const val USERNAME_ROUTE = "username"
    const val SNACK_DETAIL_ROUTE = "snack"
    const val SNACK_ID_KEY = "snackId"
    const val ORIGIN = "origin"
    const val CHECK_ROUTE = "check"
    const val PROFILE_ROUTE = "profile"
    const val FORGOT_PASSWORD_ROUTE = "forgotPassword"
}

object DetailDestinations {
    const val WATER_DETAIL_ROUTE = "water"
    const val CALORIES_DETAIL_ROUTE = "calories"
    const val DIET_SCHEDULE_DETAIL_ROUTE = "dietSchedule"
    const val FOOD_RECOMENDATION_DETAIL_ROUTE_BASE = "foodRecomendation"
    const val FOOD_LIST_DETAIL_ROUTE = "foodList"
    const val FOOD_DETAIL_ROUTE_BASE = "foodDetail"
    const val FOOD_ID_ARG = "foodId"
    const val SCHEDULE_ID_ARG = "scheduleId"
    const val CHANGE_PASSWORD_AUTHED_ROUTE = "changePasswordAuthed"
    const val UPDATE_HEIGHT_ROUTE = "updateHeight"
    const val UPDATE_HEIGHT_ONLY_ROUTE = "updateHeightOnly"
    const val UPDATE_WEIGHT_ROUTE = "updateWeight"
    const val UPDATE_WEIGHT_ONLY_ROUTE = "updateWeightOnly"
    const val UPDATE_ACTIVITY_ROUTE = "updateActivity"
    const val UPDATE_GOAL_ROUTE = "updateGoal"
    const val STATISTIC_DETAIL_ROUTE = "statistic"



    fun foodRecomendationRouteWithId(scheduleId: String): String {
        return "$FOOD_RECOMENDATION_DETAIL_ROUTE_BASE/$scheduleId"
    }

    const val FOOD_RECOMENDATION_DETAIL_ROUTE =
        "$FOOD_RECOMENDATION_DETAIL_ROUTE_BASE/{$SCHEDULE_ID_ARG}"

    // Untuk navigasi
    fun foodDetailRouteWithId(foodId: String): String {
        return "$FOOD_DETAIL_ROUTE_BASE/$foodId"
    }

    // Untuk deklarasi route di NavHost
    const val FOOD_DETAIL_ROUTE = "$FOOD_DETAIL_ROUTE_BASE/{$FOOD_ID_ARG}"
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

    fun navigateWithClear(route: String, clearBackStack: Boolean = false) {
        if (route != navController.currentDestination?.route) {
            if (clearBackStack) {
                navController.navigate(route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            } else {
                navController.navigate(route) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }


    fun navigateToFoodDetail(foodId: String, from: NavBackStackEntry) {
        if (from.lifecycleIsResumed()) {
            navController.navigate("foodDetail/$foodId")
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