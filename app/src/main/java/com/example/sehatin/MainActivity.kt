package com.example.sehatin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsetsController
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.compose.SehatInTheme
import com.example.sehatin.view.screen.authentication.forgot.ChangePassword
import com.example.sehatin.view.screen.authentication.forgot.ForgotPassword
import com.example.sehatin.view.screen.authentication.login.LoginScreen
import com.example.sehatin.view.screen.authentication.register.OtpScreen
import com.example.sehatin.view.screen.authentication.register.RegisterScreen
import com.example.sehatin.view.screen.authentication.register.personalize.InputActivity
import com.example.sehatin.view.screen.authentication.register.personalize.InputAge
import com.example.sehatin.view.screen.authentication.register.personalize.InputGender
import com.example.sehatin.view.screen.authentication.register.personalize.InputGoal
import com.example.sehatin.view.screen.authentication.register.personalize.InputHeight
import com.example.sehatin.view.screen.authentication.register.personalize.InputName
import com.example.sehatin.view.screen.authentication.register.personalize.InputWeight
import com.example.sehatin.view.screen.onboarding.OnBoardingScreen
import com.valentinilk.shimmer.ShimmerBounds.View

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        createNotificationChannel(this)
        splashScreen.setKeepOnScreenCondition { true }
        window.statusBarColor = Color.Transparent.toArgb()
        this.window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_VISIBLE
//        window.navigationBarColor = Color.Black.toArgb()
//        window.navigationBarDividerColor = Color.Transparent.toArgb()
        this.getWindow().getDecorView().getWindowInsetsController()
            ?.setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);




        Handler(Looper.getMainLooper()).postDelayed({
            splashScreen.setKeepOnScreenCondition { false }
        }, 2500)
        setContent {
            SehatInApp()
        }
    }


    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "diet_channel"
            val channelName = "Diet Reminders"
            val channelDescription = "Pengingat jadwal diet"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}



