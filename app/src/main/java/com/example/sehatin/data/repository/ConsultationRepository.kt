package com.example.sehatin.data.repository

import android.content.Context
import android.util.Log
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.ConsultationResponse
import com.example.sehatin.data.model.response.CreateWaterHistoryResponse
import com.example.sehatin.data.model.response.DietResponse
import com.example.sehatin.data.model.response.DietResponse.FoodFilterResponse
import com.example.sehatin.data.model.response.DietResponse.UpdateScheduleResponse
import com.example.sehatin.data.model.response.FoodDetailResponse
import com.example.sehatin.data.model.response.ScheduleADayResponse
import com.example.sehatin.data.model.response.ScheduleClosestResponse
import com.example.sehatin.data.model.response.WaterADayResponse
import com.example.sehatin.data.model.response.WaterHistoryRequest
import com.example.sehatin.data.store.DataStoreManager
import com.example.sehatin.retrofit.api.ApiConfig
import com.example.sehatin.utils.formatDateToISO8601
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date
import kotlin.concurrent.Volatile

class ConsultationRepository private constructor(
    context: Context
) {
    private val dataStoreManager = DataStoreManager(context)
    private val consultationService = ApiConfig.getConsultationService(context)


    fun sendMessage(
        content: String
    ): Flow<ResultResponse<ConsultationResponse.SendMessageResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response =
                consultationService.sendMessage(request = ConsultationResponse.SendMessageRequest(content = content, sender = "user"))
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultResponse.Success(it))
                } ?: emit(ResultResponse.Error("Empty response body"))
            } else {
                emit(
                    ResultResponse.Error(
                        "Error: ${
                            response.errorBody()?.string() ?: "Unknown error"
                        }"
                    )
                )
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.localizedMessage ?: "Network error"))
        }
    }.flowOn(Dispatchers.IO)

    fun getMessages(): Flow<ResultResponse<ConsultationResponse.GetMessageResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response =
                consultationService.getUserMessages()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultResponse.Success(it))
                } ?: emit(ResultResponse.Error("Empty response body"))
            } else {
                emit(
                    ResultResponse.Error(
                        "Error: ${
                            response.errorBody()?.string() ?: "Unknown error"
                        }"
                    )
                )
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.localizedMessage ?: "Network error"))
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        @Volatile
        private var INSTANCE: ConsultationRepository? = null

        @JvmStatic
        fun getInstance(
            context: Context
        ): ConsultationRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ConsultationRepository(context).also {
                    INSTANCE = it
                }
            }
    }
}