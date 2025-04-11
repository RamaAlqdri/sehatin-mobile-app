package com.example.sehatin.view.screen.authentication.register.personalize

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sehatin.R
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.navigation.MainDestinations
import com.example.sehatin.view.components.CustomGenderRadioButton
import com.example.sehatin.view.components.CustomRadioButton

data class RadioOption(val index: Int, val label: String, val desc: String, val level: String)

@Composable
fun InputActivity(
    modifier: Modifier = Modifier,
    navigateToRoute: (String, Boolean) -> Unit,
    personalizeViewModel: PersonalizeViewModel
) {
//    val vectorImages = listOf(
//        R.drawable.ic_male,
//        R.drawable.ic_female
//    )


    val options = listOf(
        RadioOption(0, "Tidak banyak bergerak", "Sedikit atau tidak berolahraga", "male"),
        RadioOption(1, "Aktif ringan", "berolahraga 1-3 hari per minggu", "female"),
        RadioOption(2, "Cukup aktif", "berolahraga 3-5 hari per minggu", "moderately"),
        RadioOption(3, "Sangat aktif", "berolahraga 6-7 hari per minggu", "heavy"),
    )
    var selectedOption by remember { mutableStateOf(options[0]) }

    LaunchedEffect(selectedOption) {
        Log.e("selectedOption", selectedOption.label)
    }

    var showCircularProgress by remember { mutableStateOf(false) }

    val selectionState by personalizeViewModel.personalizeState.collectAsStateWithLifecycle(
        initialValue = ResultResponse.None
    )

    LaunchedEffect(selectionState) {
        when (selectionState) {
            is ResultResponse.Success -> {
                showCircularProgress = false
                navigateToRoute(MainDestinations.INPUT_GOAL_ROUTE, true)
                personalizeViewModel.setPersonalizeState(ResultResponse.None)

            }

            is ResultResponse.Loading -> {
                showCircularProgress = true
            }

            is ResultResponse.Error -> {
                showCircularProgress = false
                personalizeViewModel.setPersonalizeState(ResultResponse.None)


            }

            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

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
                        text = "6/7",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = "Seberapa aktif Anda?",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = "Kami akan menyesuaikan rencana Anda berdasarkan tingkat aktivitas harian Anda",
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
//                        icon = painterResource(id = vectorImages[option.index]),
                            selected = (option == selectedOption),
                            onClick = {
                                selectedOption = option
                                personalizeViewModel.setActivityLevel(selectedOption.level)
                            },
                            defaultIconSize = 70.dp,
                            label = option.label, // Menggunakan label dari objek option
                            description = option.desc
                        )
                    }
                }
            }
            CustomButton(
                text = "Selanjutnya",
                onClick = {
                    personalizeViewModel.inputActivity()
                },
                modifier = Modifier
                    .padding(bottom = 65.dp)

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