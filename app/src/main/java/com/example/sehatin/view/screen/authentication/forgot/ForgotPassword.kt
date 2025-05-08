package com.example.sehatin.view.screen.authentication.forgot

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sehatin.R
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.navigation.MainDestinations
import com.example.sehatin.view.components.BackgroundCurve
import com.example.sehatin.view.components.CustomButton
import com.example.sehatin.view.components.CustomTextField
import com.example.sehatin.view.components.DynamicDialog
import com.example.sehatin.viewmodel.LoginScreenViewModel
import com.example.sehatin.viewmodel.OtpScreenViewModel
import kotlinx.coroutines.delay


fun setPassword(value: String) {

}

@Composable
fun ForgotPassword(
    modifier: Modifier = Modifier, onBackClick: () -> Unit,
    loginScreenViewModel: LoginScreenViewModel,
    navigateToRoute: (String, Boolean) -> Unit,
) {

    val reqOtpState by loginScreenViewModel.reqOtpState.collectAsStateWithLifecycle(initialValue = ResultResponse.None)

    val emailValue = Uri.encode(loginScreenViewModel.emailValue)
    var showCircularProgress by remember { mutableStateOf(false) }

    val cloudVector = R.drawable.cloud
    val vectorIcon = R.drawable.login_1

    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }
    var isDialogError by remember { mutableStateOf(false) }
    var isDialogSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        loginScreenViewModel.resetRequesOtpState()
    }

    LaunchedEffect(reqOtpState) {
        when (reqOtpState) {
            is ResultResponse.Success<*> -> {
                showCircularProgress = false

                showDialog = true
                dialogTitle = "Berhasil"
                dialogMessage = "Otp telah dikirim ke email $emailValue"
                isDialogSuccess = true
                isDialogError = false
                delay(
                    2000L
                )
                navigateToRoute("${MainDestinations.FORGOT_OTP_ROUTE}?" + "email=$emailValue", true)
            }

            is ResultResponse.Error -> {
                showCircularProgress = false
//                showCircularProgress = false
                showDialog = true
                dialogTitle = "Gagal"
                dialogMessage = "Gagal mengirim otp ke email $emailValue"
                isDialogError = true
                isDialogSuccess = false
                // Handle error state
            }

            is ResultResponse.Loading -> {
                // Handle loading state
                showCircularProgress = true
            }

            else -> {}
        }
    }




    Box(
        modifier = Modifier.fillMaxSize()
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


        // ✅ Tombol kembali absolute
        Box(
            modifier = Modifier
                .size(40.dp)
                .offset(x = 20.dp, y = 60.dp)
                .zIndex(1f)
                .shadow(2.5.dp, RoundedCornerShape(8.dp))
                .background(Color.White, RoundedCornerShape(8.dp))
                .border(1.dp, color = Color.Unspecified, shape = RoundedCornerShape(8.dp))
                .clickable(
                    onClick = onBackClick,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.back_arrow),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(20.dp)
                    .graphicsLayer(rotationZ = 180f)
            )
        }








        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().imePadding()){

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
//            .background(color = Color.Black)
                    .fillMaxSize()

                    .padding(top = 70.dp)

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
                            text = "Lupa Kata Sandi",
                            modifier = Modifier.padding(bottom = 3.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
//                Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Masukkan alamat email Anda untuk melakukan reset kata sandi",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.secondary,
                            lineHeight = 24.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(290.dp)
//                        .height(69.dp)
                                .padding(bottom = 10.dp)
                        )


                        CustomTextField(

                            value = loginScreenViewModel.emailValue,
                            placeholder = "Email",
                            onChange = loginScreenViewModel::setEmail,
                            isError = false,
//                    isPassword = true,
                            errorMessage = "Email tidak valid"
                        )
//


                        CustomButton(
                            text = "Kirim",
//                isOutlined = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            onClick = {
                                loginScreenViewModel.requestOtp()
                            })


                    }

                }
            }
        }
        if (showDialog) {
            DynamicDialog(
                title = dialogTitle,
                message = dialogMessage,
                onDismiss = { showDialog = false },
                isError = isDialogError,
                isSuccess = isDialogSuccess,
//                isWarning = isDialogWarning,
//                dismissText = "Batal"
            )
        }
        if (showCircularProgress) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f)
                    .background(Color.Black.copy(alpha = 0.8f))
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}