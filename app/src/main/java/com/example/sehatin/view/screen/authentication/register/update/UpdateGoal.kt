package com.example.sehatin.view.screen.authentication.register.update

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehatin.view.components.CustomButton
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sehatin.R
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.navigation.DetailDestinations
import com.example.sehatin.navigation.MainDestinations
import com.example.sehatin.view.components.CustomRadioButton
import com.example.sehatin.viewmodel.LoginScreenViewModel
import com.example.sehatin.viewmodel.PersonalizeViewModel


data class OptionGoal(val index: Int, val label: String, val level: String)

@Composable
fun UpdateGoal(
    modifier: Modifier = Modifier,
    navigateToRoute: (String, Boolean) -> Unit,
    personalizeViewModel: PersonalizeViewModel,
    loginScreenViewModel: LoginScreenViewModel
) {
    val vectorImages = listOf(
        R.drawable.weight,
        R.drawable.muscle,
        R.drawable.healthy,
    )
    val options = listOf(
        OptionGoal(0, "Menurunkan berat badan", "weight"),
        OptionGoal(1, "Membentuk otot", "muscle"),
        OptionGoal(2, "Tetap sehat", "health"),

        )
    var selectedOption by remember { mutableStateOf(options[0]) }

    var showCircularProgress by remember { mutableStateOf(false) }

    LaunchedEffect(selectedOption) {
        Log.e("selectedOption", selectedOption.level)
    }

    val selectionState by personalizeViewModel.personalizeState.collectAsStateWithLifecycle(
        initialValue = ResultResponse.None
    )


    val personalizeState by loginScreenViewModel.isPersonalizeFilled()
        .collectAsStateWithLifecycle(initialValue = false)

    LaunchedEffect(selectionState) {
        when (selectionState) {
            is ResultResponse.Success -> {
                showCircularProgress = false
                navigateToRoute(MainDestinations.DASHBOARD_ROUTE,true)
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
                        text = "4/4",
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
                        .padding(top = 50.dp)

                ) {


                    options.forEach { option ->
                        CustomRadioButton(
                            icon = painterResource(id = vectorImages[option.index]),
                            selected = (option == selectedOption),
                            onClick = {
                                selectedOption = option
                                personalizeViewModel.setGoal(option.level)
                            },
//                        defaultIconSize = 70.dp,
                            label = option.label, // Menggunakan label dari objek option

                        )
                    }
                }
            }

            CustomButton(
                text = "Selanjutnya",
                onClick = {
                    personalizeViewModel.inputGoal()
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