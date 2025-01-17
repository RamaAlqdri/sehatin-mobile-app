package com.example.sehatin.view.screen.authentication.register.personalize

import CustomScrollInput
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehatin.view.components.CustomButton


@Composable
fun InputWeight(modifier: Modifier = Modifier) {
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
                Text(text = "5/7",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth())
                Text(text = "Berapa berat badan Anda?",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth())
                Text(text = "Ini membantu kami menghitung BMI Anda dan merekomendasikan diet dan latihan yang dipersonalisasi",
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

                CustomScrollInput(
                    firstUnitMax = 220,
                    secondUnitMax = 440,
                    unitOptions = listOf(
                        Option(0,"kg"),
                        Option(1,"lbs")
                    )
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