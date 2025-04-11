package com.example.sehatin.di.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sehatin.data.repository.PersonalizeRepository
import com.example.sehatin.data.repository.RegisterRepository
import com.example.sehatin.di.Injection
import com.example.sehatin.view.screen.authentication.register.RegisterScreenViewModel
import com.example.sehatin.view.screen.authentication.register.personalize.PersonalizeViewModel

class PersonalizeViewModelFactory private constructor(private val personalizeRepository: PersonalizeRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PersonalizeViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                PersonalizeViewModel(personalizeRepository = personalizeRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: PersonalizeViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            context: Context
        ): PersonalizeViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: PersonalizeViewModelFactory(Injection.providePersonalizeRepository(context)).also {
                    INSTANCE = it
                }
            }
    }
}