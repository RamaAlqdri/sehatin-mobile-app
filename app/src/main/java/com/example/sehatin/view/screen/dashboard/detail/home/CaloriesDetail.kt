package com.example.sehatin.view.screen.dashboard.detail.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.compose.back
import com.example.compose.ter
import com.example.sehatin.common.CaloriesConsumptionItem
import com.example.sehatin.common.FakeData
import com.example.sehatin.navigation.SehatInSurface
import com.example.sehatin.view.components.CaloriesProgress
import com.example.sehatin.view.components.CustomTopAppBar

@Composable
fun CaloriesDetail(
    modifier: Modifier = Modifier
) {
    //Call API Here
    CaloriesDetail(
        modifier = modifier,
        id = 1
    )
}

@Composable
private fun CaloriesDetail(
    modifier: Modifier = Modifier,
    id: Int = 0
) {

    var caloriesValue by remember { mutableIntStateOf(1500) }
    val maxCaloriesValue by remember { mutableIntStateOf(3000) }

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
                    title = "Calories",
                    showNavigationIcon = true
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {

                    item {
                        Spacer(modifier = Modifier.height(14.dp))
                        CalendarSection(
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        CaloriesProgress(
                            currentCalories = caloriesValue,
                            maxCaloriesValue = maxCaloriesValue,
                            onAdd = {
                                caloriesValue += 100
                            }
                        )
                    }

                    item{
                        Spacer(modifier = Modifier.height(16.dp))
                        CaloriesConsumptionHistory()
                    }
                }
            }
        }
    }
}

@Composable
fun CaloriesConsumptionHistory(
    modifier: Modifier = Modifier,
) {
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
        FakeData.CaloriesHistory.forEach { item ->
            CaloriesConsumptionRow(item)
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