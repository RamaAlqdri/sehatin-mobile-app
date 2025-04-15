package com.example.sehatin.navigation


import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.sehatin.LocalNavAnimatedVisibilityScope
import com.example.sehatin.view.components.HomeSections
import com.example.sehatin.view.screen.dashboard.consultation.ConsultationScreen
import com.example.sehatin.view.screen.dashboard.diet.DietScreen
import com.example.sehatin.view.screen.dashboard.home.DashboardScreen
import com.example.sehatin.viewmodel.DashboardViewModel
import com.example.sehatin.view.screen.dashboard.profile.ProfileScreen

fun <T> spatialExpressiveSpring() = spring<T>(
    dampingRatio = 0.8f,
    stiffness = 380f
)

fun <T> nonSpatialExpressiveSpring() = spring<T>(
    dampingRatio = 1f,
    stiffness = 1600f
)

fun NavGraphBuilder.composableWithCompositionLocal(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (
    @JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
    )? = {
        fadeIn(nonSpatialExpressiveSpring())
    },
    exitTransition: (
    @JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
    )? = {
        fadeOut(nonSpatialExpressiveSpring())
    },
    popEnterTransition: (
    @JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
    )? =
        enterTransition,
    popExitTransition: (
    @JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
    )? =
        exitTransition,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route,
        arguments,
        deepLinks,
        enterTransition,
        exitTransition,
        popEnterTransition,
        popExitTransition
    ) {
        CompositionLocalProvider(
            LocalNavAnimatedVisibilityScope provides this@composable
        ) {
            content(it)
        }
    }
}


fun NavGraphBuilder.addHomeGraph(
    dashboardViewModel: DashboardViewModel,
    onSnackSelected: (Long, String, NavBackStackEntry) -> Unit,
    modifier: Modifier = Modifier,
    navigateToRoute: (String) -> Unit,
    navigateToRootRoute: (String) -> Unit // <--- Tambahkan ini
) {
    composable(HomeSections.Dashboard.route) { from ->
//        HomeScreen(
//            onSnackClick = { id, origin ->
//                onSnackSelected(id, origin, from)
//            },
//            modifier = modifier
//        )
        DashboardScreen(
            onSnackClick = { id, origin ->
                onSnackSelected(id, origin, from)
            },
            dashboardViewModel = dashboardViewModel,
            modifier = modifier,
            navigateToDetail = { route -> // 👈 Tambahkan ini
                navigateToRootRoute(route) // 👈 Ini akan mengarah ke root NavController
            }
        )
    }
    composable(HomeSections.Consultation.route) { from ->
        ConsultationScreen(
            modifier = modifier
        )
    }
    composable(HomeSections.Diet.route) { from ->
        DietScreen(

        )
    }
    composable(HomeSections.Profile.route) {
        ProfileScreen(

        )
    }
}