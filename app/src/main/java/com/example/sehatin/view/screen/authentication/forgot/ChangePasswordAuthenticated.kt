package com.example.sehatin.view.screen.authentication.forgot

import android.util.Log
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.sehatin.view.components.BackgroundCurve
import com.example.sehatin.view.components.CustomButton
import com.example.sehatin.view.components.CustomTextField
import com.example.sehatin.view.components.CustomTopAppBar
import com.example.sehatin.viewmodel.LoginScreenViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.sehatin.navigation.MainDestinations


@Composable
fun ChangePasswordAuth(
    modifier: Modifier = Modifier, onBackClick: () -> Unit,
    loginViewModel: LoginScreenViewModel, navigateToRoute: (String, Boolean) -> Unit,
) {
    val cloudVector = R.drawable.cloud
    val vectorIcon = R.drawable.login_1

    val changePasswordState by loginViewModel.changePasswordState.collectAsStateWithLifecycle(initialValue = ResultResponse.None)
    var showCircularProgress by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        loginViewModel.resetChangePasswordState()
    }


    LaunchedEffect(changePasswordState) {
        when (changePasswordState) {
            is ResultResponse.Success -> {
                showCircularProgress = false
                Log.e(
                    "Change Password Auth",
                    "Change Password Auth Sukses: ${(changePasswordState as ResultResponse.Success).data}"
                )
                navigateToRoute(MainDestinations.DASHBOARD_ROUTE, true)
            }

            is ResultResponse.Loading -> {
                showCircularProgress = true
            }

            is ResultResponse.Error -> {
                showCircularProgress = false
                Log.e(
                    "Change Password Auth",
                    "Change Password Auth error: ${(changePasswordState as ResultResponse.Error).error}"
                )

                // Display error message to the user
                // Toast.makeText(context, otpState.message, Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    Box(modifier = modifier.fillMaxSize()) {

        // ✅ Background dan ilustrasi di luar layout utama
        BackgroundCurve(containerHeight = 0.40f, curveHeightRatio = 0.75f)

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
        ) {}
        Surface(
            modifier = Modifier
                .size(10.dp)
                .offset(x = 250.dp, y = 150.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.background
        ) {}
        Surface(
            modifier = Modifier
                .size(10.dp)
                .offset(x = 320.dp, y = 70.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.tertiary
        ) {}

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

        // ✅ Konten utama
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp) // Disesuaikan agar tidak tabrakan dengan ilustrasi
        ) {
            Image(
                painter = painterResource(id = vectorIcon),
                contentDescription = null,
                modifier = Modifier.size(300.dp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(top = 5.dp)
            ) {
                Text(
                    text = "Ubah Kata Sandi Anda",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 3.dp)
                )

                Text(
                    text = "Masukkan kata sandi baru anda",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(290.dp)
                        .padding(bottom = 10.dp)
                )

                CustomTextField(
                    value = loginViewModel.oldPasswordValue,
                    placeholder = "Kata Sandi Lama",
                    onChange = loginViewModel::setOldPassword,
                    isError = false,
                    isPassword = true,
                    errorMessage = "Password is invalid"
                )

                CustomTextField(
                    value = loginViewModel.newPasswordValue,
                    placeholder = "Kata Sandi Baru",
                    onChange = loginViewModel::setNewPassword,
                    isError = false,
                    isPassword = true,
                    errorMessage = "Password is invalid"
                )

                CustomTextField(
                    value = loginViewModel.confirmPasswordValue,
                    placeholder = "Konfirmasi Kata Sandi",
                    onChange = loginViewModel::setConfirmPassword,
                    isError = false,
                    isPassword = true,
                    errorMessage = "Password is invalid"
                )

                CustomButton(
                    text = "Konfirmasi",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    onClick = {
                        loginViewModel.changePassword()
                    }
                )
            }
        }
    }
}

// Placeholder biar gak error
