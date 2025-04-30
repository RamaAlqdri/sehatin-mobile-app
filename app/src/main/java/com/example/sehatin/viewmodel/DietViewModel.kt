package com.example.sehatin.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.CreateWaterHistoryResponse
import com.example.sehatin.data.model.response.DietResponse
import com.example.sehatin.data.model.response.DietResponse.FoodFilterResponse
import com.example.sehatin.data.model.response.FoodDetailResponse
import com.example.sehatin.data.model.response.ScheduleADayResponse
import com.example.sehatin.data.model.response.ScheduleClosestResponse
import com.example.sehatin.data.model.response.WaterADayResponse
import com.example.sehatin.data.repository.DietRepository
import com.example.sehatin.utils.getTodayUtcDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class DietViewModel(
    private val dietRepository: DietRepository
) : ViewModel() {

    //    SEPARATOR
    private val _waterADayState =
        MutableStateFlow<ResultResponse<WaterADayResponse>>(ResultResponse.None)
    val waterADayState: StateFlow<ResultResponse<WaterADayResponse>> = _waterADayState

    private val _waterADay = MutableStateFlow<WaterADayResponse?>(null)
    val waterADay = _waterADay.asStateFlow()

    private val _scheduleClosestState =
        MutableStateFlow<ResultResponse<ScheduleClosestResponse>>(ResultResponse.None)
    val scheduleClosestState: StateFlow<ResultResponse<ScheduleClosestResponse>> =
        _scheduleClosestState

    private val _scheduleClosest = MutableStateFlow<ScheduleClosestResponse?>(null)
    val scheduleClosest = _scheduleClosest.asStateFlow()

    private val _createWaterState =
        MutableStateFlow<ResultResponse<CreateWaterHistoryResponse>>(ResultResponse.Loading)
    val createWaterState: StateFlow<ResultResponse<CreateWaterHistoryResponse>> = _createWaterState

    private val _completedScheduleState =
        MutableStateFlow<ResultResponse<DietResponse.UpdateScheduleResponse>>(ResultResponse.None)
    val completedScheduleState: StateFlow<ResultResponse<DietResponse.UpdateScheduleResponse>> =
        _completedScheduleState

    private val _updateFoodScheduleState =
        MutableStateFlow<ResultResponse<DietResponse.UpdateScheduleResponse>>(ResultResponse.None)
    val updateFoodScheduleState: StateFlow<ResultResponse<DietResponse.UpdateScheduleResponse>> =
        _updateFoodScheduleState

    private val _scheduleTodayState =
        MutableStateFlow<ResultResponse<ScheduleADayResponse>>(ResultResponse.None)
    val scheduleTodayState: StateFlow<ResultResponse<ScheduleADayResponse>> = _scheduleTodayState

    private val _scheduleToday = MutableStateFlow<ScheduleADayResponse?>(null)
    val scheduleToday = _scheduleToday.asStateFlow()

    private val _foodFilterState =
        MutableStateFlow<ResultResponse<FoodFilterResponse>>(ResultResponse.None)
    val foodFilterState: StateFlow<ResultResponse<FoodFilterResponse>> = _foodFilterState

    private val _foodFilter = MutableStateFlow<FoodFilterResponse?>(null)
    val foodFilter = _foodFilter.asStateFlow()

    private val _foodRecommendationState =
        MutableStateFlow<ResultResponse<FoodFilterResponse>>(ResultResponse.None)
    val foodRecommendationState: StateFlow<ResultResponse<FoodFilterResponse>> = _foodRecommendationState

    private val _foodRecommendation = MutableStateFlow<FoodFilterResponse?>(null)
    val foodRecommendation = _foodRecommendation.asStateFlow()


    private val _scheduleTomorrowState =
        MutableStateFlow<ResultResponse<ScheduleADayResponse>>(ResultResponse.None)
    val scheduleTomorrowState: StateFlow<ResultResponse<ScheduleADayResponse>> =
        _scheduleTomorrowState

    private val _scheduleTomorrow = MutableStateFlow<ScheduleADayResponse?>(null)
    val scheduleTomorrow = _scheduleTomorrow.asStateFlow()

    private val _scheduleDailyState =
        MutableStateFlow<ResultResponse<ScheduleADayResponse>>(ResultResponse.None)
    val scheduleDailyState: StateFlow<ResultResponse<ScheduleADayResponse>> = _scheduleDailyState

    private val _scheduleDaily = MutableStateFlow<ScheduleADayResponse?>(null)
    val scheduleDaily = _scheduleDaily.asStateFlow()

    private val _foodDetailState =
        MutableStateFlow<ResultResponse<FoodDetailResponse>>(ResultResponse.None)
    val foodDetailState: StateFlow<ResultResponse<FoodDetailResponse>> = _foodDetailState

    private val _foodDetail = MutableStateFlow<FoodDetailResponse?>(null)
    val foodDetail = _foodDetail.asStateFlow()


    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private var lastWaterFetchTime = 0L
    private val WaterCacheValidityPeriod = 5 * 60 * 1000L

    private var lastScheduleFetchTime = 0L
    private val ScheduleCacheValidityPeriod = 5 * 60 * 1000L


    private var lastScheduleTomorrowFetchTime = 0L
    private val ScheduleTomorrowCacheValidityPeriod = 5 * 60 * 1000L


    private var lastScheduleClosestFetchTime = 0L
    private val ScheduleClosestCacheValidityPeriod = 5 * 60 * 1000L

    private var lastFoodFilterFetchTime = 0L
    private val foodFilterCacheValidityPeriod = 5 * 60 * 1000L

    private var lastFoodRecommendationFetchTime = 0L
    private val foodRecommendationCacheValidityPeriod = 5 * 60 * 1000L


    private val _foodName = MutableStateFlow<String>("")
    val foodName = _foodName.asStateFlow()

    fun setFoodName(name: String) {
        _foodName.value = name
    }

    private val _foodLimit = MutableStateFlow<Int>(5)
    val foodLimit = _foodLimit.asStateFlow()

    private val _selectedDate = MutableStateFlow<Date>(getTodayUtcDate())
    val selectedDate: StateFlow<Date> = _selectedDate.asStateFlow()

    fun setSelectedDate(date: Date) {
        _selectedDate.value = date
    }


    init {

        viewModelScope.launch {
            foodName.collect { name ->
                Log.e("DietViewModel", "foodName: $name")
                getFoodFilter(forceRefresh = true)
            }

            selectedDate.collect {date ->
                getScheduleDaily(forceRefresh = true)
//                refresh()
            }


        }

        getFoodFilter(forceRefresh = false)
        getWaterADay(forceRefresh = false)
        getScheduleToday(forceRefresh = false)
        getScheduleTomorrow(forceRefresh = false)
        getScheduleClosest(forceRefresh = false)
        getFoodRecommendation(forceRefresh = false)
        getScheduleDaily(forceRefresh = false)

    }

    fun setCompletedSchedule() {
        viewModelScope.launch {
            dietRepository.setCompleteSchedule(scheduleClosest.value?.data?.id.toString())
                .collect {
                    _completedScheduleState.value = it
                }
        }
    }

    fun resetCompletedScheduleState() {
        _completedScheduleState.value = ResultResponse.None
    }

    fun createWaterHistory(water: Double) {
        viewModelScope.launch {
            dietRepository.createWaterHistory(water).collect {
                _createWaterState.value = it
            }
        }
    }

    fun resetCreateWaterState() {
        _createWaterState.value = ResultResponse.None
    }

    fun resetUpdateFoodScheduleState() {
        _updateFoodScheduleState.value = ResultResponse.None
    }

    fun resetScheduleDailyState() {
        _scheduleDailyState.value = ResultResponse.None
    }

    fun updateFoodSchedule(
        scheduleId: String,
        foodId: String
    ) {

        viewModelScope.launch {
            dietRepository.updateFoodSchedule(scheduleId, foodId).collect {
                _updateFoodScheduleState.value = it
            }
        }
    }



    private fun getScheduleDaily(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val date = selectedDate.value


                val currentTime = System.currentTimeMillis()
                val shouldRefresh = _scheduleDailyState.value is ResultResponse.None ||
                        forceRefresh ||
                        (currentTime - lastScheduleFetchTime > ScheduleCacheValidityPeriod)

                if (shouldRefresh) {
                    _scheduleDailyState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dietRepository.getScheduleADay(date)
                        .collect { result ->
                            _scheduleDailyState.value = result
                            if (result !is ResultResponse.Loading) {
                                _isRefreshing.value = false
                                if (result is ResultResponse.Success) {
                                    _scheduleDaily.value = result.data
                                    lastScheduleFetchTime = System.currentTimeMillis()
                                }
                            }
                        }
                } else {
                    _isRefreshing.value = false
                }
            } catch (e: Exception) {
                _scheduleDailyState.value =
                    ResultResponse.Error(e.localizedMessage ?: "Network error")
                _isRefreshing.value = false
            }
        }
    }


    private fun getFoodRecommendation(
        forceRefresh: Boolean = false
    ) {
        viewModelScope.launch {
            try {
                val currentTime = System.currentTimeMillis()
                val shouldRefresh = _foodRecommendationState.value is ResultResponse.None ||
                        forceRefresh ||
                        (currentTime - lastFoodRecommendationFetchTime > foodRecommendationCacheValidityPeriod)

                if (shouldRefresh) {
                    _foodRecommendationState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dietRepository.getFoodRecommendation()
                        .collect { result ->
                            _foodRecommendationState.value = result
                            if (result !is ResultResponse.Loading) {
                                _isRefreshing.value = false
                                if (result is ResultResponse.Success) {
                                    _foodRecommendation.value = result.data
                                    lastFoodRecommendationFetchTime = System.currentTimeMillis()
                                }
                            }
                        }
                } else {
                    _isRefreshing.value = false
                }
            } catch (e: Exception) {
                _foodRecommendationState.value =
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
                val date = Date.from(currentDate.atZone(ZoneId.of("UTC")).toInstant())


                val currentTime = System.currentTimeMillis()
                val shouldRefresh = _waterADayState.value is ResultResponse.None ||
                        forceRefresh ||
                        (currentTime - lastWaterFetchTime > WaterCacheValidityPeriod)

                if (shouldRefresh) {
                    _waterADayState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dietRepository.getWaterADay(date)
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

    fun getFoodDetail(foodId: String, forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                _foodDetailState.value = ResultResponse.Loading
                dietRepository.getFoodDetail(foodId).collect { result ->
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

    private fun getFoodFilter(
        forceRefresh: Boolean = false
    ) {

        viewModelScope.launch {
            try {
                val currentTime = System.currentTimeMillis()
                val shouldRefresh = _foodFilterState.value is ResultResponse.None ||
                        forceRefresh ||
                        (currentTime - lastFoodFilterFetchTime > foodFilterCacheValidityPeriod)

                if (shouldRefresh) {
                    _foodFilterState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dietRepository.getFoodFilter(foodName.value, foodLimit.value)
                        .collect { result ->
                            _foodFilterState.value = result
                            if (result !is ResultResponse.Loading) {
                                _isRefreshing.value = false
                                if (result is ResultResponse.Success) {
                                    _foodFilter.value = result.data
                                    lastFoodFilterFetchTime = System.currentTimeMillis()
                                }
                            }
                        }
                } else {
                    _isRefreshing.value = false
                }
            } catch (e: Exception) {
                _foodFilterState.value =
                    ResultResponse.Error(e.localizedMessage ?: "Network error")
                _isRefreshing.value = false
            }
        }

    }

    private fun getScheduleToday(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val zoneId = ZoneId.of("UTC+8")
                val currentDate = LocalDateTime.now(zoneId)
                val formatter = DateTimeFormatter.ISO_INSTANT
                val formattedDate = currentDate.atZone(zoneId).format(formatter)
                val date = Date.from(currentDate.atZone(zoneId).toInstant())


                val currentTime = System.currentTimeMillis()
                val shouldRefresh = _scheduleTodayState.value is ResultResponse.None ||
                        forceRefresh ||
                        (currentTime - lastScheduleFetchTime > ScheduleCacheValidityPeriod)

                if (shouldRefresh) {
                    _scheduleTodayState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dietRepository.getScheduleADay(date)
                        .collect { result ->
                            _scheduleTodayState.value = result
                            if (result !is ResultResponse.Loading) {
                                _isRefreshing.value = false
                                if (result is ResultResponse.Success) {
                                    _scheduleToday.value = result.data
                                    lastScheduleFetchTime = System.currentTimeMillis()
                                }
                            }
                        }
                } else {
                    _isRefreshing.value = false
                }
            } catch (e: Exception) {
                _scheduleTodayState.value =
                    ResultResponse.Error(e.localizedMessage ?: "Network error")
                _isRefreshing.value = false
            }
        }
    }

    private fun getScheduleClosest(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val zoneId = ZoneId.of("UTC+8")
                val currentDate = LocalDateTime.now(zoneId)
                val formatter = DateTimeFormatter.ISO_INSTANT
                val formattedDate = currentDate.atZone(zoneId).format(formatter)
                val date = Date.from(currentDate.atZone(zoneId).toInstant())

                val currentTime = System.currentTimeMillis()
                val shouldRefresh = _scheduleClosestState.value is ResultResponse.None ||
                        forceRefresh ||
                        (currentTime - lastScheduleClosestFetchTime > ScheduleClosestCacheValidityPeriod)

                if (shouldRefresh) {
                    _scheduleClosestState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dietRepository.getScheduleClosest(date)
                        .collect { result ->
                            _scheduleClosestState.value = result
                            if (result !is ResultResponse.Loading) {
                                _isRefreshing.value = false
                                if (result is ResultResponse.Success) {
                                    _scheduleClosest.value = result.data
                                    lastScheduleClosestFetchTime = System.currentTimeMillis()
                                }
                            }
                            Log.e(
                                "DietViewModel",
                                "getScheduleClosest in ViewModel: ${result}"
                            )
                        }
                } else {
                    _isRefreshing.value = false
                }
            } catch (e: Exception) {
                _scheduleTodayState.value =
                    ResultResponse.Error(e.localizedMessage ?: "Network error")
                _isRefreshing.value = false
            }
        }
    }

    private fun getScheduleTomorrow(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val zoneId = ZoneId.of("UTC+8")
                val tomorrowDate = LocalDateTime.now(zoneId).plusDays(1)
                val formatter = DateTimeFormatter.ISO_INSTANT
                val formattedDate = tomorrowDate.atZone(zoneId).format(formatter)
                val date = Date.from(tomorrowDate.atZone(zoneId).toInstant())

                val currentTime = System.currentTimeMillis()
                val shouldRefresh = _scheduleTomorrowState.value is ResultResponse.None ||
                        forceRefresh ||
                        (currentTime - lastScheduleTomorrowFetchTime > ScheduleTomorrowCacheValidityPeriod)

                if (shouldRefresh) {
                    _scheduleTomorrowState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dietRepository.getScheduleADay(date)
                        .collect { result ->
                            _scheduleTomorrowState.value = result
                            if (result !is ResultResponse.Loading) {
                                _isRefreshing.value = false
                                if (result is ResultResponse.Success) {
                                    _scheduleTomorrow.value = result.data
                                    lastScheduleTomorrowFetchTime = System.currentTimeMillis()
                                }
                            }
                        }
                } else {
                    _isRefreshing.value = false
                }
            } catch (e: Exception) {
                _scheduleTomorrowState.value =
                    ResultResponse.Error(e.localizedMessage ?: "Network error")
                _isRefreshing.value = false
            }
        }
    }


    fun refresh() {
        getWaterADay(forceRefresh = true)
        getScheduleToday(forceRefresh = true)
        getScheduleTomorrow(forceRefresh = true)
        getScheduleClosest(forceRefresh = true)
        getFoodFilter(forceRefresh = true)
        getFoodRecommendation(forceRefresh = true)
        getScheduleDaily(forceRefresh = true)


    }


}