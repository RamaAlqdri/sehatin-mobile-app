package com.example.sehatin.view.screen.authentication.login

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sehatin.R
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.resource.Resource
import com.example.sehatin.di.factory.LoginViewModelFactory
import com.example.sehatin.di.factory.OnBoardingViewModelFactory
import com.example.sehatin.navigation.MainDestinations
import com.example.sehatin.view.components.BackgroundCurve
import com.example.sehatin.view.components.CustomButton
import com.example.sehatin.view.components.CustomTextField
import com.example.sehatin.view.screen.onboarding.OnBoardingViewModel
import kotlinx.coroutines.delay


fun setPassword(value: String) {

}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateToRoute: (String, Boolean) -> Unit,
) {

    val loginViewModel: LoginScreenViewModel = viewModel(
        factory = LoginViewModelFactory.getInstance(
            Resource.appContext
        )
    )

    val loginState by loginViewModel.loginState.collectAsStateWithLifecycle(initialValue = ResultResponse.None)

    val userState by loginViewModel.userState.collectAsStateWithLifecycle(initialValue = ResultResponse.None)

    val personalizeState by loginViewModel.isPersonalizeFilled()
        .collectAsStateWithLifecycle(initialValue = false)

    val focusManager = LocalFocusManager.current

    var showCircularProgress by remember { mutableStateOf(false) }

    val cloudVector = R.drawable.cloud
    val vectorIcon = R.drawable.login_1

    var showInvalidMessage by remember { mutableStateOf(false) }

    LaunchedEffect(showInvalidMessage) {
        if (showInvalidMessage) {
            delay(5000L)
            showInvalidMessage = false
        }
    }

    LaunchedEffect(loginState) {
        when (loginState) {
            is ResultResponse.Success -> {
                Log.e(
                    "LoginScreen",
                    "Login successful: ${(loginState as ResultResponse.Success).data}"
                )

//                print("doksaodk")

                loginViewModel.getUser()

//                navigateToRoute("${MainDestinations.DASHBOARD_ROUTE}?", true)
            }

            is ResultResponse.Loading -> {
                showCircularProgress = true
            }

            is ResultResponse.Error -> {
                showCircularProgress = false
                showInvalidMessage = true
                Log.e(
                    "LoginScreen",
                    "Login error: ${(loginState as ResultResponse.Error).error}"
                )
            }

            else -> {}
        }
    }

    LaunchedEffect(userState) {
        when (userState) {
            is ResultResponse.Success -> {
                showCircularProgress = false
                val detail = (userState as ResultResponse.Success).data.data
                Log.e("LoginScreen", "User detail: $detail")
                // Cek langsung pada detail yang diterima
                if (detail.isProfileComplete()) {
                    loginViewModel.setPersonalizeCompleted()
                    navigateToRoute(MainDestinations.DASHBOARD_ROUTE, true)
                } else {
                    navigateToRoute(MainDestinations.INPUT_NAME_ROUTE, true)
                }
            }
            is ResultResponse.Loading -> {
                showCircularProgress = true
            }
            is ResultResponse.Error -> {
                showCircularProgress = false
                Log.e("LoginScreen", "Get user error: ${(userState as ResultResponse.Error).error}")
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
//            .background(color = Color.Black)
                .fillMaxSize()
                .clickable(
                    onClick = {
                        focusManager.clearFocus()
                    },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Box(
                    modifier = Modifier
                ) {
                    BackgroundCurve(
                        containerHeight = 0.40f,
                        curveHeightRatio = 0.75f
                    )

                    Image(
                        painter = painterResource(id = cloudVector),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .offset(x = 5.dp, y = 80.dp)
                    )

                    Image(
                        painter = painterResource(id = cloudVector),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .offset(x = 180.dp, y = 40.dp)
                    )

                    Image(
                        painter = painterResource(id = cloudVector),
                        contentDescription = null,
                        modifier = Modifier
                            .size(90.dp)
                            .offset(x = 280.dp, y = 130.dp)
                    )

                    Surface(
                        modifier = Modifier
                            .size(10.dp)
                            .offset(x = 100.dp, y = 100.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.background
                    ) {
                    }

                    Surface(
                        modifier = Modifier
                            .size(10.dp)
                            .offset(x = 250.dp, y = 150.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.background
                    ) {
                    }

                    Surface(
                        modifier = Modifier
                            .size(10.dp)
                            .offset(x = 320.dp, y = 70.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.tertiary
                    ) {
                    }

                    Image(
                        painter = painterResource(id = vectorIcon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(300.dp)
                            .align(Alignment.BottomCenter)
                            .padding(top = 60.dp)
                    )
                }

            }

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
//                .background(color = Color.Blue)
                    .fillMaxHeight()
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_in),
                        modifier = Modifier.padding(bottom = 22.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    CustomTextField(
                        value = loginViewModel.emailValue,
                        placeholder = "Email",
                        onChange = loginViewModel::setEmail,
                        isError = loginViewModel.emailError.isNotEmpty(),
                        errorMessage = loginViewModel.emailError
                    )
                    CustomTextField(
                        value = loginViewModel.passwordValue,
                        placeholder = "Kata Sandi",
                        onChange = loginViewModel::setPassword,
                        isError = loginViewModel.passwordError.isNotEmpty(),
                        isPassword = true,
                        errorMessage = loginViewModel.passwordError
                    )
                    Text(
                        text = "Lupa kata sandi?",
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxWidth(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.End
                    )
                    CustomButton(
                        text = "Masuk",
//                isOutlined = true,
                        isAvailable = !showCircularProgress,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            focusManager.clearFocus()
                            Log.e("RegisterScreen", "Register button clicked")
                            loginViewModel.loginUser()
                        })
                    Box(
                        modifier = Modifier
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center,

                        ) {

                        Divider(
                            color = MaterialTheme.colorScheme.primary,
                            thickness = 1.dp,
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                        )
                        Text(
                            text = "atau",
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .background(color = MaterialTheme.colorScheme.background)
                                .padding(horizontal = 10.dp)
                        )
                    }
                    CustomButton(
                        text = "Lanjutkan dengan Google",
                        isOutlined = true,
                        borderWidth = 1.5.dp,
                        textColor = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Normal,
                        icon = painterResource(id = R.drawable.icon_google),
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                        })
                }
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(0.75f)
                        .padding(bottom = 25.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.dont_have_acc),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = (-0.15).sp
                    )
                    Text(
                        text = " " + stringResource(id = R.string.sign_up),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = (-0.15).sp,
                        modifier = Modifier.clickable {
                            navigateToRoute(MainDestinations.REGISTER_ROUTE, true)
                        }
                    )

                }
            }

            if (!showInvalidMessage) {
                Spacer(modifier = Modifier.height(24.dp))
            } else {
                AnimatedVisibility(
                    visible = showInvalidMessage,
                    enter = fadeIn() + scaleIn(initialScale = 0.8f),
                    exit = fadeOut(animationSpec = tween(durationMillis = 500)) + scaleOut(
                        targetScale = 0.8f
                    )
                ) {
                    Text(
                        text = "Email atau Kata sandi salah!",
                        color = Color.Red,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }
        }
        if (showCircularProgress) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}