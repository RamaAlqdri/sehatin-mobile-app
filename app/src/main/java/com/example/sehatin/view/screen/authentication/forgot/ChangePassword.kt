package com.example.sehatin.view.screen.authentication.forgot

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sehatin.R
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.navigation.MainDestinations
import com.example.sehatin.view.components.BackgroundCurve
import com.example.sehatin.view.components.CustomButton
import com.example.sehatin.view.components.CustomTextField
import com.example.sehatin.viewmodel.LoginScreenViewModel


@Composable
fun ChangePassword(
    modifier: Modifier = Modifier,
    loginScreenViewModel: LoginScreenViewModel,
    navigateToRoute: (String, Boolean) -> Unit
) {


    val resetPasswordState by loginScreenViewModel.resetPasswordState.collectAsStateWithLifecycle(
        initialValue = ResultResponse.None
    )
    var showCircularProgress by remember { mutableStateOf(false) }


    LaunchedEffect(resetPasswordState) {
        when (resetPasswordState) {
            is ResultResponse.Success -> {
                showCircularProgress = false
                Log.e(
                    "Change Password Screen",
                    "Ganti Password Sukses: ${(resetPasswordState as ResultResponse.Success).data}"
                )
                navigateToRoute(MainDestinations.LOGIN_ROUTE, true)
            }

            is ResultResponse.Loading -> {
                showCircularProgress = true
            }

            is ResultResponse.Error -> {
                showCircularProgress = false
                Log.e(
                    "Change Pass screen",
                    "Change pass error: ${(resetPasswordState as ResultResponse.Error).error}"
                )

                // Display error message to the user
                // Toast.makeText(context, otpState.message, Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }


    val cloudVector = R.drawable.cloud
    val vectorIcon = R.drawable.login_1
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
//            .background(color = Color.Black)
                .fillMaxSize().imePadding()
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
                        text = "Ubah Kata Sandi Anda",
                        modifier = Modifier.padding(bottom = 3.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
//                Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Masukkan kata sandi baru anda",
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

                        value = loginScreenViewModel.newPasswordValue,
                        placeholder = "Kata Sandi Baru",
                        onChange = loginScreenViewModel::setNewPassword,
                        isError = false,
                        isPassword = true,
                        errorMessage = "Kata sandi tidak valid"
                    )
                    CustomTextField(

                        value = loginScreenViewModel.confirmPasswordValue,
                        placeholder = "Konfirmasi Kata Sandi",
                        onChange = loginScreenViewModel::setConfirmPassword,
                        isError = false,
                        isPassword = true,
                        errorMessage = "Kata sandi tidak valid"
                    )
//                Row(
//                    modifier = Modifier
////                        .padding(top = 10.dp)
//                        .fillMaxWidth(01f),
////                        .padding(bottom = 25.dp),
//                    horizontalArrangement = Arrangement.End
//                ) {
//                    Text(
//                        text = "Didn't receive the code?",
//                        fontSize = 12.sp,
//                        fontWeight = FontWeight.Medium,
//                        color = MaterialTheme.colorScheme.onBackground,
//                        letterSpacing = -0.15.sp
//                    )
//                    Text(
//                        text = " " + "Resend OTP",
//                        fontSize = 12.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.colorScheme.onBackground,
//                        letterSpacing = -0.15.sp
//                    )
//
//                }


                    CustomButton(
                        text = "Ubah",
//                isOutlined = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        onClick = {
                            loginScreenViewModel.resetPassword()
                        })


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