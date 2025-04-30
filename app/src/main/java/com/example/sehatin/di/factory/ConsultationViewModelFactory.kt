package com.example.sehatin.di.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sehatin.data.repository.ConsultationRepository
import com.example.sehatin.data.repository.DietRepository
import com.example.sehatin.di.Injection
import com.example.sehatin.viewmodel.ConsultationViewModel
import com.example.sehatin.viewmodel.DietViewModel

class ConsultationViewModelFactory private constructor(private val consultationRepository: ConsultationRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ConsultationViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                ConsultationViewModel(consultationRepository = consultationRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ConsultationViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            context: Context
        ): ConsultationViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ConsultationViewModelFactory(Injection.provideConsultationRepository(context)).also {
                    INSTANCE = it
                }
            }
    }
}