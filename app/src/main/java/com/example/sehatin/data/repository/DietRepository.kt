package com.example.sehatin.data.repository

import android.content.Context
import android.util.Log
import com.example.sehatin.common.ResultResponse
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
import com.example.sehatin.utils.formatDatesToISO8601
import com.example.sehatin.utils.getCurrentTimeInMakassarISO8601
import com.example.sehatin.utils.getUtcTimeAdjustedToPlus8
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.concurrent.Volatile

class DietRepository private constructor(
    context: Context
) {
    private val dataStoreManager = DataStoreManager(context)
    private val dietService = ApiConfig.getDietService(context)


    fun addFoodHistory(
        food_id: String,
        meal_type: String,
        serving_amount: Double,
        date: String,
    ): Flow<ResultResponse<DietResponse.CreateFoodHistoryResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response = dietService.addFoodHistory(
                DietResponse.CreateFoodHistoryRequest(
                    food_id,
                    meal_type,
                    serving_amount,
                    date
                )
            )
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

    fun deleteFoodHistory(
        food_id: String,
        meal_type: String,
        date: String
    ): Flow<ResultResponse<DietResponse.CreateFoodHistoryResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response = dietService.deleteFoodHistory(
                food_id, meal_type, date
            )
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

    fun getOneFoodHistory(
        food_id: String,
        meal_type: String,
        date: String
    ): Flow<ResultResponse<DietResponse.FetchFoodHistoryResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response = dietService.getFoodHistory(food_id, meal_type, date)
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

    fun getManyFoodHistory(
        meal_type: String,
        date: String
    ): Flow<ResultResponse<DietResponse.FetchManyFoodHistoryResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response = dietService.getManyFoodHistory(meal_type, date)
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


    fun setCompleteSchedule(
        scheduleId: String
    ): Flow<ResultResponse<UpdateScheduleResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response = dietService.setCompletedSchedule(scheduleId)
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

    fun updateFoodSchedule(
        scheduleId: String,
        food_id: String
    ): Flow<ResultResponse<UpdateScheduleResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response =
                dietService.updateFoodSchedule(scheduleId, DietResponse.FoodScheduleUpdate(food_id))
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

    fun createWaterHistory(
        water: Double
    ): Flow<ResultResponse<CreateWaterHistoryResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response = dietService.postWaterHistory(WaterHistoryRequest(water))
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
            val response = dietService.deleteLatestWaterHistory()
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
            val response = dietService.deleteWaterByIdHistory(id)
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
            val response = dietService.getWaterADay(formattedDate)
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

    fun getFoodRecommendation(): Flow<ResultResponse<FoodFilterResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response = dietService.getFoodRecommendation()
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
            val response = dietService.getFoodDetail(id)
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


    fun getFoodFilter(
        name: String,
        limit: Int
    ): Flow<ResultResponse<FoodFilterResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val response = dietService.getFoodFilter(name, limit)
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

    fun getScheduleADay(date: Date): Flow<ResultResponse<ScheduleADayResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
//            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val formattedDate = formatDateToISO8601(date) // Format date to ISO 8601
            val response = dietService.getScheduleADay(formattedDate)
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

    fun getScheduleClosest(date: Date): Flow<ResultResponse<ScheduleClosestResponse>> = flow {
        emit(ResultResponse.Loading)
        try {
            val formattedDate = formatDatesToISO8601(date) // Format date to ISO 8601
            val response = dietService.getSchedultClosest(formattedDate)
//            Log.e("DietRepository", "getScheduleClosest in Repository: $formattedDate")
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
        private var INSTANCE: DietRepository? = null

        @JvmStatic
        fun getInstance(
            context: Context
        ): DietRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DietRepository(context).also {
                    INSTANCE = it
                }
            }
    }
}