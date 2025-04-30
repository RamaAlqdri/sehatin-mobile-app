package com.example.sehatin.retrofit.services

import com.example.sehatin.data.model.response.CreateWaterHistoryResponse
import com.example.sehatin.data.model.response.DietResponse
import com.example.sehatin.data.model.response.DietResponse.FoodFilterResponse
import com.example.sehatin.data.model.response.DietResponse.FoodScheduleUpdate
import com.example.sehatin.data.model.response.DietResponse.UpdateScheduleResponse
import com.example.sehatin.data.model.response.FoodDetailResponse
import com.example.sehatin.data.model.response.ScheduleADayResponse
import com.example.sehatin.data.model.response.ScheduleClosestResponse
import com.example.sehatin.data.model.response.WaterADayResponse
import com.example.sehatin.data.model.response.WaterHistoryRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface DietService {

    @GET("api/schedule/water")
    suspend fun getWaterADay(@Query("date") date: String): Response<WaterADayResponse>

    @POST("api/schedule/water")
    suspend fun postWaterHistory(@Body request: WaterHistoryRequest): Response<CreateWaterHistoryResponse>

    @GET("api/food/detail")
    suspend fun getFoodDetail(@Query("foodId") id: String): Response<FoodDetailResponse>

    @GET("api/schedule/day")
    suspend fun getScheduleADay(@Query("date") date: String): Response<ScheduleADayResponse>

    @GET("api/schedule/closest")
    suspend fun getSchedultClosest(@Query("date") date: String): Response<ScheduleClosestResponse>

    @GET("api/food/filter")
    suspend fun getFoodFilter(
        @Query("name") name: String, @Query("limit") limit: Int
    ): Response<FoodFilterResponse>

    @PUT("api/schedule/complete")
    suspend fun setCompletedSchedule(
        @Query("scheduleId") scheduleId: String
    ): Response<UpdateScheduleResponse>

    @PUT("api/schedule/food")
    suspend fun updateFoodSchedule(
        @Query("scheduleId") scheduleId: String, @Body request: FoodScheduleUpdate
    ): Response<UpdateScheduleResponse>

    @GET("api/food/recommendation")
    suspend fun getFoodRecommendation(
    ): Response<FoodFilterResponse>

}