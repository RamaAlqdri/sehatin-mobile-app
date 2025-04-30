@file:OptIn(
    ExperimentalSharedTransitionApi::class
)

package com.example.sehatin

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.compose.SehatInTheme
import com.example.sehatin.common.DetailTest
import com.example.sehatin.data.resource.Resource
import com.example.sehatin.di.factory.ConsultationViewModelFactory
import com.example.sehatin.di.factory.DashboardViewModelFactory
import com.example.sehatin.di.factory.DietViewModelFactory
import com.example.sehatin.di.factory.LoginViewModelFactory
import com.example.sehatin.di.factory.OnBoardingViewModelFactory
import com.example.sehatin.di.factory.OtpViewModelFactory
import com.example.sehatin.di.factory.PersonalizeViewModelFactory
import com.example.sehatin.navigation.DetailDestinations
import com.example.sehatin.navigation.MainDestinations
import com.example.sehatin.navigation.QueryKeys
import com.example.sehatin.navigation.SehatInNavController
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
import com.example.sehatin.view.screen.authentication.forgot.ChangePasswordAuth
import com.example.sehatin.view.screen.authentication.forgot.ForgotOtpScreen
import com.example.sehatin.view.screen.authentication.forgot.ForgotPassword
import com.example.sehatin.view.screen.authentication.login.LoginScreen
import com.example.sehatin.viewmodel.LoginScreenViewModel
import com.example.sehatin.view.screen.authentication.register.OtpScreen
import com.example.sehatin.view.screen.authentication.register.RegisterScreen
import com.example.sehatin.view.screen.authentication.register.personalize.InputActivity
import com.example.sehatin.view.screen.authentication.register.personalize.InputAge
import com.example.sehatin.view.screen.authentication.register.personalize.InputGender
import com.example.sehatin.view.screen.authentication.register.personalize.InputGoal
import com.example.sehatin.view.screen.authentication.register.personalize.InputHeight
import com.example.sehatin.view.screen.authentication.register.personalize.InputName
import com.example.sehatin.view.screen.authentication.register.personalize.InputWeight
import com.example.sehatin.view.screen.authentication.register.update.UpdateActivity
import com.example.sehatin.view.screen.authentication.register.update.UpdateGoal
import com.example.sehatin.view.screen.authentication.register.update.UpdateHeight
import com.example.sehatin.view.screen.authentication.register.update.UpdateWeight
import com.example.sehatin.viewmodel.PersonalizeViewModel
import com.example.sehatin.view.screen.dashboard.detail.diet.DietScheduleDetail
import com.example.sehatin.view.screen.dashboard.detail.diet.FoodDetail
import com.example.sehatin.view.screen.dashboard.detail.diet.FoodListDetail
import com.example.sehatin.view.screen.dashboard.detail.diet.FoodRecomendationDetail
import com.example.sehatin.view.screen.dashboard.detail.home.CaloriesDetail
import com.example.sehatin.view.screen.dashboard.detail.home.WaterDetail
import com.example.sehatin.view.screen.dashboard.home.CheckScreen
import com.example.sehatin.viewmodel.DashboardViewModel
import com.example.sehatin.view.screen.onboarding.OnBoardingScreen
import com.example.sehatin.viewmodel.ConsultationViewModel
import com.example.sehatin.viewmodel.DietViewModel
import com.example.sehatin.viewmodel.OnBoardingViewModel
import com.example.sehatin.viewmodel.OtpScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun SehatInApp() {

    val onBoardingViewModel: OnBoardingViewModel = viewModel(
        factory = OnBoardingViewModelFactory.getInstance(
            Resource.appContext
        )
    )

    val otpViewModel: OtpScreenViewModel = viewModel(
        factory = OtpViewModelFactory.getInstance(
            Resource.appContext
        )
    )



    val personalizeViewModel: PersonalizeViewModel = viewModel(
        factory = PersonalizeViewModelFactory.getInstance(
            Resource.appContext
        )
    )

    val dashboardViewModel: DashboardViewModel = viewModel(
        factory = DashboardViewModelFactory.getInstance(
            Resource.appContext
        )
    )
    val consultationViewModel: ConsultationViewModel = viewModel(
        factory = ConsultationViewModelFactory.getInstance(
            Resource.appContext
        )
    )

    val dietViewModel: DietViewModel = viewModel(
        factory = DietViewModelFactory.getInstance(
            Resource.appContext
        )
    )
    val loginViewModel: LoginScreenViewModel = viewModel(
        factory = LoginViewModelFactory.getInstance(
            Resource.appContext
        )
    )

    val onBoardingStatus by
    onBoardingViewModel.onBoardingStatus.collectAsStateWithLifecycle(initialValue = false)

    val isUserLoggedIn by loginViewModel.isUserLoggedIn()
        .collectAsStateWithLifecycle(initialValue = false)

//    val isPersonalizeCompleted = loginViewModel.checkFilled()
    val isPersonalizeCompleted by loginViewModel.isPersonalizeFilled()
        .collectAsStateWithLifecycle(initialValue = false)

    LaunchedEffect(isPersonalizeCompleted) {
        Log.e("isPersonalizeCompleted", isPersonalizeCompleted.toString())
    }

    LaunchedEffect(onBoardingStatus) {
        Log.e("onBoarding", onBoardingStatus.toString())
    }

    val startDestination = remember {
        when {
            !onBoardingStatus -> MainDestinations.ONBOARDING_ROUTE
            !isUserLoggedIn -> MainDestinations.LOGIN_ROUTE
            !isPersonalizeCompleted -> MainDestinations.INPUT_NAME_ROUTE
            else -> MainDestinations.DASHBOARD_ROUTE
        }
    }

    val startupNavViewModel = viewModel<StartupNavigationViewModel>(
        factory = StartupNavigationViewModelFactory(onBoardingViewModel, loginViewModel)
    )

    val destination by startupNavViewModel.startDestination.collectAsStateWithLifecycle(
        initialValue = MainDestinations.ONBOARDING_ROUTE // Safe fallback
    )

    SehatInTheme {
        val sehatInNavController = rememberSehatInNavController()
        SharedTransitionLayout {
            CompositionLocalProvider(
                LocalSharedTransitionScope provides this
            ) {
//                destination?.let {
                NavHost(
                    navController = sehatInNavController.navController,
                    startDestination = destination
                    //                        when {
                    //                        !onBoardingStatus -> MainDestinations.ONBOARDING_ROUTE
                    //                         !isUserLoggedIn -> MainDestinations.LOGIN_ROUTE
                    ////                        !isPersonalizeCompleted -> MainDestinations.INPUT_NAME_ROUTE
                    //                        else -> MainDestinations.DASHBOARD_ROUTE
                    //                    }
                    ,
                    contentAlignment = Alignment.Center
                ) {

                    //                    composableWithCompositionLocal(
                    //                        route = MainDestinations.????
                    //                    ) { backStackEntry ->
                    //                        Masukkan Screen yang bukan merupakan bagian dari bottom navbar
                    //                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.CHECK_ROUTE
                    ) { backStackEntry ->
                        //                        CheckScreen(
                        //                            navigateToRoute = sehatInNavController::navigateToNonBottomBarRoute,
                        //                            loginScreenViewModel = loginViewModel
                        //                        )
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.LOGIN_ROUTE
                    ) { backStackEntry ->
                        LoginScreen(
                            navigateToRoute = sehatInNavController::navigateToNonBottomBarRoute,
                            loginViewModel = loginViewModel
                        )
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.ONBOARDING_ROUTE
                    ) { backStackEntry ->
                        OnBoardingScreen(
                            navigateToRoute = sehatInNavController::navigateToNonBottomBarRoute,
                            setOnBoardingCompleted = onBoardingViewModel::setOnBoardingCompleted
                        )
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.REGISTER_ROUTE
                    ) { backStackEntry ->
                        RegisterScreen(
                            navigateToRoute = sehatInNavController::navigateToNonBottomBarRoute
                        )
                    }

                    composableWithCompositionLocal(
                        route = "${MainDestinations.OTP_ROUTE}?" + "email={${QueryKeys.EMAIL}}"
                    ) { backStackEntry ->
                        val arguments = requireNotNull(backStackEntry.arguments)
                        val email = arguments.getString(QueryKeys.EMAIL) ?: "Email tidak ditemukan"
                        OtpScreen(
                            email = email,
                            navigateToRoute = sehatInNavController::navigateToNonBottomBarRoute,
                            otpViewModel = otpViewModel,
                        )
                    }
                    composableWithCompositionLocal(
                        route = "${MainDestinations.FORGOT_OTP_ROUTE}?" + "email={${QueryKeys.EMAIL}}"
                    ) { backStackEntry ->
                        val arguments = requireNotNull(backStackEntry.arguments)
                        val email = arguments.getString(QueryKeys.EMAIL) ?: "Email tidak ditemukan"
                        ForgotOtpScreen(
                            email = email,
                            navigateToRoute = sehatInNavController::navigateToNonBottomBarRoute,
                            loginViewModel = loginViewModel,
                        )
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.CHANGE_PASSWORD_ROUTE
                    ) { backStackEntry ->
                        ChangePassword(
                            loginScreenViewModel = loginViewModel,
                            navigateToRoute = sehatInNavController::navigateToNonBottomBarRoute
                        )
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.FORGOT_PASSWORD_ROUTE
                    ) { backStackEntry ->
                        ForgotPassword(
                            onBackClick = sehatInNavController::upPress,
                            loginScreenViewModel = loginViewModel,
                            navigateToRoute = sehatInNavController::navigateToNonBottomBarRoute
                        )
                    }

                    composableWithCompositionLocal(
                        route = DetailDestinations.CHANGE_PASSWORD_AUTHED_ROUTE
                    ) { backStackEntry ->
                        ChangePasswordAuth(onBackClick = sehatInNavController::upPress,
                            loginViewModel = loginViewModel,
                            navigateToRoute = sehatInNavController::navigateToNonBottomBarRoute,
                        )
                    }

                    composableWithCompositionLocal(
                        route = DetailDestinations.UPDATE_HEIGHT_ROUTE
                    ) { backStackEntry ->
                        UpdateHeight(
                            personalizeViewModel = personalizeViewModel,
                            navigateToRoute = { route ->
                                sehatInNavController.navigateToNonBottomBarRoute(route)
                            }
                        )
                    }
                    composableWithCompositionLocal(
                        route = DetailDestinations.UPDATE_WEIGHT_ROUTE
                    ) { backStackEntry ->
                        UpdateWeight(
                            personalizeViewModel = personalizeViewModel,
                            navigateToRoute = { route ->
                                sehatInNavController.navigateToNonBottomBarRoute(route)
                            }
                        )
                    }

                    composableWithCompositionLocal(
                        route = DetailDestinations.UPDATE_ACTIVITY_ROUTE
                    ) { backStackEntry ->
                        UpdateActivity(
                            personalizeViewModel = personalizeViewModel,
                            navigateToRoute = { route ->
                                sehatInNavController.navigateToNonBottomBarRoute(route)
                            }
                        )
                    }

                    composableWithCompositionLocal(
                        route = DetailDestinations.UPDATE_GOAL_ROUTE
                    ) { backStackEntry ->
                        UpdateGoal(
                            personalizeViewModel = personalizeViewModel,
                            navigateToRoute = sehatInNavController::navigateToNonBottomBarRoute,
                            loginScreenViewModel = loginViewModel
                        )
                    }



                    composableWithCompositionLocal(
                        route = DetailDestinations.WATER_DETAIL_ROUTE
                    ) { backStackEntry ->
                        WaterDetail(
                            onBackClick = sehatInNavController::upPress,
                            dashboardViewModel = dashboardViewModel
                        )
                    }

                    composableWithCompositionLocal(
                        route = DetailDestinations.FOOD_RECOMENDATION_DETAIL_ROUTE
                    ) { backStackEntry ->
                        val scheduleId =
                            backStackEntry.arguments?.getString(DetailDestinations.SCHEDULE_ID_ARG)
                                ?: ""
                        FoodRecomendationDetail(
                            scheduleId = scheduleId,
                            onBackClick = sehatInNavController::upPress,
                            dietViewModel = dietViewModel,
                            navigateToDetail = { route ->
                                sehatInNavController.navigateToNonBottomBarRoute(route)
                            }

                        )
                    }

                    composableWithCompositionLocal(
                        route = DetailDestinations.FOOD_LIST_DETAIL_ROUTE
                    ) { backStackEntry ->
                        FoodListDetail(
                            onBackClick = sehatInNavController::upPress,
                            dietViewModel = dietViewModel,
                            navigateToDetail = { route ->
                                sehatInNavController.navigateToNonBottomBarRoute(route)
                            }

                        )
                    }

                    composableWithCompositionLocal(
                        route = DetailDestinations.DIET_SCHEDULE_DETAIL_ROUTE
                    ) { backStackEntry ->
                        DietScheduleDetail(
                            onBackClick = sehatInNavController::upPress,
                            navigateToDetail = { route ->
                                sehatInNavController.navigateToNonBottomBarRoute(route)
                            },
                            dietViewModel = dietViewModel
                        )


                    }

                    composableWithCompositionLocal(
                        route = DetailDestinations.CALORIES_DETAIL_ROUTE
                    ) { backStackEntry ->
                        CaloriesDetail(
                            onBackClick = sehatInNavController::upPress, // ✅ Navigasi kembali
                            dashboardViewModel = dashboardViewModel
                        )
                    }

//                    composableWithCompositionLocal(
//                        route = DetailDestinations.FOOD_DETAIL_ROUTE
//                    ) { backStackEntry ->
//                        FoodDetail(
//                            onBackClick = sehatInNavController::upPress
//                        )
//                    }

                    composableWithCompositionLocal(
                        route = DetailDestinations.FOOD_DETAIL_ROUTE
                    ) { backStackEntry ->
                        val foodId =
                            backStackEntry.arguments?.getString(DetailDestinations.FOOD_ID_ARG)
                                ?: ""
                        FoodDetail(
                            foodId = foodId,
                            onBackClick = sehatInNavController::upPress,
                            dashboardViewModel = dashboardViewModel
                        )
                    }

                    // PERSONALIZE SCREEN

                    composableWithCompositionLocal(
                        route = MainDestinations.INPUT_NAME_ROUTE
                    ) { backStackEntry ->
                        InputName(
                            navigateToRoute = sehatInNavController::navigateToNonBottomBarRoute,
                            personalizeViewModel = personalizeViewModel
                        )
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.INPUT_AGE_ROUTE
                    ) { backStackEntry ->
                        InputAge(
                            navigateToRoute = sehatInNavController::navigateToNonBottomBarRoute,
                            personalizeViewModel = personalizeViewModel
                        )
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.INPUT_GENDER_ROUTE
                    ) { backStackEntry ->
                        InputGender(
                            navigateToRoute = sehatInNavController::navigateToNonBottomBarRoute,
                            personalizeViewModel = personalizeViewModel
                        )
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.INPUT_HEIGHT_ROUTE
                    ) { backStackEntry ->
                        InputHeight(
                            navigateToRoute = sehatInNavController::navigateToNonBottomBarRoute,
                            personalizeViewModel = personalizeViewModel
                        )
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.INPUT_WEIGHT_ROUTE
                    ) { backStackEntry ->
                        InputWeight(
                            navigateToRoute = sehatInNavController::navigateToNonBottomBarRoute,
                            personalizeViewModel = personalizeViewModel
                        )
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.INPUT_ACTIVITY_ROUTE
                    ) { backStackEntry ->
                        InputActivity(
                            navigateToRoute = sehatInNavController::navigateToNonBottomBarRoute,
                            personalizeViewModel = personalizeViewModel
                        )
                    }

                    composableWithCompositionLocal(
                        route = MainDestinations.INPUT_GOAL_ROUTE
                    ) { backStackEntry ->
                        InputGoal(
                            navigateToRoute = sehatInNavController::navigateToNonBottomBarRoute,
                            personalizeViewModel = personalizeViewModel,
                            loginScreenViewModel = loginViewModel
                        )
                    }

                    // TIDAK MENYARANKAN PASSING OBJEK, Melainkan menggunakan callbacks
                    composableWithCompositionLocal(
                        route = MainDestinations.DASHBOARD_ROUTE
                    ) { backStackEntry ->
                        MainContainer(
                            dashboardViewModel = dashboardViewModel,
                            dietViewModel = dietViewModel,
                            onSnackSelected = sehatInNavController::navigateToSnackDetail,
                            sehatInNavController = sehatInNavController,
                            consultationViewModel = consultationViewModel,
                            loginViewModel = loginViewModel,
                            personalizeViewModel = personalizeViewModel,
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
                            upPress = sehatInNavController::upPress
                        )
                    }
                }
//                }
            }
        }
    }
}

