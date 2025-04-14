package com.example.sehatin.data.repository

import android.content.Context
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.CaloriesADayRequest
import com.example.sehatin.data.model.response.CaloriesADayResponse
import com.example.sehatin.data.model.response.CaloriesHistoryResponse
import com.example.sehatin.data.model.response.CreateWaterHistoryResponse
import com.example.sehatin.data.model.response.Detail
import com.example.sehatin.data.model.response.DietProgressResponse
import com.example.sehatin.data.model.response.GetUserResponse
import com.example.sehatin.data.model.response.ScheduleADayResponse
import com.example.sehatin.data.model.response.WaterADayResponse
import com.example.sehatin.data.model.response.WaterHistoryRequest
import com.example.sehatin.data.model.response.WaterHistoryResponse
import com.example.sehatin.data.store.DataStoreManager
import com.example.sehatin.retrofit.api.ApiConfig
import com.example.sehatin.utils.formatDateToISO8601
import com.example.sehatin.utils.parseDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

class DashboardRepository private constructor(
    context: Context
) {

    private val dataStoreManager = DataStoreManager(context)
    private val dashboardService = ApiConfig.getDashboardService(context)

    suspend fun getUserDetail(): Detail? {
        return dataStoreManager.getUserDetail()
    }


    fun createWaterHistory(
        water: Double
    ): Flow<ResultResponse<CreateWaterHistoryResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response = dashboardService.postWaterHistory(WaterHistoryRequest(water))
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

    fun getCaloriesADay(date: Date): Flow<ResultResponse<CaloriesADayResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val formattedDate = formatDateToISO8601(date) // Format date to ISO 8601
            val response = dashboardService.getCaloriesADay(formattedDate) // Pass only the date
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

    fun getCaloriesHistory(date: Date): Flow<ResultResponse<CaloriesHistoryResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val formattedDate = formatDateToISO8601(date) // Format date to ISO 8601
            val response = dashboardService.getCaloriesHistory(formattedDate) // Pass only the date
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

    fun getWaterADay(date: Date): Flow<ResultResponse<WaterADayResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val formattedDate = formatDateToISO8601(date) // Format date to ISO 8601
            val response = dashboardService.getWaterADay(formattedDate)
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

    fun getWaterHistory(date: Date): Flow<ResultResponse<WaterHistoryResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val formattedDate = formatDateToISO8601(date) // Format date to ISO 8601
            val response = dashboardService.getWaterHistory(formattedDate) // Pass only the date
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

    fun getScheduleADay(date: Date): Flow<ResultResponse<ScheduleADayResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val formattedDate = formatDateToISO8601(date) // Format date to ISO 8601
            val response = dashboardService.getScheduleADay(formattedDate)
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