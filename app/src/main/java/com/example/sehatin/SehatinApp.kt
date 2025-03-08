@file:OptIn(
    ExperimentalSharedTransitionApi::class
)

package com.example.sehatin

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.compose.SehatInTheme
import com.example.sehatin.common.DetailTest
import com.example.sehatin.navigation.DetailDestinations
import com.example.sehatin.navigation.MainDestinations
import com.example.sehatin.navigation.SehatiInScaffold
import com.example.sehatin.navigation.addHomeGraph
import com.example.sehatin.navigation.composableWithCompositionLocal
import com.example.sehatin.navigation.nonSpatialExpressiveSpring
import com.example.sehatin.navigation.rememberSehatInNavController
import com.example.sehatin.navigation.rememberSehatiInScaffoldState
import com.example.sehatin.navigation.spatialExpressiveSpring
import com.example.sehatin.view.components.HomeSections
import com.example.sehatin.view.components.SehatInBottomBar
import com.example.sehatin.view.screen.authentication.forgot.ChangePassword
import com.example.sehatin.view.screen.authentication.login.LoginScreen
import com.example.sehatin.view.screen.authentication.register.OtpScreen
import com.example.sehatin.view.screen.authentication.register.RegisterScreen
import com.example.sehatin.view.screen.authentication.register.personalize.InputActivity
import com.example.sehatin.view.screen.authentication.register.personalize.InputAge
import com.example.sehatin.view.screen.authentication.register.personalize.InputGender
import com.example.sehatin.view.screen.authentication.register.personalize.InputGoal
import com.example.sehatin.view.screen.authentication.register.personalize.InputHeight
import com.example.sehatin.view.screen.authentication.register.personalize.InputName
import com.example.sehatin.view.screen.authentication.register.personalize.InputWeight
import com.example.sehatin.view.screen.dashboard.detail.diet.DietScheduleDetail
import com.example.sehatin.view.screen.dashboard.detail.diet.FoodDetail
import com.example.sehatin.view.screen.dashboard.detail.diet.FoodListDetail
import com.example.sehatin.view.screen.dashboard.detail.diet.FoodRecomendationDetail
import com.example.sehatin.view.screen.dashboard.detail.home.CaloriesDetail
import com.example.sehatin.view.screen.dashboard.detail.home.WaterDetail
import com.example.sehatin.view.screen.onboarding.OnBoardingScreen

