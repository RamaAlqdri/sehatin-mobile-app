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
fun InputAge(modifier: Modifier = Modifier) {
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
                .fillMaxWidth(0.75f)

        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Text(text = "2/8",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth())
                Text(text = "How old are you?",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth())
                Text(text = "Your age helps us calculate the best diet and workout plans for you.",
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


                AgeDisplay(selectedDate = selectedDate)
                CustomDatePicker(
                    label = "Select a Date",
                    selectedDate = selectedDate,
                    onDateSelected = {
                        selectedDate = it
                        isError = it.isEmpty() // Contoh validasi
                    },
                    isError = isError,
                    errorMessage = "Date cannot be empty"
                )
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