package com.example.sehatin.view.screen.dashboard.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.CaloriesADayRequest
import com.example.sehatin.data.model.response.CaloriesADayResponse
import com.example.sehatin.data.model.response.Detail
import com.example.sehatin.data.model.response.DietProgressResponse
import com.example.sehatin.data.model.response.RegisterResponse
import com.example.sehatin.data.model.response.ScheduleADayResponse
import com.example.sehatin.data.model.response.VerifyOtpRequest
import com.example.sehatin.data.model.response.WaterADayResponse
import com.example.sehatin.data.repository.DashboardRepository
import com.example.sehatin.utils.parseDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.text.compareTo
import kotlin.text.format

class DashboardViewModel(
    private val dashboardRepository: DashboardRepository
) : ViewModel() {

//    SEPARATOR
    private val _dietProgressState =
        MutableStateFlow<ResultResponse<DietProgressResponse>>(ResultResponse.None)
    val dietProgressState: StateFlow<ResultResponse<DietProgressResponse>> = _dietProgressState

    private val _dietProgress = MutableStateFlow<DietProgressResponse?>(null)
    val dietProgress = _dietProgress.asStateFlow()

//    SEPARATOR
    private val _caloriesADayState =
        MutableStateFlow<ResultResponse<CaloriesADayResponse>>(ResultResponse.None)
    val caloriesADayState: StateFlow<ResultResponse<CaloriesADayResponse>> = _caloriesADayState

    private val _caloriesADay = MutableStateFlow<CaloriesADayResponse?>(null)
    val caloriesADay = _caloriesADay.asStateFlow()

//    SEPARATOR
    private val _waterADayState =
        MutableStateFlow<ResultResponse<WaterADayResponse>>(ResultResponse.None)
    val waterADayState: StateFlow<ResultResponse<WaterADayResponse>> = _waterADayState

    private val _waterADay = MutableStateFlow<WaterADayResponse?>(null)
    val waterADay = _waterADay.asStateFlow()

// SEPARATOR
    private val _scheduleADayState =
        MutableStateFlow<ResultResponse<ScheduleADayResponse>>(ResultResponse.None)
    val scheduleADayState: StateFlow<ResultResponse<ScheduleADayResponse>> = _scheduleADayState

    private val _scheduleADay = MutableStateFlow<ScheduleADayResponse?>(null)
    val scheduleADay = _scheduleADay.asStateFlow()


    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private var lastDietFetchTime = 0L
    private val dietCacheValidityPeriod = 5 * 60 * 1000L

    private var lastCaloriesFetchTime = 0L
    private val caloriesCacheValidityPeriod = 5 * 60 * 1000L

    private var lastWaterFetchTime = 0L
    private val WaterCacheValidityPeriod = 5 * 60 * 1000L

    private var lastScheduleFetchTime = 0L
    private val ScheduleCacheValidityPeriod = 5 * 60 * 1000L

    init {
        getUserDietProgress(forceRefresh = false)
         getCaloriesADay(forceRefresh = false)
        getWaterADay(forceRefresh = false)
        getScheduleADay(forceRefresh = false)
    }

    private fun getUserDietProgress(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val currentTime = System.currentTimeMillis()
                val shouldRefresh = _dietProgressState.value is ResultResponse.None ||
                        forceRefresh ||
                        (currentTime - lastDietFetchTime > dietCacheValidityPeriod)

                if (shouldRefresh) {
                    _dietProgressState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getUserDietProgress()
                        .collect { result ->
                            _dietProgressState.value = result
                            if (result !is ResultResponse.Loading) {
                                _isRefreshing.value = false
                                if (result is ResultResponse.Success) {
                                    _dietProgress.value = result.data
                                    lastDietFetchTime = System.currentTimeMillis()
                                }
                            }
                        }
                } else {
                    _isRefreshing.value = false
                }
            } catch (e: Exception) {
                _dietProgressState.value =
                    ResultResponse.Error(e.localizedMessage ?: "Network error")
                _isRefreshing.value = false
            }
        }
    }

    private fun getWaterADay(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val currentDate = LocalDateTime.now()
                val formatter = DateTimeFormatter.ISO_INSTANT
//                val formattedDate = currentDate.format(formatter)
                val date = java.util.Date.from(currentDate.atZone(java.time.ZoneId.of("UTC")).toInstant())


                val currentTime = System.currentTimeMillis()
                val shouldRefresh = _waterADayState.value is ResultResponse.None ||
                        forceRefresh ||
                        (currentTime - lastWaterFetchTime > WaterCacheValidityPeriod)

                if (shouldRefresh) {
                    _waterADayState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getWaterADay(date)
                        .collect { result ->
                            _waterADayState.value = result
                            if (result !is ResultResponse.Loading) {
                                _isRefreshing.value = false
                                if (result is ResultResponse.Success) {
                                    _waterADay.value = result.data
                                    lastWaterFetchTime = System.currentTimeMillis()
                                }
                            }
                        }
                } else {
                    _isRefreshing.value = false
                }
            } catch (e: Exception) {
                _waterADayState.value =
                    ResultResponse.Error(e.localizedMessage ?: "Network error")
                _isRefreshing.value = false
            }
        }
    }

    private fun getCaloriesADay(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val currentDate = LocalDateTime.now()
                val formatter = DateTimeFormatter.ISO_INSTANT
                val formattedDate = currentDate.atZone(java.time.ZoneId.of("UTC")).format(formatter)
                val date = java.util.Date.from(currentDate.atZone(java.time.ZoneId.of("UTC")).toInstant())


//                Log.e("RESULT", "getCaloriesADay: $formattedDate")

                val currentTime = System.currentTimeMillis()
                val shouldRefresh = _caloriesADayState.value is ResultResponse.None ||
                        forceRefresh ||
                        (currentTime - lastCaloriesFetchTime > caloriesCacheValidityPeriod)

                if (shouldRefresh) {
                    _caloriesADayState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getCaloriesADay(date)
                        .collect { result ->
                            _caloriesADayState.value = result
                            if (result !is ResultResponse.Loading) {
                                _isRefreshing.value = false
                                if (result is ResultResponse.Success) {
                                    _caloriesADay.value = result.data
                                    lastCaloriesFetchTime = System.currentTimeMillis()
                                }
                            }
                        }
                } else {
                    _isRefreshing.value = false
                }
            } catch (e: Exception) {
                _caloriesADayState.value =
                    ResultResponse.Error(e.localizedMessage ?: "Network error")
                _isRefreshing.value = false
            }
        }
    }
    private fun getScheduleADay(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val currentDate = LocalDateTime.now()
                val formatter = DateTimeFormatter.ISO_INSTANT
                val formattedDate = currentDate.atZone(java.time.ZoneId.of("UTC")).format(formatter)
                val date = java.util.Date.from(currentDate.atZone(java.time.ZoneId.of("UTC")).toInstant())


                val currentTime = System.currentTimeMillis()
                val shouldRefresh = _scheduleADayState.value is ResultResponse.None ||
                        forceRefresh ||
                        (currentTime - lastScheduleFetchTime > ScheduleCacheValidityPeriod)

                if (shouldRefresh) {
                    _scheduleADayState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getScheduleADay(date)
                        .collect { result ->
                            _scheduleADayState.value = result
                            if (result !is ResultResponse.Loading) {
                                _isRefreshing.value = false
                                if (result is ResultResponse.Success) {
                                    _scheduleADay.value = result.data
                                    lastScheduleFetchTime = System.currentTimeMillis()
                                }
                            }
                        }
                } else {
                    _isRefreshing.value = false
                }
            } catch (e: Exception) {
                _scheduleADayState.value =
                    ResultResponse.Error(e.localizedMessage ?: "Network error")
                _isRefreshing.value = false
            }
        }
    }

    fun refresh() {
        getUserDietProgress(forceRefresh = true)
        getCaloriesADay(forceRefresh = true)
        getWaterADay(forceRefresh = true)
        getScheduleADay(forceRefresh = true)
    }

    suspend fun getUserDetail(): Detail? {
        return dashboardRepository.getUserDetail()
    }
}