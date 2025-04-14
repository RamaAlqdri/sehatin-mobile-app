package com.example.sehatin.view.screen.dashboard.detail.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.compose.back
import com.example.compose.ter
import com.example.sehatin.common.CaloriesConsumptionItem

import com.example.sehatin.common.ResultResponse
import com.example.sehatin.navigation.SehatInSurface
import com.example.sehatin.utils.convertToHoursAndMinutes
import com.example.sehatin.view.components.CaloriesProgress
import com.example.sehatin.view.components.CustomTopAppBar
import com.example.sehatin.view.screen.dashboard.home.DashboardViewModel

import network.chaintech.kmp_date_time_picker.utils.now
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@Composable
fun CaloriesDetail(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    dashboardViewModel: DashboardViewModel
) {
    //Call API Here
    CaloriesDetail(
        modifier = modifier,
        id = 1,
        onBackClick = onBackClick,
        dashboardViewModel = dashboardViewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CaloriesDetail(
    modifier: Modifier = Modifier,
    id: Int = 0,
    onBackClick: () -> Unit, // 👈 Tambahkan ini
    dashboardViewModel: DashboardViewModel
) {


    val state = rememberPullToRefreshState(

    )
    val isRefreshing by dashboardViewModel.isRefreshing.collectAsStateWithLifecycle()



    val caloriesDailyState by dashboardViewModel.caloriesDailyState.collectAsStateWithLifecycle(initialValue = ResultResponse.None)


    val caloriesDayData by dashboardViewModel.caloriesDaily.collectAsStateWithLifecycle(initialValue = null)

    // ✅ Tanggal yang dipilih disimpan di sini
//    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val selectedDate by dashboardViewModel.selectedDate.collectAsStateWithLifecycle()
//
//    LaunchedEffect(Unit) {
////        val localDate = LocalDate.now()
////        val date = Date.from(localDate.atStartOfDay(ZoneId.of("UTC")).toInstant())
////        dashboardViewModel.setSelectedDate(date)
//
//        Log.e(
//            "RESULT",
//            "Selected Date: $selectedDate"
//        )
//    }
    LaunchedEffect(selectedDate) {
        Log.e("RESULT", "Selected Date: $selectedDate")
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            dashboardViewModel.refresh()
        },
        state = state,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                state = state,
                threshold = 120.dp
            )
        }
    ) {
        SehatInSurface(
            modifier = modifier.fillMaxSize(),
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
                        title = "Kalori",
                        showNavigationIcon = true,
                        onBackClick = onBackClick // 👈 Ini penting!
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        item {
                            Spacer(modifier = Modifier.height(14.dp))
                            val localDate = selectedDate.toInstant()
                                .atZone(ZoneId.systemDefault()) // Use the system's default timezone or UTC as per your needs
                                .toLocalDate()
                            CalendarSection(
                                selectedDate = localDate,
                                onDateSelected = { newDate ->
                                    // Convert the new LocalDate back to Date if needed
                                    val date = Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
                                    dashboardViewModel.setSelectedDate(date)
                                }
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            CaloriesProgress(
                                currentCalories = caloriesDayData?.data?.calories?.toInt() ?: 0,
                                maxCaloriesValue = caloriesDayData?.data?.target?.toInt() ?: 0,
//                            onAdd = {
//                                caloriesValue += 100
//                            }
                            )
                        }

                        item{
                            Spacer(modifier = Modifier.height(16.dp))
                            CaloriesConsumptionHistory(
                                dashboardViewModel = dashboardViewModel
                            )
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun CaloriesConsumptionHistory(
    modifier: Modifier = Modifier,
    dashboardViewModel: DashboardViewModel
) {

    val caloriesHistoryState by dashboardViewModel.caloriesHistoryState.collectAsStateWithLifecycle(initialValue = ResultResponse.None)
    val caloriesHistoryData by dashboardViewModel.caloriesHistory.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(caloriesHistoryState) {
        when (caloriesHistoryState) {
            is ResultResponse.Loading -> {
                // tampilkan progress bar loading
            }
            is ResultResponse.Error -> {
                // tampilkan error

                Log.e("RESULT", "Error: ${(caloriesHistoryState as ResultResponse.Error).error}")
            }
            is ResultResponse.Success -> {
                // tampilkan data jika sukses
                Log.d("RESULT", "Success: ${(caloriesHistoryState as ResultResponse.Success).data}")
            }
            else -> {
                // status default
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp)
            .shadow(elevation = 2.5.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 21.dp, vertical = 12.dp),
    ) {
        Text(
            text = "Riwayat Konsumsi Kalori",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
//        FakeData.CaloriesHistory.forEach { item ->
//            CaloriesConsumptionRow(item)
//        }

        caloriesHistoryData?.data?.forEach { caloriesHistoryItem ->
            CaloriesConsumptionRow(
                item = CaloriesConsumptionItem(
                    time = convertToHoursAndMinutes(caloriesHistoryItem.createdAt) ,
                    amount = "${caloriesHistoryItem.calories} kcaL"
                )
            )
        }

    }
}

@Composable
fun CaloriesConsumptionRow(
    item : CaloriesConsumptionItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .width(70.dp)
                .height(50.dp)
                .background(ter, RoundedCornerShape(10.dp))
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.time,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0xFFF3F3F3), RoundedCornerShape(10.dp))
                .padding(14.dp),
            contentAlignment = Alignment.TopStart
        ) {

            Text(
                text = item.amount,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}