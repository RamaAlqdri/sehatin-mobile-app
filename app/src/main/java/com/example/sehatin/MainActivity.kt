package com.example.sehatin

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.AppTheme
import com.example.sehatin.view.screen.authentication.forgot.ChangePassword
import com.example.sehatin.view.screen.authentication.forgot.ForgotPassword
import com.example.sehatin.view.screen.authentication.login.LoginScreen
import com.example.sehatin.view.screen.authentication.register.OtpScreen
import com.example.sehatin.view.screen.authentication.register.RegisterScreen
import com.example.sehatin.view.screen.authentication.register.personalize.InputAge
import com.example.sehatin.view.screen.authentication.register.personalize.InputGender
import com.example.sehatin.view.screen.authentication.register.personalize.InputHeight
import com.example.sehatin.view.screen.authentication.register.personalize.InputName
import com.example.sehatin.view.screen.onboarding.OnBoardingScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
//                SehatinApp()
//                LoginScreen()
//                OnBoardingScreen()
//                RegisterScreen()
//                OtpScreen()
//                ChangePassword()
                InputHeight()
            }
        }
    }
}



