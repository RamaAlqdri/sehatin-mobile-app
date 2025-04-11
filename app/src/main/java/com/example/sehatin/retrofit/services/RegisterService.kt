package com.example.sehatin.retrofit.services

import com.example.sehatin.data.model.response.OtpRequest
import com.example.sehatin.data.model.response.OtpResponse
import com.example.sehatin.data.model.response.RegisterRequest
import com.example.sehatin.data.model.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("api/user/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("api/user/auth/otp/generate")
    suspend fun generateOtp(@Body request: OtpRequest): Response<OtpResponse>
}