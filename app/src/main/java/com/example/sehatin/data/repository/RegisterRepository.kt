package com.example.sehatin.data.repository

import android.content.Context
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.OtpRequest
import com.example.sehatin.data.model.response.OtpResponse
import com.example.sehatin.data.model.response.RegisterRequest
import com.example.sehatin.data.model.response.RegisterResponse
import com.example.sehatin.retrofit.api.ApiConfig
import com.example.sehatin.retrofit.services.RegisterService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RegisterRepository private constructor(
    context: Context
) {

    private val registerService: RegisterService = ApiConfig.getRegisterService(context)

    fun registerUser(email: String, password: String): Flow<ResultResponse<RegisterResponse>> = flow {
        emit(ResultResponse.Loading) // Emit loading sebelum request

        try {
            val response = registerService.register(RegisterRequest(email, password))
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultResponse.Success(it))
                } ?: emit(ResultResponse.Error("Empty response body"))
            } else {
                emit(ResultResponse.Error("Error: ${response.errorBody()?.string() ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.localizedMessage ?: "Network error"))
        }
    }.flowOn(Dispatchers.IO)

    fun generateOtp(email: String): Flow<ResultResponse<OtpResponse>> = flow {
        emit(ResultResponse.Loading)

        try {
            val response = registerService.generateOtp(OtpRequest(email))
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultResponse.Success(it))
                } ?: emit(ResultResponse.Error("Failed to generate OTP, response body empty"))
            } else {
                emit(ResultResponse.Error("Error: ${response.errorBody()?.string() ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.localizedMessage ?: "Network error"))
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        @Volatile
        private var INSTANCE: RegisterRepository? = null

        @JvmStatic
        fun getInstance(
            context: Context
        ): RegisterRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: RegisterRepository(context).also {
                    INSTANCE = it
                }
            }
    }
}