@Composable
fun MainContainer(
    dashboardViewModel: DashboardViewModel,
    personalizeViewModel: PersonalizeViewModel,
    consultationViewModel: ConsultationViewModel,
    dietViewModel: DietViewModel,
    modifier: Modifier = Modifier,
    loginViewModel: LoginScreenViewModel,
    onSnackSelected: (Long, String, NavBackStackEntry) -> Unit,
    sehatInNavController: SehatInNavController // ✅ TAMBAHKAN INI

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
                dashboardViewModel = dashboardViewModel,
                personalizeViewModel = personalizeViewModel,
                consultationViewModel = consultationViewModel,
                dietViewModel = dietViewModel,
                loginViewModel = loginViewModel,
                onSnackSelected = onSnackSelected,
                modifier = Modifier
                    .padding(padding)
                    .consumeWindowInsets(padding),
//                navigateToRoute = nestedNavController::navigateToNonBottomBarRoute,
                navigateToRootRoute = sehatInNavController::navigateToNonBottomBarRoute, // 👈 ini penting!
                navigateToMain = sehatInNavController::navigateWithClear
            )
        }
    }
}


val LocalNavAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }


// 1. Change your ViewModel to start with a non-null default value
class StartupNavigationViewModel(
    private val onBoardingViewModel: OnBoardingViewModel,
    private val loginViewModel: LoginScreenViewModel
) : ViewModel() {
    // Use a safe default route
    private val _startDestination = MutableStateFlow(MainDestinations.ONBOARDING_ROUTE)
    val startDestination: StateFlow<String> = _startDestination

    init {
        determineStartDestination()
    }

    private fun determineStartDestination() {
        viewModelScope.launch {
            combine(
                onBoardingViewModel.onBoardingStatus,
                loginViewModel.isUserLoggedIn(),
                loginViewModel.isPersonalizeFilled()
            ) { onBoardingComplete, isLoggedIn, isPersonalizeComplete ->
                when {
                    !onBoardingComplete -> MainDestinations.ONBOARDING_ROUTE
                    !isLoggedIn -> MainDestinations.LOGIN_ROUTE
                    !isPersonalizeComplete -> MainDestinations.INPUT_NAME_ROUTE
                    else -> MainDestinations.DASHBOARD_ROUTE
                }
            }.first().let {
                _startDestination.value = it
            }
        }
    }
}

class StartupNavigationViewModelFactory(
    private val onBoardingViewModel: OnBoardingViewModel,
    private val loginViewModel: LoginScreenViewModel
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StartupNavigationViewModel::class.java)) {
            return StartupNavigationViewModel(
                onBoardingViewModel,
                loginViewModel
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

