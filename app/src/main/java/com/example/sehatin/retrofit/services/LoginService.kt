package com.example.sehatin.retrofit.services

import com.example.sehatin.data.model.response.ChangePasswordAuthRequest
import com.example.sehatin.data.model.response.ResetPasswordRequest
import com.example.sehatin.data.model.response.ChangePasswordResponse
import com.example.sehatin.data.model.response.GetUserResponse
import com.example.sehatin.data.model.response.LoginRequest
import com.example.sehatin.data.model.response.LoginResponse
import com.example.sehatin.data.model.response.VerifyOtpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginService {
    @POST("api/user/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("api/user/profile")
    suspend fun getUser(): Response<GetUserResponse>

    @POST("api/user/auth/otp/password/verify")
    suspend fun verifyForgotPass(@Body request: VerifyOtpRequest): Response<LoginResponse>

    @POST("api/user/password/reset")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<ChangePasswordResponse>

    @POST("api/user/password/change")
    suspend fun changePassword(@Body request: ChangePasswordAuthRequest): Response<ChangePasswordResponse>


}
