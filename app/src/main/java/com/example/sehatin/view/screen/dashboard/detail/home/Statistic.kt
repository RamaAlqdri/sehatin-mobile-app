package com.example.sehatin.view.screen.dashboard.detail.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.compose.back
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.navigation.SehatInSurface
import com.example.sehatin.view.components.CaloriesBarChart
import com.example.sehatin.view.components.CustomTopAppBar
import com.example.sehatin.view.components.FilterBar
import com.example.sehatin.view.components.FoodHistory
import com.example.sehatin.view.components.KaloriSummary
import com.example.sehatin.view.components.MealLegend
import com.example.sehatin.view.components.MealLegendItem
import com.example.sehatin.viewmodel.ChartData
import com.example.sehatin.viewmodel.DashboardViewModel

@Composable
fun Statistic(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    dashboardViewModel: DashboardViewModel,
) {
    StatisticDetail(
        modifier = modifier,
        onBackClick = onBackClick,
        dashboardViewModel = dashboardViewModel
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StatisticDetail(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    dashboardViewModel: DashboardViewModel,
) {

    val state = rememberPullToRefreshState(

    )
    val isRefreshing by dashboardViewModel.isRefreshing.collectAsStateWithLifecycle()

    val selectedSummaryOption by dashboardViewModel.selectedSummaryOption.collectAsStateWithLifecycle()

    val selectedLabel = when (selectedSummaryOption.type) {
        "day" -> "H"
        "7days" -> "M"
        "30days" -> "B"
        "range" -> "R"
        else -> "H"
    }

    val summaryState by dashboardViewModel.summaryState.collectAsStateWithLifecycle()
    val isLoading = summaryState is ResultResponse.Loading


    val summaryData by dashboardViewModel.summary.collectAsStateWithLifecycle()
    Log.e("SummaryData", "Summary Data: $summaryData")


//
//    PullToRefreshBox(
//        isRefreshing = isRefreshing,
//        onRefresh = {
//            dashboardViewModel.refresh()
//        },
//        state = state,
//        indicator = {
//            Indicator(
//                modifier = Modifier.align(Alignment.TopCenter),
//                isRefreshing = isRefreshing,
//                containerColor = MaterialTheme.colorScheme.primaryContainer,
//                color = MaterialTheme.colorScheme.onPrimaryContainer,
//                state = state,
//                threshold = 120.dp
//            )
//        }
//    ) {
    SehatInSurface(
        modifier = modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .background(back),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CustomTopAppBar(
                    title = "Statistik",
                    showNavigationIcon = true,
                    onBackClick = onBackClick // 👈 Ini penting!
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {

                    item {
                        Spacer(modifier = Modifier.height(14.dp))
                        FilterBar(
                            selectedLabel = selectedLabel,
                            onSelect = { label ->
                                val mode = when (label) {
                                    "H" -> "day"
                                    "M" -> "7days"
                                    "B" -> "30days"
                                    else -> "day"
                                }
                                dashboardViewModel.setSelectedSummaryOption(mode)
//                                dashboardViewModel.getSummary(forceRefresh = true)
                            },
                            onDateRangeSelected = { startStr, endStr ->

                                if (true) {
                                    dashboardViewModel.setSelectedSummaryOption("range")
                                    dashboardViewModel.setSelectedStartSummaryDate(startStr)
                                    dashboardViewModel.setSelectedEndSummaryDate(endStr)
                                    dashboardViewModel.getSummary(forceRefresh = true)
                                }
                            }

                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        if (summaryData != null) {
                            summaryData?.let {
                                Column(
                                    modifier = Modifier
                                        .padding(horizontal = 21.dp)
                                        .shadow(elevation = 2.5.dp, RoundedCornerShape(16.dp))
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(Color.White)
                                        .padding(20.dp)
                                ) {
                                    // --- Kalori Summary
                                    KaloriSummary(
                                        totalKalori = it.data.totalCalories.toInt(),
                                        rataRata = it.data.averageCalories.toInt(),
                                        target = it.data.totalTargetCalories.toInt(),
                                        isLoading = isLoading
                                    )

                                    // Generate the chart data
//                                    val chartData = dashboardViewModel.generateChartData(it)

//                                    Log.e("ChartData", "Chart Data: $chartData")

                                    val groupedCalories = summaryData?.data?.groupedCalories

                                    val labels = groupedCalories?.keys?.sortedWith(compareBy {
                                        val weekMatch = Regex("minggu (\\d+)").find(it)
                                        weekMatch?.groupValues?.get(1)?.toIntOrNull() ?: it.split(" ")[1].toIntOrNull() ?: 0
                                    })

                                    val breakfastData = labels?.map { label -> groupedCalories[label]?.breakfast?.toFloat() ?: 0f }
                                    val lunchData = labels?.map { label -> groupedCalories[label]?.lunch?.toFloat() ?: 0f }
                                    val dinnerData = labels?.map { label -> groupedCalories[label]?.dinner?.toFloat() ?: 0f }
                                    val snackData = labels?.map { label -> groupedCalories[label]?.other?.toFloat() ?: 0f }

                                    val chartData = ChartData(
                                        labels = labels,
                                        breakfastData = breakfastData,
                                        lunchData = lunchData,
                                        dinnerData = dinnerData,
                                        snackData = snackData
                                    )
                                    // --- Chart Bar (Kalori per hari)
                                    CaloriesBarChart(
                                        labels = chartData.labels,
                                        breakfastData = chartData.breakfastData,
                                        lunchData = chartData.lunchData,
                                        dinnerData = chartData.dinnerData,
                                        snackData = chartData.snackData,
                                        isLoading = isLoading
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // --- Legenda
                                    // --- Meal Legend with dynamic data from API
                                    MealLegend(
                                        legendItems = listOf(
                                            MealLegendItem(
                                                "Makan Pagi",
                                                Color(0xFFE8C37B),
                                                it.data.caloriesPerMealType.breakfast.toInt()
                                            ),
                                            MealLegendItem(
                                                "Makan Siang",
                                                Color(0xFF7FB2D9),
                                                it.data.caloriesPerMealType.lunch.toInt()
                                            ),
                                            MealLegendItem(
                                                "Makan Malam",
                                                Color(0xFFD26466),
                                                it.data.caloriesPerMealType.dinner.toInt()
                                            ),
                                            MealLegendItem(
                                                "Camilan/Lainnya",
                                                Color(0xFF7D5BA6),

                                                it.data.caloriesPerMealType.other.toInt()
                                            )
                                        ),
                                        isLoading = isLoading
                                    )
                                }
                            }


                        }

                    }
                    item {
                        // --- Konsumsi Harian
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(
                            modifier = Modifier
//                                    .fillMaxWidth()
                                .padding(horizontal = 21.dp)
                                .shadow(elevation = 2.5.dp, RoundedCornerShape(16.dp))
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White)
                                .padding(20.dp)
                        ) {

                            FoodHistory(
                                items = summaryData?.data?.foodHistory?.map { f -> f.food.name to f.calories.toInt() }
                                    ?: emptyList(),
                                total = summaryData?.data?.totalCalories?.toInt() ?: 0,
                                isLoading = isLoading
                            )
                        }
                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )
                    }


                }


            }

        }
    }


//    }
}

