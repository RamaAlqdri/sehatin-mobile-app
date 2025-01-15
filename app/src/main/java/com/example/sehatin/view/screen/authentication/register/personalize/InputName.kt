package com.example.sehatin.view.screen.authentication.register.personalize

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehatin.view.components.CustomButton
import com.example.sehatin.view.components.CustomTextField
import com.example.sehatin.view.screen.authentication.register.setPassword


fun setPassword(value: String) {

}
@Composable
fun InputName(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
//            .background(color = Color.Black)
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
//                .background(color = Color.Blue)
//                .fillMaxHeight(0.35f)
                .padding(top = 120.dp)
                .fillMaxWidth(0.75f)

        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Text(text = "1/7",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth())
                Text(text = "Siapa nama Anda?",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth())
                Text(text = "Kami ingin menyapa Anda dengan nama untuk pengalaman yang lebih personal",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .fillMaxWidth())

            }
            Column (
                modifier = Modifier
                    .padding(top = 50.dp)
            ){

                CustomTextField(value = "",
                    placeholder = "Nama Anda",
                    onChange = ::setPassword,
                    isError = false,
//                isPassword = true,
                    errorMessage = "Otp is invalid",

                    )
            }
        }
        CustomButton (
            text = "Selanjutnya",
            modifier = Modifier
                .padding(bottom =65.dp)

        ) {

        }
    }
}