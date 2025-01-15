package com.example.sehatin.view.screen.authentication.register.personalize

import CustomHeightInput
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehatin.view.components.AgeDisplay
import com.example.sehatin.view.components.CustomButton
import com.example.sehatin.view.components.CustomDatePicker
import com.example.sehatin.view.components.CustomTextField
import com.example.sehatin.view.screen.authentication.register.setPassword



@Composable
fun InputHeight(modifier: Modifier = Modifier) {
    var selectedDate by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }
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
                .fillMaxWidth(0.90f)

        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Text(text = "4/7",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth())
                Text(text = "Berapa tinggi badan Anda?",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth())
                Text(text = "Kami membutuhkan tinggi badan Anda untuk menghitung komposisi tubuh dan berat badan ideal Anda",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .fillMaxWidth())

            }
            Column (
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier
                    .padding(top = 50.dp)

            ){

                CustomHeightInput()

            }
        }
        CustomButton (
            text = "Next",
            modifier = Modifier
                .padding(bottom =65.dp)

        ) {

        }
    }
}