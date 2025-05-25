package com.example.sehatin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.CaloriesADayResponse
import com.example.sehatin.data.model.response.CaloriesHistoryResponse
import com.example.sehatin.data.model.response.CreateWaterHistoryResponse
import com.example.sehatin.data.model.response.Detail
import com.example.sehatin.data.model.response.DietProgressResponse
import com.example.sehatin.data.model.response.DietResponse
import com.example.sehatin.data.model.response.FoodDetailResponse
import com.example.sehatin.data.model.response.ScheduleADayResponse
import com.example.sehatin.data.model.response.ScheduleDataItem
import com.example.sehatin.data.model.response.WaterADayResponse
import com.example.sehatin.data.model.response.WaterHistoryResponse
import com.example.sehatin.data.model.response.WeightResponse
import com.example.sehatin.data.repository.DashboardRepository
import com.example.sehatin.utils.getTodayUtcDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date


data class RadioOptionSummary(val index: Int, val type: String)


class DashboardViewModel(
    private val dashboardRepository: DashboardRepository
) : ViewModel() {


    val summaryOption = listOf(
        RadioOptionSummary(0, "day"),
        RadioOptionSummary(1, "7days"),
        RadioOptionSummary(2, "30days"),
        RadioOptionSummary(3, "range"),
    )
    private val _selectedSummaryOption = MutableStateFlow(summaryOption[0])
    val selectedSummaryOption: StateFlow<RadioOptionSummary> = _selectedSummaryOption.asStateFlow()

    private val _selectedSummaryDate = MutableStateFlow<Date>(getTodayUtcDate())
    val selectedSummaryDate: StateFlow<Date> = _selectedSummaryDate.asStateFlow()

    fun setSelectedSummaryDate(date: Date) {
        _selectedSummaryDate.value = date
    }

    private val _selectedStartSummaryDate = MutableStateFlow<Date>(getTodayUtcDate())
    val selectedStartSummaryDate: StateFlow<Date> = _selectedStartSummaryDate.asStateFlow()

    fun setSelectedStartSummaryDate(date: Date) {
        _selectedStartSummaryDate.value = date
    }

    private val _selectedEndSummaryDate = MutableStateFlow<Date>(getTodayUtcDate())
    val selectedEndSummaryDate: StateFlow<Date> = _selectedEndSummaryDate.asStateFlow()

    fun setSelectedEndSummaryDate(date: Date) {
        _selectedEndSummaryDate.value = date
    }

    private val _summaryState =
        MutableStateFlow<ResultResponse<DietResponse.FetchSummaryResponse>>(ResultResponse.None)
    val summaryState: StateFlow<ResultResponse<DietResponse.FetchSummaryResponse>> = _summaryState

    private val _summary = MutableStateFlow<DietResponse.FetchSummaryResponse?>(null)
    val summary = _summary.asStateFlow()

    //    SEPARATOR
    private val _dietProgressState =
        MutableStateFlow<ResultResponse<DietProgressResponse>>(ResultResponse.None)
    val dietProgressState: StateFlow<ResultResponse<DietProgressResponse>> = _dietProgressState

    private val _dietProgress = MutableStateFlow<DietProgressResponse?>(null)
    val dietProgress = _dietProgress.asStateFlow()


    private val _weightHistoryState =
        MutableStateFlow<ResultResponse<WeightResponse>>(ResultResponse.None)
    val weightHistoryState: StateFlow<ResultResponse<WeightResponse>> = _weightHistoryState

    private val _weightHistory = MutableStateFlow<WeightResponse?>(null)
    val weightHistory = _weightHistory.asStateFlow()

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
    val caloriesHistoryState: StateFlow<ResultResponse<CaloriesHistoryResponse>> =
        _caloriesHistoryState

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
    private val _deleteLatestWaterState =
        MutableStateFlow<ResultResponse<CreateWaterHistoryResponse>>(ResultResponse.Loading)
    val deleteLatestWaterState: StateFlow<ResultResponse<CreateWaterHistoryResponse>> =
        _deleteLatestWaterState
    private val _deleteWaterbyIdState =
        MutableStateFlow<ResultResponse<CreateWaterHistoryResponse>>(ResultResponse.Loading)
    val deleteWaterbyIdState: StateFlow<ResultResponse<CreateWaterHistoryResponse>> =
        _deleteWaterbyIdState


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

    private var lastWeightFetchTime = 0L
    private val weightCacheValidityPeriod = 5 * 60 * 1000L

    private var lastSummaryFetchTime = 0L
    private val summaryCacheValidityPeriod = 5 * 60 * 1000L

    init {
        // Memperbarui data saat tanggal dipilih berubah
        viewModelScope.launch {
            launch {
                selectedDate.collect { date ->
                    // Memanggil API yang bergantung pada selectedDate
                    getCaloriesDaily(forceRefresh = true)
                    getCaloriesHistory(forceRefresh = true)
                    getWaterHistory(forceRefresh = true)
                    getWaterDaily(forceRefresh = true)
                }
            }

            launch {
                _selectedSummaryOption.collect {
                    getSummary(true)
                }
            }

//            selectedStartSummaryDate.collect {
//                getSummary(true)
//            }
//
//            selectedEndSummaryDate.collect {
//                getSummary(true)
//            }
        }

        getUserDietProgress(forceRefresh = false)
        getCaloriesADay(forceRefresh = false)
        getWaterADay(forceRefresh = false)
        getSummary(true)
        getScheduleADay(forceRefresh = false)
        getCaloriesDaily(forceRefresh = false)
        getCaloriesHistory(forceRefresh = false)
        getWaterHistory(forceRefresh = false)
        getWeightHistory(forceRefresh = false)
        getWaterDaily(forceRefresh = false)


    }


    fun createWaterHistory(water: Double) {
        viewModelScope.launch {
            dashboardRepository.createWaterHistory(water).collect {
                _createWaterState.value = it
            }
        }
    }

    fun deleteLatestWaterHistory() {
        viewModelScope.launch {
            dashboardRepository.deleteLatestWaterHistory().collect {
                _deleteLatestWaterState.value = it
            }
        }
    }

    fun deleteWaterByIdHistory(id: String) {
        viewModelScope.launch {
            dashboardRepository.deleteWaterByIdHistory(id).collect {
                _deleteWaterbyIdState.value = it
            }
        }
    }

    fun resetCreateWaterState() {
        _createWaterState.value = ResultResponse.None
    }

    fun resetDeleteLatestWaterState() {
        _deleteLatestWaterState.value = ResultResponse.None
    }

    fun resetDeleteWaterByIdState() {
        _deleteWaterbyIdState.value = ResultResponse.None
    }

    private fun getUserDietProgress(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val currentTime = System.currentTimeMillis()
                val shouldRefresh =
                    _dietProgressState.value is ResultResponse.None || forceRefresh || (currentTime - lastDietFetchTime > dietCacheValidityPeriod)

                if (shouldRefresh) {
                    _dietProgressState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getUserDietProgress().collect { result ->
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

    private fun getWeightHistory(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val currentTime = System.currentTimeMillis()
                val shouldRefresh =
                    _weightHistoryState.value is ResultResponse.None || forceRefresh || (currentTime - lastWeightFetchTime > weightCacheValidityPeriod)

                if (shouldRefresh) {
                    _weightHistoryState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getUserWeightHistory().collect { result ->
                        _weightHistoryState.value = result
                        if (result !is ResultResponse.Loading) {
                            _isRefreshing.value = false
                            if (result is ResultResponse.Success) {
                                _weightHistory.value = result.data
                                lastWeightFetchTime = System.currentTimeMillis()
                            }
                        }
                    }
                } else {
                    _isRefreshing.value = false
                }
            } catch (e: Exception) {
                _weightHistoryState.value =
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
                _foodDetailState.value = ResultResponse.Error(e.localizedMessage ?: "Network error")
            }
        }
    }

    private fun getWaterADay(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val today = LocalDate.now()
                val zoneId = ZoneId.of("UTC+8")
                val zonedDateTime = today.atTime(12, 0).atZone(zoneId)
                val instant = zonedDateTime.toInstant()
                val date = Date.from(instant)

                val currentTime = System.currentTimeMillis()
                val shouldRefresh =
                    _waterADayState.value is ResultResponse.None || forceRefresh || (currentTime - lastWaterFetchTime > WaterCacheValidityPeriod)

                if (shouldRefresh) {
                    _waterADayState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getWaterADay(date).collect { result ->
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
                _waterADayState.value = ResultResponse.Error(e.localizedMessage ?: "Network error")
                _isRefreshing.value = false
            }
        }
    }

    private fun getCaloriesADay(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val today = LocalDate.now()
                val zoneId = ZoneId.of("UTC+8")
                val zonedDateTime = today.atTime(12, 0).atZone(zoneId)
                val instant = zonedDateTime.toInstant()
                val date = Date.from(instant)


//                Log.e("RESULT", "getCaloriesADay: $formattedDate")

                val currentTime = System.currentTimeMillis()
                val shouldRefresh =
                    _caloriesADayState.value is ResultResponse.None || forceRefresh || (currentTime - lastCaloriesFetchTime > caloriesCacheValidityPeriod)

                if (shouldRefresh) {
                    _caloriesADayState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getCaloriesADay(date).collect { result ->
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
                val shouldRefresh =
                    _caloriesDailyState.value is ResultResponse.None || forceRefresh || (currentTime - lastCaloriesFetchTime > caloriesCacheValidityPeriod)

                if (shouldRefresh) {
                    _caloriesDailyState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getCaloriesADay(date).collect { result ->
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
                val shouldRefresh =
                    _waterDailyState.value is ResultResponse.None || forceRefresh || (currentTime - lastWaterFetchTime > WaterCacheValidityPeriod)

                if (shouldRefresh) {
                    _waterDailyState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getWaterADay(date).collect { result ->
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
                _waterDailyState.value = ResultResponse.Error(e.localizedMessage ?: "Network error")
                _isRefreshing.value = false
            }
        }
    }


    private fun getCaloriesHistory(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {
                val date = selectedDate.value

                val currentTime = System.currentTimeMillis()
                val shouldRefresh =
                    _caloriesHistoryState.value is ResultResponse.None || forceRefresh || (currentTime - lastCaloriesFetchTime > caloriesCacheValidityPeriod)

                if (shouldRefresh) {
                    _caloriesHistoryState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getCaloriesHistory(date).collect { result ->
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
                val shouldRefresh =
                    _waterHistoryState.value is ResultResponse.None || forceRefresh || (currentTime - lastWaterFetchTime > WaterCacheValidityPeriod)

                if (shouldRefresh) {
                    _waterHistoryState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getWaterHistory(date).collect { result ->
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
                val today = LocalDate.now()
                val zoneId = ZoneId.of("UTC+8")
                val zonedDateTime = today.atTime(12, 0).atZone(zoneId)
                val instant = zonedDateTime.toInstant()
                val date = Date.from(instant)

                val currentTime = System.currentTimeMillis()
                val shouldRefresh =
                    _scheduleADayState.value is ResultResponse.None || forceRefresh || (currentTime - lastScheduleFetchTime > ScheduleCacheValidityPeriod)

                if (shouldRefresh) {
                    _scheduleADayState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getScheduleADay(date).collect { result ->
                        _scheduleADayState.value = result
                        if (result !is ResultResponse.Loading) {
                            _isRefreshing.value = false
                            if (result is ResultResponse.Success) {
                                _scheduleADay.value = result.data
                                lastScheduleFetchTime = System.currentTimeMillis()
                                // Simpan ke DataStore
// Simpan ke DataStore

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


    suspend fun getSavedSchedule(): List<ScheduleDataItem>? {
        return dashboardRepository.getSavedScheduleList()
    }


    fun setSelectedSummaryOption(type: String) {
        summaryOption.find { it.type == type }?.let {
            _selectedSummaryOption.value = it
        }
    }

    fun selectedSummaryLabel(): String {
        return when (selectedSummaryOption.value.type) {
            "day" -> "H"
            "7days" -> "M"
            "30days" -> "B"
            else -> "H"
        }
    }

    fun getSummary(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            try {


                val localDateTime = LocalDateTime.now()
                val zoneId = ZoneId.of("Asia/Makassar") // konsisten sejak awal
                val instant = localDateTime.atZone(zoneId).toInstant()
                val date = Date.from(instant)

                val currentTime = System.currentTimeMillis()
                val shouldRefresh =
                    _weightHistoryState.value is ResultResponse.None || forceRefresh || (currentTime - lastSummaryFetchTime > summaryCacheValidityPeriod)

                if (shouldRefresh) {
                    _weightHistoryState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getSummary(
                        selectedSummaryOption.value.type,
                        date.toString(),
                        selectedStartSummaryDate.value.toString(),
                        selectedEndSummaryDate.value.toString()
                    ).collect { result ->
                        _summaryState.value = result
                        if (result !is ResultResponse.Loading) {
                            _isRefreshing.value = false
                            if (result is ResultResponse.Success) {
                                _summary.value = result.data
                                lastSummaryFetchTime = System.currentTimeMillis()
                            }
                        }
                    }
                } else {
                    _isRefreshing.value = false
                }
            } catch (e: Exception) {
                _summaryState.value = ResultResponse.Error(e.localizedMessage ?: "Network error")
                _isRefreshing.value = false
            }
        }
    }



//    fun generateChartData(summary: FetchSummaryResponse): ChartData {
//        val foodHistory = summary.data.foodHistory
//
//        val dataWithDates = foodHistory.mapNotNull { entry ->
//            entry.createdAt.toDate()?.let { date ->
//                Triple(date, entry.meal_type, entry.calories)
//            }
//        }
//
//        val sortedData = dataWithDates.map { it.first }.distinct().sorted()
//
//        val labels = if (sortedData.size > 7) {
//            groupDataByWeek(sortedData).map { week ->
//                val weekNumber = week.first().toWeekNumber()
//                "Minggu $weekNumber"
//            }.distinct()
//        } else {
//            sortedData.map { it.toDayLabel() }.distinct()
//        }
//
//        fun sumCalories(mealType: String, dateRange: List<Date>): Float {
//            return dataWithDates
//                .filter { (date, type, _) -> date in dateRange && type == mealType }
//                .sumOf { it.third.toDouble() }
//                .toFloat()
//        }
//
//        val labelToRange = labels.associateWith { getDateRangeForLabel(it, sortedData) }
//
//        val breakfastData = labels.map { label -> sumCalories("breakfast", labelToRange[label] ?: emptyList()) }
//        val lunchData = labels.map { label -> sumCalories("lunch", labelToRange[label] ?: emptyList()) }
//        val dinnerData = labels.map { label -> sumCalories("dinner", labelToRange[label] ?: emptyList()) }
//        val otherData = labels.map { label -> sumCalories("other", labelToRange[label] ?: emptyList()) }
//
//        return ChartData(labels, breakfastData, lunchData, dinnerData, otherData)
//    }
//
//    fun groupDataByWeek(sortedData: List<Date>): List<List<Date>> {
//        val calendar = Calendar.getInstance()
//        val weeks = mutableListOf<List<Date>>()
//        var currentWeek = mutableListOf<Date>()
//        calendar.time = sortedData.first()
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
//        var currentWeekStart = calendar.time
//
//        for (date in sortedData) {
//            val weekStart = Calendar.getInstance().apply {
//                time = date
//                set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
//            }.time
//            if (weekStart == currentWeekStart) {
//                currentWeek.add(date)
//            } else {
//                weeks.add(currentWeek)
//                currentWeek = mutableListOf(date)
//                currentWeekStart = weekStart
//            }
//        }
//
//        if (currentWeek.isNotEmpty()) {
//            weeks.add(currentWeek)
//        }
//
//        return weeks
//    }
//
//    fun getDateRangeForLabel(label: String, sortedData: List<Date>): List<Date> {
//        return if (label.startsWith("Minggu")) {
//            val weekNumber = label.split(" ")[1].toInt()
//            val weekData = sortedData.filter {
//                it.toWeekNumber() == weekNumber &&
//                        Calendar.getInstance().apply { time = it }.get(Calendar.YEAR) == Calendar.getInstance().apply { time = sortedData.first() }.get(Calendar.YEAR)
//            }
//            if (weekData.isNotEmpty()) getDatesInRange(weekData.first(), weekData.last()) else emptyList()
//        } else {
//            sortedData.filter { it.toDayLabel() == label }
//        }
//    }
//
//    fun getDatesInRange(startDate: Date, endDate: Date): List<Date> {
//        val calendar = Calendar.getInstance()
//        calendar.time = startDate
//        val datesInRange = mutableListOf<Date>()
//
//        while (!calendar.time.after(endDate)) {
//            datesInRange.add(calendar.time)
//            calendar.add(Calendar.DAY_OF_YEAR, 1)
//        }
//        return datesInRange
//    }
//
//    fun String.toDate(): Date? {
//        return try {
//            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
//            inputFormat.parse(this)
//        } catch (e: Exception) {
//            null
//        }
//    }
//
//    fun Date.toDayLabel(): String {
//        val outputFormat = SimpleDateFormat("EEE dd", Locale("id"))
//        return outputFormat.format(this)
//    }
//
//    fun Date.toWeekNumber(): Int {
//        val calendar = Calendar.getInstance()
//        calendar.time = this
//        return calendar.get(Calendar.WEEK_OF_YEAR)
//    }
    fun refresh() {
        getUserDietProgress(forceRefresh = true)
        getWeightHistory(forceRefresh = true)
        getCaloriesADay(forceRefresh = true)
        getCaloriesDaily(forceRefresh = true)
        getCaloriesHistory(forceRefresh = true)
        getWaterADay(forceRefresh = true)
        getScheduleADay(forceRefresh = true)
        getWaterHistory(forceRefresh = true)
        getWaterDaily(forceRefresh = true)
        getSummary(true)
    }

    suspend fun getUserDetail(): Detail? {
        return dashboardRepository.getUserDetail()
    }
}


data class ChartData(
    val labels: List<String>?,
    val breakfastData: List<Float>?,
    val lunchData: List<Float>?,
    val dinnerData: List<Float>?,
    val snackData: List<Float>?
)
