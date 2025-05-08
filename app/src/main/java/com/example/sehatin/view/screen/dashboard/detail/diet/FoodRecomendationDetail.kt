package com.example.sehatin.view.screen.dashboard.detail.diet

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.sehatin.common.FakeData
import com.example.sehatin.common.FoodItem
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.DietResponse
import com.example.sehatin.navigation.DetailDestinations
import com.example.sehatin.navigation.SehatInSurface
import com.example.sehatin.view.components.CustomTopAppBar
import com.example.sehatin.view.components.FoodItemCard
import com.example.sehatin.viewmodel.DietViewModel

@Composable
fun FoodRecomendationDetail(
    modifier: Modifier = Modifier,
    scheduleId: String,
    onBackClick: () -> Unit,
    dietViewModel: DietViewModel,
    navigateToDetail: (String) -> Unit // 👈 Tambahan
) {
    FoodRecomendationDetail(
        modifier = modifier,
        id = 1,
        scheduleId = scheduleId,
        onBackClick = onBackClick,
        dietViewModel = dietViewModel,
        navigateToDetail = navigateToDetail // 👈 Tambahan
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FoodRecomendationDetail(
    scheduleId: String,
    modifier: Modifier = Modifier,
    id: Int = 0,
    onBackClick: () -> Unit,
    dietViewModel: DietViewModel,
    navigateToDetail: (String) -> Unit // 👈 Tambahan
) {


    Log.e("TAG", "FoodRecomendationDetail: $scheduleId")
    val state = rememberPullToRefreshState(

    )
    val isRefreshing by dietViewModel.isRefreshing.collectAsStateWithLifecycle()

    val foodRecommendationState by dietViewModel.foodRecommendationState.collectAsStateWithLifecycle()
    val updateScheduleState by dietViewModel.updateFoodScheduleState.collectAsStateWithLifecycle()

    val foodRecommendation by dietViewModel.foodRecommendation.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = updateScheduleState) {
        if (updateScheduleState is ResultResponse.Success) {
            dietViewModel.refresh()
            dietViewModel.resetUpdateFoodScheduleState()
            onBackClick()
        }
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
                        title = "Rekomendasi Makanan",
                        showNavigationIcon = true,
                        onBackClick = onBackClick // 👈 Ini penting!
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 21.dp)
                            .shadow(elevation = 1.dp, RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .padding(horizontal = 18.dp, vertical = 20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            foodRecommendation?.data?.forEach { food ->
                                ItemListSection(
                                    item = food,
                                    onFoodClick = {
                                        val foodId = food.id.toString()
                                        Log.e("TAG", "FoodRecomendationDetail Food Id: $foodId")
                                        navigateToDetail(
                                            DetailDestinations.foodDetailRouteWithId(
                                                foodId
                                            )
                                        )
                                    },
                                    onChoose = {
                                        dietViewModel.updateFoodSchedule(
                                            scheduleId = scheduleId,
                                            foodId = food.id.toString()
                                        )
//                                        dietViewModel.refresh()
//                                        onBackClick


                                    }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemListSection(
    modifier: Modifier = Modifier,
    item: DietResponse.FoodItem,
    onFoodClick: () -> Unit,
    onChoose: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min), // ⬅️ penting untuk membuat fillMaxHeight bekerja
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FoodItemCard(
            imageUrl = item.image,
            title = item.name,
            calories = item.calories,
            protein = item.protein,
            serving_amount = item.serving_amount,
            serving_unit = item.serving_unit,
            isTimeVisible = false,
            isBorderVisible = false,
            backgroundColor = Color(0xFFF3F3F3),
            modifier = Modifier
                 .weight(1f),
            onClick = onFoodClick
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .fillMaxHeight() // ⬅️ sekarang ini akan mengisi tinggi dari Row
                .width(68.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(MaterialTheme.colorScheme.primary)
                .clickable { onChoose() }
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Pilih",
                fontSize = 12.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
        }
    }

}