package com.example.sehatin.retrofit.api

import android.content.Context
import com.example.sehatin.BuildConfig
import com.example.sehatin.retrofit.AuthInterceptor
import com.example.sehatin.retrofit.services.DashboardService
import com.example.sehatin.retrofit.services.LoginService
import com.example.sehatin.retrofit.services.OtpService
import com.example.sehatin.retrofit.services.PersonalizeService
import com.example.sehatin.retrofit.services.RegisterService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        } else {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    private fun provideOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(provideLoggingInterceptor())
            .addInterceptor(AuthInterceptor(context))
            .build()
    }

    private fun provideRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(provideOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRegisterService(context: Context): RegisterService {
        return provideRetrofit(context).create(RegisterService::class.java)
    }

    fun getOtpService(context: Context): OtpService {
        return provideRetrofit(context).create(OtpService::class.java)
    }

    fun getLoginService(context: Context): LoginService {
        return provideRetrofit(context).create(LoginService::class.java)
    }

    fun getPersonalizeService(context: Context): PersonalizeService {
        return provideRetrofit(context).create(PersonalizeService::class.java)
    }

    fun getDashboardService(context: Context): DashboardService {
        return provideRetrofit(context).create(DashboardService::class.java)
    }


}