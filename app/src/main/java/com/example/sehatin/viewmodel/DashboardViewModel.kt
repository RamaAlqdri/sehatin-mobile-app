package com.example.sehatin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.CaloriesADayResponse
import com.example.sehatin.data.model.response.CaloriesHistoryResponse
import com.example.sehatin.data.model.response.CreateWaterHistoryResponse
import com.example.sehatin.data.model.response.Detail
import com.example.sehatin.data.model.response.DietProgressResponse
import com.example.sehatin.data.model.response.FoodDetailResponse
import com.example.sehatin.data.model.response.ScheduleADayResponse
import com.example.sehatin.data.model.response.WaterADayResponse
import com.example.sehatin.data.model.response.WaterHistoryResponse
import com.example.sehatin.data.repository.DashboardRepository
import com.example.sehatin.utils.getTodayUtcDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

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

    private val _caloriesDailyState =
        MutableStateFlow<ResultResponse<CaloriesADayResponse>>(ResultResponse.None)
    val caloriesDailyState: StateFlow<ResultResponse<CaloriesADayResponse>> = _caloriesDailyState

    private val _caloriesDaily = MutableStateFlow<CaloriesADayResponse?>(null)
    val caloriesDaily = _caloriesDaily.asStateFlow()

    private val _caloriesHistoryState =
        MutableStateFlow<ResultResponse<CaloriesHistoryResponse>>(ResultResponse.None)
    val caloriesHistoryState: StateFlow<ResultResponse<CaloriesHistoryResponse>> = _caloriesHistoryState

    private val _caloriesHistory = MutableStateFlow<CaloriesHistoryResponse?>(null)
    val caloriesHistory = _caloriesHistory.asStateFlow()

//    SEPARATOR
    private val _waterADayState =
    MutableStateFlow<ResultResponse<WaterADayResponse>>(ResultResponse.None)
    val waterADayState: StateFlow<ResultResponse<WaterADayResponse>> = _waterADayState

    private val _waterADay = MutableStateFlow<WaterADayResponse?>(null)
    val waterADay = _waterADay.asStateFlow()

    private val _waterDailyState =
        MutableStateFlow<ResultResponse<WaterADayResponse>>(ResultResponse.None)
    val waterDailyState: StateFlow<ResultResponse<WaterADayResponse>> = _waterDailyState

    private val _waterDaily = MutableStateFlow<WaterADayResponse?>(null)
    val waterDaily = _waterDaily.asStateFlow()

    private val _waterHistoryState =
        MutableStateFlow<ResultResponse<WaterHistoryResponse>>(ResultResponse.None)
    val waterHistoryState: StateFlow<ResultResponse<WaterHistoryResponse>> = _waterHistoryState

    private val _waterHistory = MutableStateFlow<WaterHistoryResponse?>(null)
    val waterHistory = _waterHistory.asStateFlow()


    private val _createWaterState =
        MutableStateFlow<ResultResponse<CreateWaterHistoryResponse>>(ResultResponse.Loading)
    val createWaterState: StateFlow<ResultResponse<CreateWaterHistoryResponse>> = _createWaterState


// SEPARATOR
    private val _scheduleADayState =
    MutableStateFlow<ResultResponse<ScheduleADayResponse>>(ResultResponse.None)
    val scheduleADayState: StateFlow<ResultResponse<ScheduleADayResponse>> = _scheduleADayState

    private val _scheduleADay = MutableStateFlow<ScheduleADayResponse?>(null)
    val scheduleADay = _scheduleADay.asStateFlow()

