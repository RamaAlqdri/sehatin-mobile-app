package com.example.sehatin.retrofit.services

import com.example.sehatin.data.model.response.OtpRequest
import com.example.sehatin.data.model.response.OtpResponse
import com.example.sehatin.data.model.response.VerifyOtpRequest
import com.example.sehatin.data.model.response.VerifyOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OtpService {
    @POST("api/user/auth/otp/verify")
    suspend fun verifyOtp(@Body request: VerifyOtpRequest): Response<VerifyOtpResponse>

    @POST("api/user/auth/otp/generate")
    suspend fun generateOtp(@Body request: OtpRequest): Response<OtpResponse>
}