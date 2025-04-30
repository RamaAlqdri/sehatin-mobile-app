package com.example.sehatin.retrofit.services

import com.example.sehatin.data.model.response.ConsultationResponse
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

interface ConsultationService {

    @POST("api/message")
    suspend fun sendMessage(@Body request: ConsultationResponse.SendMessageRequest): Response<ConsultationResponse.SendMessageResponse>

    @GET("api/message/user")
    suspend fun getUserMessages(): Response<ConsultationResponse.GetMessageResponse>
}