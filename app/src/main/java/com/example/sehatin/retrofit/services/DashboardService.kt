package com.example.sehatin.retrofit.services

import com.example.sehatin.data.model.response.CaloriesADayRequest
import com.example.sehatin.data.model.response.CaloriesADayResponse
import com.example.sehatin.data.model.response.CaloriesHistoryResponse
import com.example.sehatin.data.model.response.CreateWaterHistoryResponse
import com.example.sehatin.data.model.response.DietProgressResponse
import com.example.sehatin.data.model.response.DietResponse
import com.example.sehatin.data.model.response.FoodDetailResponse
import com.example.sehatin.data.model.response.GetUserResponse
import com.example.sehatin.data.model.response.LoginRequest
import com.example.sehatin.data.model.response.LoginResponse
import com.example.sehatin.data.model.response.ScheduleADayResponse
import com.example.sehatin.data.model.response.WaterADayResponse
import com.example.sehatin.data.model.response.WaterHistoryRequest
import com.example.sehatin.data.model.response.WaterHistoryResponse
import com.example.sehatin.data.model.response.WeightResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
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

    @GET("api/schedule/water/history")
    suspend fun getWaterHistory(@Query("date") date: String): Response<WaterHistoryResponse>

    @POST("api/schedule/water")
    suspend fun postWaterHistory(@Body request: WaterHistoryRequest) : Response<CreateWaterHistoryResponse>

    @DELETE("api/schedule/water")
    suspend fun deleteLatestWaterHistory(): Response<CreateWaterHistoryResponse>

    @DELETE("api/schedule/water/id")
    suspend fun deleteWaterByIdHistory(@Query("waterId") id:String): Response<CreateWaterHistoryResponse>

    @GET("api/food/detail")
    suspend fun getFoodDetail(@Query("foodId") id: String): Response<FoodDetailResponse>

    @GET("api/user/weight/history")
    suspend fun getWeightHistory(): Response<WeightResponse>

    @GET("api/food/history/summary")
    suspend fun getSummary(
        @Query("mode") mode: String,
        @Query("date") date: String,
        @Query("start_date") start_date: String? = null,
        @Query("end_date") end_date: String? = null,

    ): Response<DietResponse.FetchSummaryResponse>


}



