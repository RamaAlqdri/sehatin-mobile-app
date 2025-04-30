package com.example.sehatin.di.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sehatin.data.repository.DietRepository
import com.example.sehatin.di.Injection
import com.example.sehatin.viewmodel.DietViewModel

class DietViewModelFactory private constructor(private val dietRepository: DietRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DietViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                DietViewModel(dietRepository = dietRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DietViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            context: Context
        ): DietViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DietViewModelFactory(Injection.provideDietRepository(context)).also {
                    INSTANCE = it
                }
            }
    }
}