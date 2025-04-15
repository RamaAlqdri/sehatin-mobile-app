package com.example.sehatin.di.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sehatin.data.repository.OtpRepository
import com.example.sehatin.di.Injection
import com.example.sehatin.viewmodel.OtpScreenViewModel

class OtpViewModelFactory private constructor(private val otpRepository: OtpRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(OtpScreenViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                OtpScreenViewModel(otpRepository = otpRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: OtpViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            context: Context
        ): OtpViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: OtpViewModelFactory(Injection.provideOtpRepository(context)).also {
                    INSTANCE = it
                }
            }
    }
}