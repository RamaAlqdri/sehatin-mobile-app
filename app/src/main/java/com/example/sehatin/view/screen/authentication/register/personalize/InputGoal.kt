package com.example.sehatin.view.screen.authentication.register.personalize

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.sehatin.view.components.AgeDisplay
import com.example.sehatin.view.components.CustomButton
import com.example.sehatin.view.components.CustomDatePicker
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import com.example.sehatin.R
import com.example.sehatin.view.components.CustomGenderRadioButton
import com.example.sehatin.view.components.CustomRadioButton

@Composable
fun InputGoal(modifier: Modifier = Modifier) {
    val vectorImages = listOf(
        R.drawable.weight,
        R.drawable.muscle,
        R.drawable.healthy,
    )
    val options = listOf(
        Option(0, "Menurunkan berat badan"),
        Option(1, "Membentuk otot"),
        Option(2, "Tetap sehat"),

    )
    var selectedOption by remember { mutableStateOf(options[0]) }
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
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "7/7",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "Apa tujuan diet Anda?",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "Beri tahu kami apa yang ingin Anda capai sehingga kami dapat menyesuaikan rencana untuk Anda",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .fillMaxWidth()
                )

            }
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier
                    .padding(top = 20.dp)

            ) {


                options.forEach { option ->
                    CustomRadioButton(
                        icon = painterResource(id = vectorImages[option.index]),
                        selected = (option == selectedOption),
                        onClick = { selectedOption = option },
//                        defaultIconSize = 70.dp,
                        label = option.label, // Menggunakan label dari objek option

                    )
                }
            }
        }
        CustomButton(
            text = "Selanjutnya",
            modifier = Modifier
                .padding(bottom = 65.dp)

        ) {

        }
    }
}