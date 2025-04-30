package com.example.sehatin.view.screen.authentication.register.personalize

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.navigation.MainDestinations
import com.example.sehatin.view.components.CustomButton
import com.example.sehatin.view.components.CustomTextField
import com.example.sehatin.view.screen.authentication.register.setPassword
import com.example.sehatin.viewmodel.PersonalizeViewModel


fun setPassword(value: String) {

}

@Composable
fun InputName(
    modifier: Modifier = Modifier,
    navigateToRoute: (String, Boolean) -> Unit,
    personalizeViewModel: PersonalizeViewModel
) {

    val focusManager = LocalFocusManager.current

    val selectionState by personalizeViewModel.personalizeState.collectAsStateWithLifecycle(
        initialValue = ResultResponse.None
    )

    var showCircularProgress by remember { mutableStateOf(false) }

    LaunchedEffect(selectionState) {
        when (selectionState) {
            is ResultResponse.Success -> {
                showCircularProgress = false
                navigateToRoute(MainDestinations.INPUT_AGE_ROUTE, true)
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
                .clickable(
                    onClick = {
                        focusManager.clearFocus()
                    },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
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
                        text = "1/7",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = "Siapa nama Anda?",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = "Kami ingin menyapa Anda dengan nama untuk pengalaman yang lebih personal",
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                }
                Column(
                    modifier = Modifier
                        .padding(top = 70.dp)
                ) {

                    CustomTextField(
                        value = personalizeViewModel.usernameValue,
                        placeholder = "Nama Anda",
                        onChange = personalizeViewModel::setUsername,
                        isError = false,
//                isPassword = true,
                        errorMessage = "Invalid",
                    )
                }
            }
            CustomButton(
                text = "Selanjutnya",
                onClick = {
                    focusManager.clearFocus()
                    personalizeViewModel.inputName()
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