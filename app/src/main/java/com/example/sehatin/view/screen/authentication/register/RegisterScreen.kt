package com.example.sehatin.view.screen.authentication.register

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.sehatin.di.factory.RegisterViewModelFactory
import com.example.sehatin.navigation.MainDestinations
import com.example.sehatin.view.components.BackgroundCurve
import com.example.sehatin.view.components.CustomButton
import com.example.sehatin.view.components.CustomTextField
import com.example.sehatin.view.components.DynamicDialog
import com.example.sehatin.viewmodel.RegisterScreenViewModel
import kotlinx.coroutines.delay


@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navigateToRoute: (String, Boolean) -> Unit,
) {

    val registerViewModel: RegisterScreenViewModel = viewModel(
        factory = RegisterViewModelFactory.getInstance(
            Resource.appContext
        )
    )


    val registerState by registerViewModel.registerState.collectAsStateWithLifecycle(initialValue = ResultResponse.None)

    val emailValue = Uri.encode(registerViewModel.emailValue)

    var showCircularProgress by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }
    var isDialogError by remember { mutableStateOf(false) }
    var isDialogSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        registerViewModel.resetRegisterState()
    }

    LaunchedEffect(registerState) {
        when (registerState) {
            is ResultResponse.Success -> {
                showCircularProgress = false
//                Log.d(
//                    "RegisterScreen",
//                    "Registration successful: ${(registerState as ResultResponse.Success).data}"
//                )
//                Log.e("tempLog", "${registerViewModel.tempLog}")
                showDialog = true
                dialogTitle = "Berhasil"
                dialogMessage = "Pendaftaran akun berhasil..."
                isDialogSuccess = true
                isDialogError = false
                delay(2000L)
                navigateToRoute("${MainDestinations.OTP_ROUTE}?" + "email=$emailValue", true)
            }

            is ResultResponse.Loading -> {
                showCircularProgress = true
            }

            is ResultResponse.Error -> {
                showCircularProgress = false
                showDialog = true
                dialogTitle = "Gagal"
                dialogMessage = "Pendaftaran Akun gagal"
                isDialogError = true
                isDialogSuccess = false
            }

            else -> {}
        }
    }

    val focusManager = LocalFocusManager.current


    val cloudVector = R.drawable.cloud
    val vectorIcon = R.drawable.login_1
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
//            .background(color = Color.Black)
                .fillMaxSize()
                .imePadding()
                .clickable(
                    onClick = {
                        focusManager.clearFocus()
                    },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
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
                        containerHeight = 0.35f,
                        curveHeightRatio = 0.65f
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
                            .size(300.dp, 190.dp)
//                        .background(color = Color.Black)
                            .align(Alignment.BottomCenter)

                            .padding(bottom = 10.dp)
                    )
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
                            text = stringResource(id = R.string.create_acc),
                            modifier = Modifier.padding(bottom = 22.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        CustomTextField(
                            value = registerViewModel.emailValue,
                            placeholder = "Email",
                            onChange = registerViewModel::setEmail,
                            isError = registerViewModel.emailError.isNotEmpty(),
                            errorMessage = registerViewModel.emailError
                        )

                        CustomTextField(
                            value = registerViewModel.passwordValue,
                            placeholder = "Kata Sandi",
                            onChange = registerViewModel::setPassword,
                            isError = registerViewModel.passwordError.isNotEmpty(),
                            isPassword = true,
                            errorMessage = registerViewModel.passwordError
                        )

                        CustomTextField(
                            value = registerViewModel.confirmPasswordValue,
                            placeholder = "Konfirmasi Kata Sandi",
                            onChange = registerViewModel::setConfirmPassword,
                            isError = registerViewModel.confirmPasswordError.isNotEmpty(),
                            isPassword = true,
                            errorMessage = registerViewModel.confirmPasswordError
                        )

                        CustomButton(
                            text = stringResource(id = R.string.sign_up),
//                isOutlined = true,
                            isAvailable = !showCircularProgress,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            onClick = {
                                focusManager.clearFocus()
                                Log.e("RegisterScreen", "Register button clicked")
                                Log.e("RegisterScreen", "$registerState")
////                                Log.e("RegisterScreen", "$otpValue")
                                registerViewModel.registerUser()
                            })
                        Box(
                            modifier = Modifier
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center,

                            ) {

//                            Divider(
//                                color = MaterialTheme.colorScheme.primary,
//                                thickness = 1.dp,
//                                modifier = Modifier
//                                    .padding(vertical = 10.dp)
//                            )
//                            Text(
//                                text = "atau",
//                                textAlign = TextAlign.Center,
//                                fontSize = 12.sp,
//                                color = MaterialTheme.colorScheme.primary,
//                                fontWeight = FontWeight.Medium,
//                                modifier = Modifier
//                                    .background(color = MaterialTheme.colorScheme.background)
//                                    .padding(horizontal = 10.dp)
//                            )
                        }

//                        CustomButton(
//                            text = "Lanjutkan dengan Google",
//                            isOutlined = true,
//                            borderWidth = 1.5.dp,
//                            textColor = MaterialTheme.colorScheme.onBackground,
//                            fontWeight = FontWeight.Normal,
//                            icon = painterResource(id = R.drawable.icon_google),
//                            modifier = Modifier.fillMaxWidth(),
//                            onClick = {
//                            })
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth(0.75f)
                            .padding(bottom = 25.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.already_have_acc),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onBackground,
                            letterSpacing = (-0.15).sp
                        )
                        Text(
                            text = " " + stringResource(id = R.string.sign_in),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            letterSpacing = (-0.15).sp,
                            modifier = Modifier.clickable {
                                navigateToRoute(MainDestinations.LOGIN_ROUTE, true)
                            }
                        )

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
                    .background(Color.Black.copy(alpha = 0.8f))
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}