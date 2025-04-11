package com.example.sehatin.di

import android.content.Context
import com.example.sehatin.data.repository.DashboardRepository
import com.example.sehatin.data.repository.LoginRepository
import com.example.sehatin.data.repository.OnBoardingRepository
import com.example.sehatin.data.repository.OtpRepository
import com.example.sehatin.data.repository.PersonalizeRepository
import com.example.sehatin.data.repository.RegisterRepository

object Injection {
    fun provideOnBoardingRepository(context: Context): OnBoardingRepository {
        return OnBoardingRepository.getInstance(context)
    }

    fun provideRegisterRepository(context: Context): RegisterRepository {
        return RegisterRepository.getInstance(context)
    }

    fun provideOtpRepository(context: Context): OtpRepository {
        return OtpRepository.getInstance(context)
    }

    fun provideLoginRepository(context: Context): LoginRepository {
        return LoginRepository.getInstance(context)
    }

    fun providePersonalizeRepository(context: Context): PersonalizeRepository {
        return PersonalizeRepository.getInstance(context)
    }

    fun provideDashboardRepository(context: Context): DashboardRepository {
        return DashboardRepository.getInstance(context)
    }
}