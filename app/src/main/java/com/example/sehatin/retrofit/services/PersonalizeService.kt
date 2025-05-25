package com.example.sehatin.retrofit.services

import com.example.sehatin.data.model.response.ActivityRequest
import com.example.sehatin.data.model.response.BirthdayRequest
import com.example.sehatin.data.model.response.GenderRequest
import com.example.sehatin.data.model.response.GetUserResponse
import com.example.sehatin.data.model.response.GoalRequest
import com.example.sehatin.data.model.response.HeightRequest
import com.example.sehatin.data.model.response.NameRequest
import com.example.sehatin.data.model.response.PersonalizeResponse
import com.example.sehatin.data.model.response.VerifyOtpRequest
import com.example.sehatin.data.model.response.VerifyOtpResponse
import com.example.sehatin.data.model.response.WeightRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PersonalizeService {
    @PUT("api/user/name")
    suspend fun inputName(@Body name: NameRequest): Response<PersonalizeResponse>

    @PUT("api/user/birthday")
    suspend fun inputAge(@Body dateOfBirth: BirthdayRequest): Response<PersonalizeResponse>

    @PUT("api/user/gender")
    suspend fun inputGender(@Body gender: GenderRequest): Response<PersonalizeResponse>

    @PUT("api/user/height")
    suspend fun inputHeight(@Body height: HeightRequest): Response<PersonalizeResponse>

    @POST("api/user/weight")
    suspend fun inputWeight(@Body weight: WeightRequest): Response<PersonalizeResponse>

    @PUT("api/user/activity")
    suspend fun inputActivityLevel(@Body activity: ActivityRequest): Response<PersonalizeResponse>

    @PUT("api/user/goal")
    suspend fun inputGoal(@Body goal: GoalRequest): Response<PersonalizeResponse>


    @GET("api/user/profile")
    suspend fun getProfile(): Response<GetUserResponse>
}