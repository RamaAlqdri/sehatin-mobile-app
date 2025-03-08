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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.back
import com.example.sehatin.common.FakeData
import com.example.sehatin.navigation.SehatInSurface
import com.example.sehatin.view.components.CalendarView
import com.example.sehatin.view.components.CustomTopAppBar
import com.example.sehatin.view.screen.dashboard.detail.home.CalendarSection
import com.example.sehatin.view.screen.dashboard.diet.ConsumptionRow

@Composable
fun DietScheduleDetail(
    modifier: Modifier = Modifier
) {
    DietScheduleDetail(
        modifier = modifier,
        id = 0
    )
}

@Composable
private fun DietScheduleDetail(
    modifier: Modifier = Modifier,
    id: Int = 0
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
                    title = "Diet Schedule",
                    showNavigationIcon = true
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {

                    item {
                        CalendarView()
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        DailyScheduleSection()
                    }
                }
            }
        }
    }
}

@Composable
fun DailyScheduleSection(
    modifier: Modifier = Modifier,
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
            text = "Daily Schedule",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        FakeData.ConsumptionHistory.forEach { item ->
            ConsumptionRow(item)
        }
    }
}
