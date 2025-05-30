package com.example.sehatin.view.screen.authentication.register.personalize

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
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
import com.example.sehatin.view.components.DynamicDialog
import com.example.sehatin.viewmodel.LoginScreenViewModel
import com.example.sehatin.viewmodel.PersonalizeViewModel


data class OptionGoal(val index: Int, val label: String, val level: String)

@Composable
fun InputGoal(
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

    val userState by loginScreenViewModel.userState.collectAsStateWithLifecycle(initialValue = ResultResponse.None)

//    val personalizeState by loginScreenViewModel.isPersonalizeFilled()
//        .collectAsStateWithLifecycle(initialValue = false)

    val personalizeState by personalizeViewModel.personalizeState.collectAsStateWithLifecycle(
        initialValue = ResultResponse.None
    )
    var shouldCheckUserState by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }
    var isDialogError by remember { mutableStateOf(false) }
    var isDialogSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(selectionState) {
        when (selectionState) {
            is ResultResponse.Success -> {
                showCircularProgress = false
                loginScreenViewModel.getUser()
                showDialog = true
                dialogTitle = "Berhasil"
                dialogMessage = "Pendaftaran berhasil..."
                isDialogSuccess = true
                isDialogError = false

            }

            is ResultResponse.Loading -> {
                showCircularProgress = true
            }

            is ResultResponse.Error -> {
                showCircularProgress = false
                showDialog = true
                dialogTitle = "Gagal"
                dialogMessage = "Pendaftaran gagal"
                isDialogError = true
                isDialogSuccess = false
            }

            else -> {}
        }
    }

    LaunchedEffect(personalizeState) {
        when (personalizeState) {
            is ResultResponse.Success -> {
                loginScreenViewModel.getUser()
                showCircularProgress = false
                shouldCheckUserState = true

//                val detail = (userState as ResultResponse.Success).data.data
//                Log.e("GoalScreen", "User detail: $detail")
//                // Cek langsung pada detail yang diterima
//                if (detail.isProfileComplete()) {
//                    loginScreenViewModel.setPersonalizeCompleted()
//                    navigateToRoute(MainDestinations.DASHBOARD_ROUTE, true)
//                }
//                else {
//                    navigateToRoute(MainDestinations.INPUT_NAME_ROUTE, true)
//                }
            }
            is ResultResponse.Loading -> {
                showCircularProgress = true
            }
            is ResultResponse.Error -> {
                showCircularProgress = false
                Log.e("LoginScreen", "Get user error: ${(userState as ResultResponse.Error).error}")
            }
            else -> {}
        }
    }

    LaunchedEffect(userState, shouldCheckUserState) {
        if (shouldCheckUserState && userState is ResultResponse.Success) {
            val detail = (userState as ResultResponse.Success).data.data
            Log.e("GoalScreen", "User detail updated: $detail")
            if (detail.isProfileComplete()) {
                loginScreenViewModel.setPersonalizeCompleted()
                navigateToRoute(MainDestinations.DASHBOARD_ROUTE, true)
            } else {
                navigateToRoute(MainDestinations.INPUT_NAME_ROUTE, true)
            }
            shouldCheckUserState = false // reset biar tidak ter-trigger ulang
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
                .imePadding()
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
                        .padding(top = 100.dp)

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