//

    private val _foodDetailState =
        MutableStateFlow<ResultResponse<FoodDetailResponse>>(ResultResponse.None)
    val foodDetailState: StateFlow<ResultResponse<FoodDetailResponse>> = _foodDetailState

    private val _foodDetail = MutableStateFlow<FoodDetailResponse?>(null)
    val foodDetail = _foodDetail.asStateFlow()



    private val _selectedDate = MutableStateFlow<Date>(getTodayUtcDate())
    val selectedDate: StateFlow<Date> = _selectedDate.asStateFlow()

    fun setSelectedDate(date: Date) {
        _selectedDate.value = date
    }




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
        // Memperbarui data saat tanggal dipilih berubah
        viewModelScope.launch {
            selectedDate.collect { date ->
                getCaloriesDaily(forceRefresh = true)
                getCaloriesHistory(forceRefresh = true)
                getWaterHistory(forceRefresh = true)
                getWaterDaily(forceRefresh = true)
//                getWaterADay(forceRefresh = true)
                // Memanggil API lainnya yang bergantung pada tanggal terpilih
            }
        }

        getUserDietProgress(forceRefresh = false)
        getCaloriesADay(forceRefresh = false)
        getWaterADay(forceRefresh = false)
        getScheduleADay(forceRefresh = false)
        getCaloriesDaily(forceRefresh = false)
        getCaloriesHistory(forceRefresh = false)
        getWaterHistory(forceRefresh = false)
        getWaterDaily(forceRefresh = false)


    }


    fun createWaterHistory(water: Double) {
        viewModelScope.launch {
            dashboardRepository.createWaterHistory(water).collect {
                _createWaterState.value = it
            }
        }
    }
    fun resetCreateWaterState() {
        _createWaterState.value = ResultResponse.None
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

    fun getFoodDetail(foodId: String, forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                _foodDetailState.value = ResultResponse.Loading
                dashboardRepository.getFoodDetail(foodId).collect { result ->
                    _foodDetailState.value = result
                    if (result is ResultResponse.Success) {
                        _foodDetail.value = result.data
                    }
                }
            } catch (e: Exception) {
                _foodDetailState.value =
                    ResultResponse.Error(e.localizedMessage ?: "Network error")
            }
        }
    }

    private fun getWaterADay(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val zoneId = ZoneId.of("UTC+8")
                val currentDate = LocalDateTime.now(zoneId)
                val date = Date.from(currentDate.atZone(zoneId).toInstant())

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
                val zoneId = ZoneId.of("UTC+8")
                val currentDate = LocalDateTime.now(zoneId)
                val date = Date.from(currentDate.atZone(zoneId).toInstant())


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

    private fun getCaloriesDaily(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val date = selectedDate.value

                val currentTime = System.currentTimeMillis()
                val shouldRefresh = _caloriesDailyState.value is ResultResponse.None ||
                        forceRefresh ||
                        (currentTime - lastCaloriesFetchTime > caloriesCacheValidityPeriod)

                if (shouldRefresh) {
                    _caloriesDailyState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getCaloriesADay(date)
                        .collect { result ->
                            _caloriesDailyState.value = result
                            if (result !is ResultResponse.Loading) {
                                _isRefreshing.value = false
                                if (result is ResultResponse.Success) {
                                    _caloriesDaily.value = result.data
                                    lastCaloriesFetchTime = System.currentTimeMillis()
                                }
                            }
                        }
                } else {
                    _isRefreshing.value = false
                }
            } catch (e: Exception) {
                _caloriesDailyState.value =
                    ResultResponse.Error(e.localizedMessage ?: "Network error")
                _isRefreshing.value = false
            }
        }
    }
    private fun getWaterDaily(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val date = selectedDate.value

                val currentTime = System.currentTimeMillis()
                val shouldRefresh = _waterDailyState.value is ResultResponse.None ||
                        forceRefresh ||
                        (currentTime - lastWaterFetchTime > WaterCacheValidityPeriod)

                if (shouldRefresh) {
                    _waterDailyState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getWaterADay(date)
                        .collect { result ->
                            _waterDailyState.value = result
                            if (result !is ResultResponse.Loading) {
                                _isRefreshing.value = false
                                if (result is ResultResponse.Success) {
                                    _waterDaily.value = result.data
                                    lastWaterFetchTime = System.currentTimeMillis()
                                }
                            }
                        }
                } else {
                    _isRefreshing.value = false
                }
            } catch (e: Exception) {
                _waterDailyState.value =
                    ResultResponse.Error(e.localizedMessage ?: "Network error")
                _isRefreshing.value = false
            }
        }
    }



    private fun getCaloriesHistory(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val date = selectedDate.value

                val currentTime = System.currentTimeMillis()
                val shouldRefresh = _caloriesHistoryState.value is ResultResponse.None ||
                        forceRefresh ||
                        (currentTime - lastCaloriesFetchTime > caloriesCacheValidityPeriod)

                if (shouldRefresh) {
                    _caloriesHistoryState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getCaloriesHistory(date)
                        .collect { result ->
                            _caloriesHistoryState.value = result
                            if (result !is ResultResponse.Loading) {
                                _isRefreshing.value = false
                                if (result is ResultResponse.Success) {
                                    _caloriesHistory.value = result.data
                                    lastCaloriesFetchTime = System.currentTimeMillis()
                                }
                            }
                        }
                } else {
                    _isRefreshing.value = false
                }
            } catch (e: Exception) {
                _caloriesHistoryState.value =
                    ResultResponse.Error(e.localizedMessage ?: "Network error")
                _isRefreshing.value = false
            }
        }
    }

    private fun getWaterHistory(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val date = selectedDate.value

                val currentTime = System.currentTimeMillis()
                val shouldRefresh = _waterHistoryState.value is ResultResponse.None ||
                        forceRefresh ||
                        (currentTime - lastWaterFetchTime > WaterCacheValidityPeriod)

                if (shouldRefresh) {
                    _waterHistoryState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getWaterHistory(date)
                        .collect { result ->
                            _waterHistoryState.value = result
                            if (result !is ResultResponse.Loading) {
                                _isRefreshing.value = false
                                if (result is ResultResponse.Success) {
                                    _waterHistory.value = result.data
                                    lastWaterFetchTime = System.currentTimeMillis()
                                }
                            }
                        }
                } else {
                    _isRefreshing.value = false
                }
            } catch (e: Exception) {
                _waterHistoryState.value =
                    ResultResponse.Error(e.localizedMessage ?: "Network error")
                _isRefreshing.value = false
            }
        }
    }


    private fun getScheduleADay(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val zoneId = ZoneId.of("UTC+8")
                val currentDate = LocalDateTime.now(zoneId)
                val date = Date.from(currentDate.atZone(zoneId).toInstant())


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
        getCaloriesDaily(forceRefresh = true)
        getCaloriesHistory(forceRefresh = true)
        getWaterADay(forceRefresh = true)
        getScheduleADay(forceRefresh = true)
        getWaterHistory(forceRefresh = true)
        getWaterDaily(forceRefresh = true)
    }

    suspend fun getUserDetail(): Detail? {
        return dashboardRepository.getUserDetail()
    }
}