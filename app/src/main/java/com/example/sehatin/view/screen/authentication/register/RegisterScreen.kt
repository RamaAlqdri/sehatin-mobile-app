package com.example.sehatin.view.screen.authentication.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehatin.R
import com.example.sehatin.view.components.BackgroundCurve
import com.example.sehatin.view.components.CustomButton
import com.example.sehatin.view.components.CustomTextField




@Composable
fun RegisterScreen(modifier: Modifier = Modifier) {
    val cloudVector = R.drawable.cloud
    val vectorIcon = R.drawable.login_1
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
//            .background(color = Color.Black)
            .fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
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
                        .size(300.dp,190.dp)
//                        .background(color = Color.Black)
                        .align(Alignment.BottomCenter)

                        .padding(bottom = 10.dp)
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
                    text = stringResource(id = R.string.create_acc),
                    modifier = Modifier.padding(bottom = 22.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                CustomTextField(
                    value = "",
                    placeholder = "Email",
                    onChange = ::setPassword,
                    isError = false,
                    errorMessage = "Password"
                )
                CustomTextField(
                    value = "",
                    placeholder = "Kata Sandi",
                    onChange = ::setPassword,
                    isError = false,
                    isPassword = true,
                    errorMessage = "Password"
                )
                CustomTextField(
                    value = "",
                    placeholder = "Konfirmasi Kata Sandi",
                    onChange = ::setPassword,
                    isError = false,
                    isPassword = true,
                    errorMessage = "Password"
                )

                CustomButton(
                    text = stringResource(id = R.string.sign_up),
//                isOutlined = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    onClick = {
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
                    text = stringResource(id = R.string.already_have_acc),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                    letterSpacing = -0.15.sp
                )
                Text(
                    text = " " + stringResource(id = R.string.sign_in),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    letterSpacing = -0.15.sp
                )

            }
        }
    }
}