package com.example.sehatin.di.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sehatin.data.repository.RegisterRepository
import com.example.sehatin.di.Injection
import com.example.sehatin.viewmodel.RegisterScreenViewModel

class RegisterViewModelFactory private constructor(private val registerRepository: RegisterRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterScreenViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                RegisterScreenViewModel(registerRepository = registerRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: RegisterViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            context: Context
        ): RegisterViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RegisterViewModelFactory(Injection.provideRegisterRepository(context)).also {
                    INSTANCE = it
                }
            }
    }
}