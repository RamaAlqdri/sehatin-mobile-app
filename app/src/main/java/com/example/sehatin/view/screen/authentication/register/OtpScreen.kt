package com.example.sehatin.view.screen.authentication.register

import android.net.Uri
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sehatin.R
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.resource.Resource
import com.example.sehatin.di.factory.OtpViewModelFactory
import com.example.sehatin.navigation.MainDestinations
import com.example.sehatin.view.components.BackgroundCurve
import com.example.sehatin.view.components.CustomButton
import com.example.sehatin.view.components.CustomTextField
import com.example.sehatin.viewmodel.OtpScreenViewModel
import kotlinx.coroutines.delay


fun setPassword(value: String) {

}

@Composable
fun OtpScreen(
    modifier: Modifier = Modifier,
    email: String,
    navigateToRoute: (String, Boolean) -> Unit,
) {

    val otpViewModel: OtpScreenViewModel = viewModel(
        factory = OtpViewModelFactory.getInstance(
            Resource.appContext
        )
    )

    var countdown by remember { mutableIntStateOf(0) }

    Log.e("OtpScreen", "data ${otpViewModel.otpData}")

    val otpState by otpViewModel.verifyOtpState.collectAsStateWithLifecycle(initialValue = ResultResponse.None)
    Log.e("OtPSTET", "otpState: $otpState")
    val decodedEmail: String = Uri.decode(email)
    otpViewModel.setEmail(decodedEmail)
    Log.e("OtpScreen", "email: ${otpViewModel.emailValue}")

    val cloudVector = R.drawable.cloud
    val vectorIcon = R.drawable.otp_1
    val focusManager = LocalFocusManager.current
    var showCircularProgress by remember { mutableStateOf(false) }

    var showInvalidOtpMessage by remember { mutableStateOf(false) }

    LaunchedEffect(otpState) {
        when (otpState) {
            is ResultResponse.Success -> {
                showCircularProgress = false
                Log.e(
                    "OtpScreen",
                    "Otp Sukses: ${(otpState as ResultResponse.Success).data}"
                )
                navigateToRoute(MainDestinations.LOGIN_ROUTE, true)
            }

            is ResultResponse.Loading -> {
                showCircularProgress = true
            }

            is ResultResponse.Error -> {
                showCircularProgress = false
                Log.e(
                    "RegisterScreen",
                    "Registration error: ${(otpState as ResultResponse.Error).error}"
                )

                showInvalidOtpMessage = true
                // Display error message to the user
                // Toast.makeText(context, otpState.message, Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    LaunchedEffect(showInvalidOtpMessage) {
        if (showInvalidOtpMessage) {
            delay(1000L)
            showInvalidOtpMessage = false
        }
    }

    LaunchedEffect(countdown) {
        if (countdown > 0) {
            delay(2000L)
            countdown -= 1
        }
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        val title = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append("Tidak menerima kode? ")
            }
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Normal
                )
            ) {
                if (countdown > 0) {
                    append("$countdown")
                } else {
                    append("Resend")
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
//            .background(color = Color.Black)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
//                .background(color = Color.Red)
                    .wrapContentHeight()
            ) {

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
                        shape = CircleShape, // menentukan bentuk lingkaran
                        color = MaterialTheme.colorScheme.tertiary // warna latar lingkaran
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
                        text = stringResource(id = R.string.otp_tittle),
                        modifier = Modifier.padding(bottom = 3.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
//                Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.otp_desc),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.secondary,
                        lineHeight = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(290.dp)
//                        .height(69.dp)
                            .padding(bottom = 10.dp)
                    )


                    CustomTextField(

                        value = otpViewModel.otpValue,
                        placeholder = "OTP",
                        onChange = otpViewModel::setOtp,
                        isError = false,
                        isPassword = true,
                        keyboardType = KeyboardType.NumberPassword,
                        errorMessage = "Otp is invalid"
                    )

                    Row(
                        modifier = Modifier
//                        .padding(top = 10.dp)
                            .fillMaxWidth(01f),
//                        .padding(bottom = 25.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Tidak menerima kode? ",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = (-0.15).sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = if (countdown > 0) "$countdown" else "Resend",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.15).sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                if (countdown == 0) {
                                    countdown = 30
                                    // Panggil fungsi request OTP ulang di sini, misalnya:
                                    // otpViewModel.requestOtp()
                                }
                            }
                        )

                    }


                    CustomButton(
                        text = "Verifikasi",
//                isOutlined = true,
                        isAvailable = !showCircularProgress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        onClick = {
                            focusManager.clearFocus()
                            otpViewModel.verifyOtp()
                        })

                    if (!showInvalidOtpMessage) {
                        Spacer(modifier = Modifier.height(24.dp))
                    } else {
                        AnimatedVisibility(
                            visible = showInvalidOtpMessage,
                            enter = fadeIn() + scaleIn(initialScale = 0.8f),
                            exit = fadeOut(animationSpec = tween(durationMillis = 500)) + scaleOut(
                                targetScale = 0.8f
                            )
                        ) {
                            Text(
                                text = "Kode Otp Salah!",
                                color = Color.Red,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                        }
                    }


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