@Composable
fun SehatInApp() {
    SehatInTheme {
        val fleupartNavController = rememberSehatInNavController()
        SharedTransitionLayout {
            CompositionLocalProvider(
                LocalSharedTransitionScope provides this
            ) {
                NavHost(
                    navController = fleupartNavController.navController,
                    startDestination = MainDestinations.DASHBOARD_ROUTE,
                    contentAlignment = Alignment.Center
                ) {

//                    composableWithCompositionLocal(
//                        route = MainDestinations.????
//                    ) { backStackEntry ->
//                        Masukkan Screen yang bukan merupakan bagian dari bottom navbar
//                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.LOGIN_ROUTE
                    ) { backStackEntry ->
                        LoginScreen()
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.ONBOARDING_ROUTE
                    ) { backStackEntry ->
                        OnBoardingScreen(
                        )
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.REGISTER_ROUTE
                    ) { backStackEntry ->
                        RegisterScreen()
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.OTP_ROUTE
                    ) { backStackEntry ->
                        OtpScreen()
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.CHANGE_PASSWORD_ROUTE
                    ) { backStackEntry ->
                        ChangePassword()
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.INPUT_AGE_ROUTE
                    ) { backStackEntry ->
                        InputAge()
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.INPUT_NAME_ROUTE
                    ) { backStackEntry ->
                        InputName()
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.INPUT_GENDER_ROUTE
                    ) { backStackEntry ->
                        InputGender()
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.INPUT_HEIGHT_ROUTE
                    ) { backStackEntry ->
                        InputHeight()
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.INPUT_HEIGHT_ROUTE
                    ) { backStackEntry ->
                        InputHeight()
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.INPUT_WEIGHT_ROUTE
                    ) { backStackEntry ->
                        InputWeight()
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.INPUT_ACTIVITY_ROUTE
                    ) { backStackEntry ->
                        InputActivity()
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.INPUT_GOAL_ROUTE
                    ) { backStackEntry ->
                        InputGoal()
                    }

                    composableWithCompositionLocal(
                        route = DetailDestinations.WATER_DETAIL_ROUTE
                    ) { backStackEntry ->
                        WaterDetail(

                        )
                    }

                    composableWithCompositionLocal(
                        route = DetailDestinations.WATER_DETAIL_ROUTE
                    ) { backStackEntry ->
                        WaterDetail(

                        )
                    }

                    composableWithCompositionLocal(
                        route = DetailDestinations.FOOD_RECOMENDATION_DETAIL_ROUTE
                    ) { backStackEntry ->
                        FoodRecomendationDetail(

                        )
                    }

                    composableWithCompositionLocal(
                        route = DetailDestinations.FOOD_LIST_DETAIL_ROUTE
                    ) { backStackEntry ->
                        FoodListDetail(

                        )
                    }

                    composableWithCompositionLocal(
                        route = DetailDestinations.CALORIES_DETAIL_ROUTE
                    ) { backStackEntry ->
                        CaloriesDetail()
                    }

                    composableWithCompositionLocal(
                        route = DetailDestinations.FOOD_DETAIL_ROUTE
                    ) { backStackEntry ->
                        FoodDetail(
                            onBackClick = fleupartNavController::upPress
                        )
                    }


                    // TIDAK MENYARANKAN PASSING OBJEK, Melainkan menggunakan callbacks
                    composableWithCompositionLocal(
                        route = MainDestinations.DASHBOARD_ROUTE
                    ) { backStackEntry ->
                        MainContainer(
                            onSnackSelected = fleupartNavController::navigateToSnackDetail
                        )
                    }

                    //BUAT NGECEK BISA ATAU TIDAK NAVIGASI KE SCREEN NON-BOTTOM NAVBAR
                    composableWithCompositionLocal(
                        "${MainDestinations.SNACK_DETAIL_ROUTE}/" +
                                "{${MainDestinations.SNACK_ID_KEY}}" +
                                "?origin={${MainDestinations.ORIGIN}}",
                        arguments = listOf(
                            navArgument(MainDestinations.SNACK_ID_KEY) {
                                type = NavType.LongType
                            }
                        ),

                        ) { backStackEntry ->
                        val arguments = requireNotNull(backStackEntry.arguments)
                        val id = arguments.getLong(MainDestinations.SNACK_ID_KEY)
                        val origin = arguments.getString(MainDestinations.ORIGIN)
                        DetailTest(
                            id,
                            origin = origin ?: "",
                            upPress = fleupartNavController::upPress
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainContainer(
    modifier: Modifier = Modifier,
    onSnackSelected: (Long, String, NavBackStackEntry) -> Unit
) {
    val fleuraScaffoldState = rememberSehatiInScaffoldState()
    val nestedNavController = rememberSehatInNavController()
    val navBackStackEntry by nestedNavController.navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No SharedElementScope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No SharedElementScope found")
    SehatiInScaffold(
        bottomBar = {
            with(animatedVisibilityScope) {
                with(sharedTransitionScope) {
                    SehatInBottomBar(
                        tabs = HomeSections.entries.toTypedArray(),
                        currentRoute = currentRoute ?: HomeSections.Dashboard.route,
                        navigateToRoute = nestedNavController::navigateToBottomBarRoute,
                        modifier = Modifier
                            .renderInSharedTransitionScopeOverlay(
                                zIndexInOverlay = 1f,
                            )
                            .animateEnterExit(
                                enter = fadeIn(nonSpatialExpressiveSpring()) + slideInVertically(
                                    spatialExpressiveSpring()
                                ) {
                                    it
                                },
                                exit = fadeOut(nonSpatialExpressiveSpring()) + slideOutVertically(
                                    spatialExpressiveSpring()
                                ) {
                                    it
                                }
                            )
                    )
                }
            }
        },
        modifier = modifier

    ) { padding ->
        NavHost(
            navController = nestedNavController.navController,
            startDestination = HomeSections.Dashboard.route,
            contentAlignment = Alignment.Center
        ) {
            addHomeGraph(
                onSnackSelected = onSnackSelected,
                modifier = Modifier
                    .padding(padding)
                    .consumeWindowInsets(padding)
            )
        }
    }
}

val LocalNavAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }

