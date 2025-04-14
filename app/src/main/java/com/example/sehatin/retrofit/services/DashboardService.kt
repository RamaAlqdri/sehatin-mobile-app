package com.example.sehatin.retrofit.services

import com.example.sehatin.data.model.response.CaloriesADayRequest
import com.example.sehatin.data.model.response.CaloriesADayResponse
import com.example.sehatin.data.model.response.CaloriesHistoryResponse
import com.example.sehatin.data.model.response.DietProgressResponse
import com.example.sehatin.data.model.response.GetUserResponse
import com.example.sehatin.data.model.response.LoginRequest
import com.example.sehatin.data.model.response.LoginResponse
import com.example.sehatin.data.model.response.ScheduleADayResponse
import com.example.sehatin.data.model.response.WaterADayResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DashboardService {
    @GET("api/schedule/progress")
    suspend fun getDietProgress(): Response<DietProgressResponse>

    @GET("api/schedule/calories")
    suspend fun getCaloriesADay(@Query("date") date: String): Response<CaloriesADayResponse>

    @GET("api/schedule/water")
    suspend fun getWaterADay(@Query("date") date: String): Response<WaterADayResponse>

    @GET("api/schedule/day")
    suspend fun getScheduleADay(@Query("date") date: String): Response<ScheduleADayResponse>

    @GET("api/schedule/calories/history")
    suspend fun getCaloriesHistory(@Query("date") date: String): Response<CaloriesHistoryResponse>

}



