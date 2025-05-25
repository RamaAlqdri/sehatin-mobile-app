package com.example.sehatin.data.repository

import android.content.Context
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.ActivityRequest
import com.example.sehatin.data.model.response.BirthdayRequest
import com.example.sehatin.data.model.response.Detail
import com.example.sehatin.data.model.response.GenderRequest
import com.example.sehatin.data.model.response.GetUserResponse
import com.example.sehatin.data.model.response.GoalRequest
import com.example.sehatin.data.model.response.HeightRequest
import com.example.sehatin.data.model.response.NameRequest
import com.example.sehatin.data.model.response.OtpRequest
import com.example.sehatin.data.model.response.OtpResponse
import com.example.sehatin.data.model.response.PersonalizeResponse
import com.example.sehatin.data.model.response.WeightRequest
import com.example.sehatin.data.store.DataStoreManager
import com.example.sehatin.retrofit.api.ApiConfig
import com.example.sehatin.retrofit.services.PersonalizeService
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PersonalizeRepository private constructor(
    context: Context
) {


    private val dataStoreManager = DataStoreManager(context)
    private val personalizeService: PersonalizeService = ApiConfig.getPersonalizeService(context)


    suspend fun getUserDetail(): Detail? {
        return dataStoreManager.getUserDetail()
    }

    fun personalizeName(name: String): Flow<ResultResponse<PersonalizeResponse>> = flow {
        emit(ResultResponse.Loading)

        try {
            val response = personalizeService.inputName(NameRequest(name))
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultResponse.Success(it))
                } ?: emit(ResultResponse.Error("Failed to generate OTP, response body empty"))
            } else {
                emit(ResultResponse.Error("Error: ${response.errorBody()?.string() ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.localizedMessage ?: "Network error"))
        }
    }.flowOn(Dispatchers.IO)

    fun personalizeAge(dateOfBirth: String): Flow<ResultResponse<PersonalizeResponse>> = flow {
        emit(ResultResponse.Loading)

        try {
            val response = personalizeService.inputAge(BirthdayRequest(dateOfBirth))
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultResponse.Success(it))
                } ?: emit(ResultResponse.Error("Failed to generate OTP, response body empty"))
            } else {
                emit(ResultResponse.Error("Error: ${response.errorBody()?.string() ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.localizedMessage ?: "Network error"))
        }
    }.flowOn(Dispatchers.IO)

    fun personalizeGender(gender: String): Flow<ResultResponse<PersonalizeResponse>> = flow {
        emit(ResultResponse.Loading)

        try {
            val response = personalizeService.inputGender(GenderRequest(gender))
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultResponse.Success(it))
                } ?: emit(ResultResponse.Error("Failed to generate OTP, response body empty"))
            } else {
                emit(ResultResponse.Error("Error: ${response.errorBody()?.string() ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.localizedMessage ?: "Network error"))
        }
    }.flowOn(Dispatchers.IO)

    fun personalizeHeight(height: Float): Flow<ResultResponse<PersonalizeResponse>> = flow {
        emit(ResultResponse.Loading)

        try {
            val response = personalizeService.inputHeight(HeightRequest(height))
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultResponse.Success(it))
                } ?: emit(ResultResponse.Error("Failed to generate OTP, response body empty"))
            } else {
                emit(ResultResponse.Error("Error: ${response.errorBody()?.string() ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.localizedMessage ?: "Network error"))
        }
    }.flowOn(Dispatchers.IO)

    fun personalizeWeight(weight: Float): Flow<ResultResponse<PersonalizeResponse>> = flow {
        emit(ResultResponse.Loading)

        try {
            val response = personalizeService.inputWeight(WeightRequest(weight))
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultResponse.Success(it))
                } ?: emit(ResultResponse.Error("Failed to generate OTP, response body empty"))
            } else {
                emit(ResultResponse.Error("Error: ${response.errorBody()?.string() ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.localizedMessage ?: "Network error"))
        }
    }.flowOn(Dispatchers.IO)

    fun personalizeActivityLevel(activityLevel: String): Flow<ResultResponse<PersonalizeResponse>> = flow {
        emit(ResultResponse.Loading)

        try {
            val response = personalizeService.inputActivityLevel(ActivityRequest(activityLevel))
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultResponse.Success(it))
                } ?: emit(ResultResponse.Error("Failed to generate OTP, response body empty"))
            } else {
                emit(ResultResponse.Error("Error: ${response.errorBody()?.string() ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.localizedMessage ?: "Network error"))
        }
    }.flowOn(Dispatchers.IO)

    fun personalizeGoal(goal: String): Flow<ResultResponse<PersonalizeResponse>> = flow {
        emit(ResultResponse.Loading)

        try {
            val response = personalizeService.inputGoal(GoalRequest(goal))
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultResponse.Success(it))
                } ?: emit(ResultResponse.Error("Failed to generate OTP, response body empty"))
            } else {
                emit(ResultResponse.Error("Error: ${response.errorBody()?.string() ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.localizedMessage ?: "Network error"))
        }
    }.flowOn(Dispatchers.IO)

    fun getProfile(): Flow<ResultResponse<GetUserResponse>> = flow {
        emit(ResultResponse.Loading)

        try {
            val response = personalizeService.getProfile()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ResultResponse.Success(it))
                } ?: emit(ResultResponse.Error("Failed to generate OTP, response body empty"))
            } else {
                emit(ResultResponse.Error("Error: ${response.errorBody()?.string() ?: "Unknown error"}"))
            }
        } catch (e: Exception) {
            emit(ResultResponse.Error(e.localizedMessage ?: "Network error"))
        }
    }.flowOn(Dispatchers.IO)


    companion object {
        @Volatile
        private var INSTANCE: PersonalizeRepository? = null

        @JvmStatic
        fun getInstance(
            context: Context
        ): PersonalizeRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: PersonalizeRepository(context).also {
                    INSTANCE = it
                }
            }
    }
}