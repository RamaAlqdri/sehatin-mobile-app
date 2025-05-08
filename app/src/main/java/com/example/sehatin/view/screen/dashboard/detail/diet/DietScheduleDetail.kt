package com.example.sehatin.view.screen.dashboard.detail.diet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.ScheduleDataItem
import com.example.sehatin.navigation.SehatInSurface
import com.example.sehatin.view.components.CalendarView
import com.example.sehatin.view.components.CustomTopAppBar
import com.example.sehatin.view.screen.dashboard.diet.ConsumptionRow
import com.example.sehatin.viewmodel.DietViewModel
import java.util.Date

@Composable
fun DietScheduleDetail(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    dietViewModel: DietViewModel,
    navigateToDetail: (String) -> Unit
) {
    DietScheduleDetail(
        modifier = modifier,
        onBackClick = onBackClick,
        id = 0,
        dietViewModel = dietViewModel,
        navigateToDetail = navigateToDetail
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DietScheduleDetail(
    modifier: Modifier = Modifier,

    onBackClick: () -> Unit,
    id: Int = 0,
    dietViewModel: DietViewModel,
    navigateToDetail: (String) -> Unit
) {

    val state = rememberPullToRefreshState(

    )
    val isRefreshing by dietViewModel.isRefreshing.collectAsStateWithLifecycle()

    val scheduleDailyState by dietViewModel.scheduleDailyState.collectAsStateWithLifecycle()
    val scheduleDailyData = dietViewModel.scheduleDaily.collectAsStateWithLifecycle()
    val selectedDate by dietViewModel.selectedDate.collectAsStateWithLifecycle()

//    LaunchedEffect(scheduleDailyState) {
//
//        if (scheduleDailyState is ResultResponse.Success){
//            dietViewModel.refresh()
//            dietViewModel.resetScheduleDailyState()
//        }
//    }

    LaunchedEffect(selectedDate){
        dietViewModel.refresh()


    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            dietViewModel.refresh()
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
                        title = "Kalender",
                        showNavigationIcon = true,
                        onBackClick = onBackClick

                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        item {
                            CalendarView(
                                selectedDate = selectedDate,
                                OnDateSelected = { date->
                                    dietViewModel.setSelectedDate(date)
                                },
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            DailyScheduleSection(
                                navigateToDetail = navigateToDetail,
                                scheduleItem = scheduleDailyData?.value?.data,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DailyScheduleSection(
    modifier: Modifier = Modifier,
    scheduleItem: List<ScheduleDataItem>?,
    navigateToDetail: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp)
            .shadow(elevation = 1.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 21.dp, vertical = 12.dp),
    ) {
        Text(
            text = "Saran Makanan",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
//        FakeData.ConsumptionHistory.forEach { item ->
//            ConsumptionRow(item)
//        }
        scheduleItem?.forEach { item ->
            ConsumptionRow(
                item = item, navigateToDetail = navigateToDetail
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
