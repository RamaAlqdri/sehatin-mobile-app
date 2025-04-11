package com.example.sehatin.di.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sehatin.data.repository.DashboardRepository
import com.example.sehatin.data.repository.LoginRepository
import com.example.sehatin.di.Injection
import com.example.sehatin.view.screen.authentication.login.LoginScreenViewModel
import com.example.sehatin.view.screen.dashboard.home.DashboardViewModel

class DashboardViewModelFactory private constructor(private val dashboardRepository: DashboardRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                DashboardViewModel(dashboardRepository = dashboardRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DashboardViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            context: Context
        ): DashboardViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DashboardViewModelFactory(Injection.provideDashboardRepository(context)).also {
                    INSTANCE = it
                }
            }
    }
}