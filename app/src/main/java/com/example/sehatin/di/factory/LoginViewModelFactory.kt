package com.example.sehatin.di.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sehatin.data.repository.LoginRepository
import com.example.sehatin.data.repository.OnBoardingRepository
import com.example.sehatin.di.Injection
import com.example.sehatin.viewmodel.LoginScreenViewModel
import com.example.sehatin.viewmodel.OnBoardingViewModel

class LoginViewModelFactory private constructor(private val loginRepository: LoginRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginScreenViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                LoginScreenViewModel(loginRepository = loginRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: LoginViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            context: Context
        ): LoginViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LoginViewModelFactory(Injection.provideLoginRepository(context)).also {
                    INSTANCE = it
                }
            }
    }
}