package com.example.sehatin.data.repository

import android.R
import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.CaloriesADayRequest
import com.example.sehatin.data.model.response.CaloriesADayResponse
import com.example.sehatin.data.model.response.CaloriesHistoryResponse
import com.example.sehatin.data.model.response.CreateWaterHistoryResponse
import com.example.sehatin.data.model.response.Detail
import com.example.sehatin.data.model.response.DietProgressResponse
import com.example.sehatin.data.model.response.DietResponse.FetchSummaryResponse
import com.example.sehatin.data.model.response.FoodDetailResponse
import com.example.sehatin.data.model.response.GetUserResponse
import com.example.sehatin.data.model.response.ScheduleADayResponse
import com.example.sehatin.data.model.response.ScheduleDataItem
import com.example.sehatin.data.model.response.WaterADayResponse
import com.example.sehatin.data.model.response.WaterHistoryRequest
import com.example.sehatin.data.model.response.WaterHistoryResponse
import com.example.sehatin.data.model.response.WeightResponse
import com.example.sehatin.data.store.DataStoreManager
import com.example.sehatin.data.worker.DietReminderWorker
import com.example.sehatin.retrofit.api.ApiConfig
import com.example.sehatin.utils.formatDateToISO8601
import com.example.sehatin.utils.parseDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class DashboardRepository private constructor(
    private val context: Context
) {

    private val dataStoreManager = DataStoreManager(context)
    private val dashboardService = ApiConfig.getDashboardService(context)

    suspend fun getUserDetail(): Detail? {
        return dataStoreManager.getUserDetail()
    }

    suspend fun getSavedScheduleList(): List<ScheduleDataItem> {
        return dataStoreManager.getTodayScheduleList()
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

    fun deleteLatestWaterHistory(
    ): Flow<ResultResponse<CreateWaterHistoryResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response = dashboardService.deleteLatestWaterHistory()
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

    fun deleteWaterByIdHistory(
        id: String
    ): Flow<ResultResponse<CreateWaterHistoryResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response = dashboardService.deleteWaterByIdHistory(id)
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


    fun getUserDietProgress(): Flow<ResultResponse<DietProgressResponse>> = flow {
        emit(ResultResponse.Loading)

        try {
            val response = dashboardService.getDietProgress()
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

    fun getUserWeightHistory(): Flow<ResultResponse<WeightResponse>> = flow {
        emit(ResultResponse.Loading)

        try {
            val response = dashboardService.getWeightHistory()
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


    fun getSummary(
        mode: String,
        date: String,
        start_date: String,
        end_date: String
    ): Flow<ResultResponse<FetchSummaryResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response = dashboardService.getSummary(mode,date, start_date, end_date) // Pass only the date
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

    fun getFoodDetail(id: String): Flow<ResultResponse<FoodDetailResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response = dashboardService.getFoodDetail(id)
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



    suspend fun saveScheduleToDataStore(scheduleList: List<ScheduleDataItem>) {
        dataStoreManager.saveTodayScheduleList(scheduleList)
    }
    fun getScheduleADay(date: Date): Flow<ResultResponse<ScheduleADayResponse>> = flow {
        emit(ResultResponse.Loading)


        try {

            val formattedDate = formatDateToISO8601(date) // Format date to ISO 8601
            val response = dashboardService.getScheduleADay(formattedDate)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultResponse.Success(it))
                    // Simpan jadwal ke DataStore
                    val scheduleList = it.data
                    saveScheduleToDataStore(scheduleList)

                    // Jadwalkan notifikasi
                    scheduleList.forEach { item ->
                        scheduleMealReminder(context, item)
                    }
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


    // Fungsi untuk menjadwalkan notifikasi
    private fun scheduleMealReminder(context: Context, item: ScheduleDataItem) {
        val timeInMillis = parseIso8601ToMillis(item.scheduledAt) ?: return

        val delay = timeInMillis - System.currentTimeMillis()
        Log.e("TAG", "scheduleMealReminder: ${item.scheduledAt} - $delay")
        if (delay <= 0) return // Lewat waktu, skip

        // Siapkan data untuk dikirim ke Worker
        val data = workDataOf(
            "meal_name" to item.food.name,
            "meal_serving_amount" to item.food.serving_amount.toInt().toString(),
            "meal_serving_unit" to item.food.serving_unit,
        )

        // Siapkan request untuk WorkManager
        val request = OneTimeWorkRequestBuilder<DietReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueue(request)
    }

    // Fungsi untuk konversi string ISO 8601 ke milisecond
    private fun parseIso8601ToMillis(isoTime: String): Long? {
        return try {
            val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            val zonedDateTime = OffsetDateTime.parse(isoTime, formatter)
            zonedDateTime.toInstant().toEpochMilli()
        } catch (e: Exception) {
            null
        }
    }
//    private fun parseIso8601ToMillis(isoDate: String): Long? {
//        return try {
//            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
//            formatter.timeZone = TimeZone.getTimeZone("Asia/Makassar") // UTC+8
//            formatter.parse(isoDate)?.time
//        } catch (e: Exception) {
//            null
//        }
//    }

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