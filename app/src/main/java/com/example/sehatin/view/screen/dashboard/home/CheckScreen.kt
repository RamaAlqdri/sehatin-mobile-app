package com.example.sehatin.view.screen.dashboard.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sehatin.navigation.MainDestinations
import com.example.sehatin.view.screen.authentication.login.LoginScreenViewModel

@Composable
fun CheckScreen(
    onNavigate: (String, Boolean) -> Unit,
    onBoardingStatus: Boolean,
    isUserLoggedIn: Boolean,
    isPersonalizeCompleted: Boolean,
    explicitAuthFlow: String?
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
    }

    LaunchedEffect(onBoardingStatus, isUserLoggedIn, isPersonalizeCompleted, explicitAuthFlow) {
        // Jika explicitAuthFlow tidak kosong, gunakan itu
        val destination = if (explicitAuthFlow?.isNotEmpty() == true) {
            explicitAuthFlow
        } else {
            when {
                !onBoardingStatus -> MainDestinations.ONBOARDING_ROUTE
                !isUserLoggedIn -> MainDestinations.LOGIN_ROUTE
                !isPersonalizeCompleted -> MainDestinations.INPUT_NAME_ROUTE
                else -> MainDestinations.DASHBOARD_ROUTE
            }
        }
        // Pastikan destination tidak kosong sebelum navigasi
        if (destination.isNotEmpty()) {
            onNavigate(destination, true)
        } else {
            Log.e("CheckScreen", "Destination is empty, navigation aborted")
        }
    }
}