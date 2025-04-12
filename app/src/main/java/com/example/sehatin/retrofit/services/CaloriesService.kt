package com.example.sehatin.retrofit.services

import com.example.sehatin.data.model.response.CaloriesADayResponse
import com.example.sehatin.data.model.response.DietProgressResponse
import com.example.sehatin.data.model.response.ScheduleADayResponse
import com.example.sehatin.data.model.response.WaterADayResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CaloriesService {
    @GET("api/schedule/progress")
    suspend fun getDietProgress(): Response<DietProgressResponse>

    @GET("api/schedule/calories")
    suspend fun getCaloriesADay(@Query("date") date: String): Response<CaloriesADayResponse>

    @GET("api/schedule/water")
    suspend fun getWaterADay(@Query("date") date: String): Response<WaterADayResponse>

    @GET("api/schedule/day")
    suspend fun getScheduleADay(@Query("date") date: String): Response<ScheduleADayResponse>

}