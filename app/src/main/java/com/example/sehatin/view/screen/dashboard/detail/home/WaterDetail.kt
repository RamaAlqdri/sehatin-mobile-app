package com.example.sehatin.view.screen.dashboard.detail.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.compose.back
import com.example.compose.backGlass
import com.example.compose.ter
import com.example.compose.waterGlass
import com.example.sehatin.R
import com.example.sehatin.common.CaloriesConsumptionItem
import com.example.sehatin.common.FakeData
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.common.WaterConsumptionItem
import com.example.sehatin.navigation.SehatInSurface
import com.example.sehatin.utils.convertToHoursAndMinutes
import com.example.sehatin.utils.convertToHoursAndMinutesWater
import com.example.sehatin.view.components.CustomTopAppBar
import com.example.sehatin.view.components.WaterIntake
import com.example.sehatin.viewmodel.DashboardViewModel
import com.valentinilk.shimmer.shimmer
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

    val waterDailyState by dashboardViewModel.waterDailyState.collectAsStateWithLifecycle(
        initialValue = ResultResponse.None
    )


    val waterDayData by dashboardViewModel.waterDaily.collectAsStateWithLifecycle(initialValue = null)

    val createWaterState by dashboardViewModel.createWaterState.collectAsStateWithLifecycle()
    val deleteLatestWaterState by dashboardViewModel.deleteLatestWaterState.collectAsStateWithLifecycle()
    val deleteWaterbyIdState by dashboardViewModel.deleteWaterbyIdState.collectAsStateWithLifecycle()

    val selectedDate by dashboardViewModel.selectedDate.collectAsStateWithLifecycle()
    val isTodaySelected = selectedDate.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate() == LocalDate.now()

    LaunchedEffect(Unit) {
        // Melakukan refresh saat pertama kali halaman dibuka
        dashboardViewModel.refresh()
    }

    LaunchedEffect(createWaterState) {
        if (createWaterState is ResultResponse.Success) {
            dashboardViewModel.refresh()
            dashboardViewModel.resetCreateWaterState()
        }
    }
    LaunchedEffect(deleteLatestWaterState) {
        if (deleteLatestWaterState is ResultResponse.Success) {
            dashboardViewModel.refresh()
            dashboardViewModel.resetDeleteLatestWaterState()
        }


    }
    LaunchedEffect(deleteWaterbyIdState) {
        if (deleteWaterbyIdState is ResultResponse.Success) {
            dashboardViewModel.refresh()
            dashboardViewModel.resetDeleteWaterByIdState()
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
                                selectedDate = selectedDate,
                                onDateSelected = { date ->
                                    // Convert the new LocalDate back to Date if needed

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
                                    dashboardViewModel.deleteLatestWaterHistory()
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
    selectedDate: Date,
    onDateSelected: (Date) -> Unit
) {
    val today = LocalDate.now()
    val last7Days = (today.minusDays(6)..today).toList()

    val selectedLocalDate = remember(selectedDate) {
        selectedDate.toInstant()
            .atZone(ZoneId.of("UTC+8"))
            .toLocalDate()
    }
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
            text = selectedDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
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
                    isSelected = date == selectedLocalDate,
                    onClick = {
                        val zoneId = ZoneId.of("UTC+8")
                        val zonedDateTime = date.atTime(12, 0).atZone(zoneId) // <- set jam 12 siang
                        val instant = zonedDateTime.toInstant()
                        Log.e(
                            "DateItem",
                            "date: $date, selectedDate: $selectedLocalDate"
                        )
                        onDateSelected(Date.from(instant))
                    }
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
    val waterHistoryState by dashboardViewModel.waterHistoryState.collectAsStateWithLifecycle(
        initialValue = ResultResponse.None
    )
    val isLoading = waterHistoryState is ResultResponse.Loading
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
        if (isLoading) {
            // tampilkan progress bar loading
            repeat(5) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .shimmer()
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(
                                Color(0xFF3E7136).copy(alpha = 0.4f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {}
                }
            }
        } else {
            waterHistoryData?.data?.forEach { waterHistoryItem ->
                WaterConsumptionRow(
                    item = WaterConsumptionItem(
                        id = waterHistoryItem.id,
                        time = convertToHoursAndMinutesWater(waterHistoryItem.createdAt),
                        amount = "${waterHistoryItem.water}"
                    ),
                    onDelete = {
                        dashboardViewModel.deleteWaterByIdHistory(waterHistoryItem.id)
                    }
                )
            }
            if (waterHistoryData?.data?.isEmpty() == true) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(bottom = 9.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "Tidak ada data Air",
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }

        }
    }
}

@Composable
fun WaterConsumptionRow(
    item: WaterConsumptionItem,
    onDelete: () -> Unit
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
//                .width(150.dp)
                .weight(1f)
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
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(
                    MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(1.dp, color = Color.Unspecified, shape = RoundedCornerShape(8.dp))
                .clickable(
                    onClick =
                        onDelete,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .size(24.dp)
//                    .graphicsLayer(rotationZ = 180f)
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