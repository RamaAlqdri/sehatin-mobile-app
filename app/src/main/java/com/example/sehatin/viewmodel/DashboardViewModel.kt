package com.example.sehatin.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.CaloriesADayResponse
import com.example.sehatin.data.model.response.CaloriesHistoryResponse
import com.example.sehatin.data.model.response.CreateWaterHistoryResponse
import com.example.sehatin.data.model.response.Detail
import com.example.sehatin.data.model.response.DietProgressResponse
import com.example.sehatin.data.model.response.DietResponse
import com.example.sehatin.data.model.response.DietResponse.FetchSummaryResponse
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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


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

    fun resetCreateWaterState() {
        _createWaterState.value = ResultResponse.None
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

                val today = LocalDate.now()
                val zoneId = ZoneId.of("UTC+8")
                val zonedDateTime = today.atTime(12, 0).atZone(zoneId)
                val instant = zonedDateTime.toInstant()
                val todayDate = Date.from(instant)

                val currentTime = System.currentTimeMillis()
                val shouldRefresh =
                    _weightHistoryState.value is ResultResponse.None || forceRefresh || (currentTime - lastSummaryFetchTime > summaryCacheValidityPeriod)

                if (shouldRefresh) {
                    _weightHistoryState.value = ResultResponse.Loading
                    _isRefreshing.value = true

                    dashboardRepository.getSummary(
                        selectedSummaryOption.value.type,
                        todayDate.toString(),
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

    fun generateChartData(summary: FetchSummaryResponse): ChartData {
        // Mengambil data foodHistory dari response
        val foodHistory = summary.data.foodHistory

        // Mengonversi createdAt ke tanggal yang dapat diproses
        val sortedData = foodHistory
            .mapNotNull { it.createdAt.toDate() } // Mengonversi createdAt menjadi Date
            .distinctBy { it.toDayLabel() } // Pastikan setiap tanggal unik
            .sorted() // Urutkan berdasarkan tanggal yang sudah diparse

        // Membuat label berdasarkan tanggal (jika data kurang dari 7 hari)
        val labels = if (sortedData.size > 7) {
            // Jika lebih dari 7 hari, kita akan menggunakan label minggu
            groupDataByWeek(sortedData).map { "Minggu ${it.first().toWeekNumber()}" }.distinct()
        } else {
            // Jika data kurang dari 7 hari, gunakan label per tanggal
            sortedData.map { it.toDayLabel() }
        }

        // Fungsi untuk menghitung kalori berdasarkan meal type dan label tanggal
        fun sumCalories(mealType: String, dateRange: List<Date>): Float {
            return foodHistory.filter { it.createdAt.toDate() in dateRange && it.meal_type == mealType }
                .sumOf { it.calories.toDouble() }
                .toFloat()
        }

        // Menghitung kalori untuk setiap meal type berdasarkan minggu atau per tanggal
        val breakfastData = labels.map { label ->
            sumCalories(
                "breakfast",
                getDateRangeForLabel(label, sortedData)
            )
        }
        val lunchData =
            labels.map { label -> sumCalories("lunch", getDateRangeForLabel(label, sortedData)) }
        val dinnerData =
            labels.map { label -> sumCalories("dinner", getDateRangeForLabel(label, sortedData)) }
        val otherData =
            labels.map { label -> sumCalories("other", getDateRangeForLabel(label, sortedData)) }

        // Mengembalikan ChartData dengan tipe data yang tepat
        return ChartData(labels, breakfastData, lunchData, dinnerData, otherData)
    }

    // Fungsi untuk mengelompokkan data per minggu jika lebih dari 7 hari
    fun groupDataByWeek(sortedData: List<Date>): List<List<Date>> {
        val weeks = mutableListOf<List<Date>>()
        var currentWeek = mutableListOf<Date>()

        var currentWeekStart = sortedData.first().toWeekStart()

        for (date in sortedData) {
            if (date.isSameWeekAs(currentWeekStart)) {
                currentWeek.add(date)
            } else {
                weeks.add(currentWeek)
                currentWeek = mutableListOf(date)
                currentWeekStart = date.toWeekStart() // Update minggu baru
            }
        }

        // Menambahkan minggu terakhir yang belum ditambahkan
        if (currentWeek.isNotEmpty()) {
            weeks.add(currentWeek)
        }

        return weeks
    }

    // Fungsi untuk mendapatkan rentang tanggal berdasarkan label minggu atau tanggal
    fun getDateRangeForLabel(label: String, sortedData: List<Date>): List<Date> {
        return if (label.startsWith("Minggu")) {
            // Jika label adalah minggu, ambil data untuk minggu tersebut
            val weekNumber = label.split(" ")[1].toInt()
            val weekStart = sortedData.first {
                it.toWeekNumber() == weekNumber && it.toWeekStart().year == sortedData.first()
                    .toWeekStart().year
            }
            val weekEnd = sortedData.last {
                it.toWeekNumber() == weekNumber && it.toWeekStart().year == sortedData.first()
                    .toWeekStart().year
            }

            // Menghasilkan rentang tanggal dari minggu tersebut
            getDatesInRange(weekStart, weekEnd)
        } else {
            // Jika label adalah tanggal, hanya ambil data untuk tanggal tersebut
            sortedData.filter { it.toDayLabel() == label }
        }
    }

    // Fungsi untuk mendapatkan rentang tanggal dari mulai hingga akhir minggu
    fun getDatesInRange(startDate: Date, endDate: Date): List<Date> {
        val calendar = Calendar.getInstance(
//            TimeZone.getTimeZone("GMT+8")
        )
        calendar.time = startDate
        val startDayOfWeek = calendar[Calendar.DAY_OF_WEEK]
        val datesInRange = mutableListOf<Date>()

        while (calendar.time <= endDate) {
            datesInRange.add(calendar.time)
            calendar.add(Calendar.DAY_OF_YEAR, 1) // Lanjut ke hari berikutnya
        }

        return datesInRange
    }

    // Fungsi untuk menentukan awal minggu dari sebuah tanggal
    fun Date.toWeekStart(): Date {
        val calendar = Calendar.getInstance(
//            TimeZone.getTimeZone("GMT+8")
        ) // Menggunakan UTC+8
        calendar.time = this
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY) // Set ke hari Senin minggu itu
        return calendar.time
    }

    // Fungsi untuk mengecek apakah dua tanggal berada dalam minggu yang sama
    fun Date.isSameWeekAs(other: Date): Boolean {
        // Pastikan tahun juga diperhitungkan saat membandingkan minggu
        return this.toWeekStart().year == other.toWeekStart().year &&
                this.toWeekStart().toWeekNumber() == other.toWeekStart().toWeekNumber()
    }

    // Fungsi untuk mendapatkan nomor minggu dari tanggal
    fun Date.toWeekNumber(): Int {
        val calendar = Calendar.getInstance(
//            TimeZone.getTimeZone("GMT+8")
        ) // Menggunakan UTC+8
        calendar.time = this
        return calendar.get(Calendar.WEEK_OF_YEAR)
    }

    // Extension function to convert the string to Date (with UTC+8 timezone)
    fun String.toDate(): Date? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
//        inputFormat.timeZone = TimeZone.getTimeZone("GMT+8") // Set timezone to UTC+8

        return try {
            inputFormat.parse(this)
        } catch (e: Exception) {
            null
        }
    }

    // Extension function to format Date into a label like "Sen 01"
    fun Date.toDayLabel(): String {
        val outputFormat = SimpleDateFormat("EEE dd", Locale("id")) // "Sen 01"
        return outputFormat.format(this)
    }


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
    val labels: List<String>,
    val breakfastData: List<Float>,
    val lunchData: List<Float>,
    val dinnerData: List<Float>,
    val snackData: List<Float>
)
