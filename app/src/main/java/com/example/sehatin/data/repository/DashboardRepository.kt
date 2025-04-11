package com.example.sehatin.data.repository

import android.content.Context
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.CaloriesADayRequest
import com.example.sehatin.data.model.response.CaloriesADayResponse
import com.example.sehatin.data.model.response.Detail
import com.example.sehatin.data.model.response.DietProgressResponse
import com.example.sehatin.data.model.response.GetUserResponse
import com.example.sehatin.data.model.response.ScheduleADayResponse
import com.example.sehatin.data.model.response.WaterADayResponse
import com.example.sehatin.data.store.DataStoreManager
import com.example.sehatin.retrofit.api.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DashboardRepository private constructor(
    context: Context
) {

    private val dataStoreManager = DataStoreManager(context)
    private val dashboardService = ApiConfig.getDashboardService(context)

    suspend fun getUserDetail(): Detail? {
        return dataStoreManager.getUserDetail()
    }

    fun getUserDietProgress(): Flow<ResultResponse<DietProgressResponse>> = flow {
        emit(ResultResponse.Loading)

        try {
            val response = dashboardService.getDietProgress()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultResponse.Success(it))
                } ?: emit(ResultResponse.Error("Empty response body"))
            } else {
                emit(ResultResponse.Error("Error: ${response.errorBody()?.string() ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.localizedMessage ?: "Network error"))
        }
    }.flowOn(Dispatchers.IO)

    fun getCaloriesADay(date: String): Flow<ResultResponse<CaloriesADayResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response = dashboardService.getCaloriesADay(CaloriesADayRequest(date))
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultResponse.Success(it))
                } ?: emit(ResultResponse.Error("Empty response body"))
            } else {
                emit(ResultResponse.Error("Error: ${response.errorBody()?.string() ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.localizedMessage ?: "Network error"))
        }
    }.flowOn(Dispatchers.IO)


    fun getWaterADay(date: String): Flow<ResultResponse<WaterADayResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response = dashboardService.getWaterADay(CaloriesADayRequest(date))
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultResponse.Success(it))
                } ?: emit(ResultResponse.Error("Empty response body"))
            } else {
                emit(ResultResponse.Error("Error: ${response.errorBody()?.string() ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.localizedMessage ?: "Network error"))
        }
    }.flowOn(Dispatchers.IO)

    fun getScheduleADay(date: String): Flow<ResultResponse<ScheduleADayResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response = dashboardService.getScheduleADay(CaloriesADayRequest(date))
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultResponse.Success(it))
                } ?: emit(ResultResponse.Error("Empty response body"))
            } else {
                emit(ResultResponse.Error("Error: ${response.errorBody()?.string() ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.localizedMessage ?: "Network error"))
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        @Volatile
        private var INSTANCE: DashboardRepository? = null

        @JvmStatic
        fun getInstance(
            context: Context
        ): DashboardRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DashboardRepository(context).also {
                    INSTANCE = it
                }
            }
    }
}