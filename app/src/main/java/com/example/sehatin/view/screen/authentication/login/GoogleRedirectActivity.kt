package com.example.sehatin.view.screen.authentication.login


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.sehatin.MainActivity
import com.example.sehatin.data.store.DataStoreManager
import kotlinx.coroutines.launch

class GoogleRedirectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uri = intent?.data
        val token = uri?.getQueryParameter("access_token")

        if (!token.isNullOrEmpty()) {
            val dataStoreManager = DataStoreManager(this)

            lifecycleScope.launch {
                dataStoreManager.saveUserToken(token)
                dataStoreManager.setUserLoggedIn(true)

                val intent = Intent(this@GoogleRedirectActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        } else {
            Toast.makeText(this, "Login gagal. Token tidak ditemukan.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
