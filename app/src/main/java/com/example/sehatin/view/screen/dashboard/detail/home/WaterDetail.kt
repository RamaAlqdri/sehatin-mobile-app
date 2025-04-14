package com.example.sehatin.view.screen.dashboard.detail.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.sehatin.common.FakeData
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.common.WaterConsumptionItem
import com.example.sehatin.navigation.SehatInSurface
import com.example.sehatin.utils.convertToHoursAndMinutes
import com.example.sehatin.view.components.CustomTopAppBar
import com.example.sehatin.view.components.WaterIntake
import com.example.sehatin.view.screen.dashboard.home.DashboardViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun WaterDetail(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    dashboardViewModel: DashboardViewModel
) {

    WaterDetail(
        modifier = modifier,
        id = 0,
        onBackClick = onBackClick,
        dashboardViewModel = dashboardViewModel
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WaterDetail(
    modifier: Modifier = Modifier,
    id: Int = 0,
    onBackClick: () -> Unit, // 👈 Tambahkan ini
    dashboardViewModel: DashboardViewModel
) {


    var selectedValue by remember { mutableIntStateOf(100) }

    val state = rememberPullToRefreshState(

    )
    val isRefreshing by dashboardViewModel.isRefreshing.collectAsStateWithLifecycle()

    val waterDailyState by dashboardViewModel.waterDailyState.collectAsStateWithLifecycle(initialValue = ResultResponse.None)


    val waterDayData by dashboardViewModel.waterDaily.collectAsStateWithLifecycle(initialValue = null)

    val createWaterState by dashboardViewModel.createWaterState.collectAsStateWithLifecycle()

    val selectedDate by dashboardViewModel.selectedDate.collectAsStateWithLifecycle()
    val isTodaySelected = selectedDate.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate() == LocalDate.now()

    LaunchedEffect(createWaterState) {
        if (createWaterState is ResultResponse.Success) {
            dashboardViewModel.refresh()
            dashboardViewModel.resetCreateWaterState()
        }
    }

    // ✅ Tanggal yang dipilih disimpan di sini
//    var selectedDate by remember { mutableStateOf(LocalDate.now()) }


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
                        title = "Water",
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
                            WaterIntake(
                                currentWater = waterDayData?.data?.water?.toInt() ?: 0,
                                onAdd = {
                                    dashboardViewModel.createWaterHistory(selectedValue.toDouble())
                                },
                                onMinus = {
                                    dashboardViewModel.createWaterHistory(-selectedValue.toDouble())
                                },
                                onSelectedChange = {
                                    selectedValue = it
                                },
                                maxWaterValue = waterDayData?.data?.target?.toInt() ?: 0,
                                selectedValue = selectedValue,
                                enableButton = isTodaySelected // 👈 Tambahkan ini
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            WaterConsumptionHistory(
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
fun CalendarSection(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val today = LocalDate.now()
    val last7Days = (today.minusDays(6)..today).toList()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp)
            .shadow(elevation = 2.5.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = selectedDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            last7Days.forEach { date ->
                DateItem(
                    date = date,
                    isSelected = date == selectedDate,
                    onClick = { onDateSelected(date) }
                )
            }
        }
    }
}


@Composable
fun WaterConsumptionHistory(
    modifier: Modifier = Modifier,
    dashboardViewModel: DashboardViewModel
) {
    val waterHistoryState by dashboardViewModel.waterHistoryState.collectAsStateWithLifecycle(initialValue = ResultResponse.None)
    val waterHistoryData by dashboardViewModel.waterHistory.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(waterHistoryState) {
        when (waterHistoryState) {
            is ResultResponse.Loading -> {
                // tampilkan progress bar loading
            }
            is ResultResponse.Error -> {
                // tampilkan error

                Log.e("RESULT", "Error: ${(waterHistoryState as ResultResponse.Error).error}")
            }
            is ResultResponse.Success -> {
                // tampilkan data jika sukses
                Log.d("RESULT", "Success: ${(waterHistoryState as ResultResponse.Success).data}")
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
            text = "Riwayat Konsumsi Air",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
//        FakeData.WaterHistory.forEach { item ->
//            WaterConsumptionRow(item)
//        }
        waterHistoryData?.data?.forEach { waterHistoryItem ->
            WaterConsumptionRow(
                item = WaterConsumptionItem(
                    time = convertToHoursAndMinutes(waterHistoryItem.createdAt) ,
                    amount = "${waterHistoryItem.calories} mL"
                )
            )
        }
    }
}

@Composable
fun WaterConsumptionRow(
    item: WaterConsumptionItem
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
                text = "${item.amount} mL",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun DateItem(date: LocalDate?, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary else ter,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(enabled = date != null, onClick = onClick)
    ) {
        Text(
            text = date?.dayOfMonth?.toString() ?: "",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.primary
        )
    }
}

operator fun LocalDate.rangeTo(other: LocalDate): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()
    var current = this
    while (current <= other) {
        dates.add(current)
        current = current.plusDays(1)
    }
    return dates
}