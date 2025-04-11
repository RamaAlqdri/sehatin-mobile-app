package com.example.sehatin.data.repository

import android.content.Context
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.OtpRequest
import com.example.sehatin.data.model.response.OtpResponse
import com.example.sehatin.data.model.response.VerifyOtpRequest
import com.example.sehatin.data.model.response.VerifyOtpResponse
import com.example.sehatin.retrofit.api.ApiConfig
import com.example.sehatin.retrofit.services.OtpService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class OtpRepository private constructor(
    context: Context
){
    private val otpService: OtpService = ApiConfig.getOtpService(context)

    fun verifyOtp(email: String, otp: String): Flow<ResultResponse<VerifyOtpResponse>> = flow {
        emit(ResultResponse.Loading)

        try {
            val response = otpService.verifyOtp(VerifyOtpRequest(email = email, otpCode = otp))
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultResponse.Success(it))
                } ?: emit(ResultResponse.Error("Failed to verify OTP, response body is empty"))
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
            val response = otpService.generateOtp(OtpRequest(email))
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
        private var INSTANCE: OtpRepository? = null

        @JvmStatic
        fun getInstance(
            context: Context
        ): OtpRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: OtpRepository(context).also {
                    INSTANCE = it
                }
            }
    }
}