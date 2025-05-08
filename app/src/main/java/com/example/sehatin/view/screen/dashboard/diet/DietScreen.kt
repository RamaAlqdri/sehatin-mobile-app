package com.example.sehatin.view.screen.dashboard.diet

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.compose.back
import com.example.compose.backGlass
import com.example.compose.backValue
import com.example.compose.ter
import com.example.compose.textColor
import com.example.compose.waterGlass
import com.example.sehatin.R
import com.example.sehatin.common.CaloriesConsumptionItem
import com.example.sehatin.common.FakeData
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.Detail
import com.example.sehatin.data.model.response.FoodItem
import com.example.sehatin.data.model.response.ScheduleADayResponse
import com.example.sehatin.data.model.response.ScheduleClosestResponse
import com.example.sehatin.data.model.response.ScheduleDataItem
import com.example.sehatin.navigation.DetailDestinations
import com.example.sehatin.navigation.SehatInSurface
import com.example.sehatin.utils.convertToHoursAndMinutes
import com.example.sehatin.view.components.CalendarView
import com.example.sehatin.view.components.CustomTopAppBar
import com.example.sehatin.view.components.FoodCardItem
import com.example.sehatin.view.components.FoodCategoryCard
import com.example.sehatin.view.components.FoodItemCard
import com.example.sehatin.view.components.WaterDialog
import com.example.sehatin.view.screen.dashboard.home.formattedCurrentDate
import com.example.sehatin.viewmodel.DashboardViewModel
import com.example.sehatin.viewmodel.DietViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietScreen(
    modifier: Modifier = Modifier,
    dietViewModel: DietViewModel,
    navigateToDetail: (String) -> Unit,
    dashboardViewModel: DashboardViewModel
) {
    // CAll API Here
    DietScreen(
        modifier = modifier,
        id = 1,
        dietViewModel = dietViewModel,
        navigateToDetail = navigateToDetail,
        dashboardViewModel = dashboardViewModel

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DietScreen(
    modifier: Modifier = Modifier,
    id: Int = 0,
    dietViewModel: DietViewModel,
    navigateToDetail: (String) -> Unit,
    dashboardViewModel: DashboardViewModel,
) {


    var profile by remember { mutableStateOf<Detail?>(null) }
    var currentWater by remember { mutableIntStateOf(1500) }
    val maxWaterValue by remember { mutableIntStateOf(3000) }
    var selectedValue by remember { mutableIntStateOf(100) }

    val state = rememberPullToRefreshState()
    val isRefreshing by dietViewModel.isRefreshing.collectAsStateWithLifecycle()

    val waterToday by dietViewModel.waterADay.collectAsStateWithLifecycle(initialValue = null)
    val scheduleToday by dietViewModel.scheduleToday.collectAsStateWithLifecycle(initialValue = null)
    val scheduleTomorrow by dietViewModel.scheduleTomorrow.collectAsStateWithLifecycle(initialValue = null)
    val scheduleClosest by dietViewModel.scheduleClosest.collectAsStateWithLifecycle(initialValue = null)


    val completedScheduleState by dietViewModel.completedScheduleState.collectAsStateWithLifecycle()
    val createWaterState by dietViewModel.createWaterState.collectAsStateWithLifecycle()

    val breakFastFoodHistory by dietViewModel.breakfastFoodHistory.collectAsStateWithLifecycle()
    val lunchFoodHistory by dietViewModel.lunchFoodHistory.collectAsStateWithLifecycle()
    val dinnerFoodHistory by dietViewModel.dinnerFoodHistory.collectAsStateWithLifecycle()
    val otherFoodHistory by dietViewModel.otherFoodHistory.collectAsStateWithLifecycle()

//    val sarapanItems = listOf(
//        FoodCardItem("Ikan", 300),
//        FoodCardItem("Roti", 200)
//    )
    val sarapanItems = listOf<FoodCardItem>(
    )
    val makanSiangItems = listOf<FoodCardItem>(
    )
    val makanMalamItems = listOf<FoodCardItem>(
    )
    val camilanItems = listOf<FoodCardItem>(
    )


    Log.e(
        "TAG", "scheduleClose: ${scheduleClosest}"
    )
    Log.e(
        "TAG", "scheduleClose: ${waterToday}"
    )
    LaunchedEffect(Unit) {
        // Melakukan refresh saat pertama kali halaman dibuka
        dietViewModel.refresh()
        profile = dashboardViewModel.getUserDetail()
    }

    LaunchedEffect(createWaterState) {
        if (createWaterState is ResultResponse.Success) {
            dietViewModel.refresh()
            dietViewModel.resetCreateWaterState()
        }


    }

    LaunchedEffect(completedScheduleState) {
        if (completedScheduleState is ResultResponse.Success) {
            dietViewModel.refresh()
            dietViewModel.resetCompletedScheduleState()
        }
    }

    PullToRefreshBox(isRefreshing = isRefreshing, onRefresh = {
        dietViewModel.refresh()
    }, state = state, indicator = {
        Indicator(
            modifier = Modifier.align(Alignment.TopCenter),
            isRefreshing = isRefreshing,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            state = state,
            threshold = 120.dp
        )
    }) {

        SehatInSurface(
            modifier = modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .background(back),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            TabSection(navigateToDetail = navigateToDetail, profile = profile)
                        }

                        item {
                            Spacer(modifier = Modifier.height(14.dp))
                            WaterDropSection(
                                onAdd = {
//                                currentWater += selectedValue
                                    dietViewModel.createWaterHistory(selectedValue.toDouble())
                                },
                                onMinus = {
//                                    currentWater -= selectedValue
                                    dietViewModel.createWaterHistory(-selectedValue.toDouble())
                                },
                                currentWater = waterToday?.data?.water?.toInt() ?: 0,
                                maxWaterValue = waterToday?.data?.target?.toInt() ?: 0,
                                selectedValue = selectedValue,
                                navigateToDetail = navigateToDetail
                            )
                        }



                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            UpcomingScheduleSection(
                                data = scheduleClosest,
                                navigateToDetail = navigateToDetail,
                                dietViewModel = dietViewModel
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            FoodCategoryCard(
                                index = 0,
                                categoryName = "Sarapan",
                                icon = painterResource(id = R.drawable.breakfast), // Example icon
                                items = breakFastFoodHistory?.data ?: listOf(),
                                iconColor = Color(0xFFE8BC1F),
                                onAddItemClick = {
                                    // Handle Add Item functionality here
                                    dietViewModel.setSelectedFoodOption(dietViewModel.foodCategoryOptions.get(0))
                                    navigateToDetail(DetailDestinations.FOOD_LIST_DETAIL_ROUTE)
                                },
                                navigateToDetail = navigateToDetail,
                                dietViewModel = dietViewModel

                            )
                        }
                        item {
//                            Spacer(modifier = Modifier.height(16.dp))
                            FoodCategoryCard(
                                index = 1,
                                categoryName = "Makan Siang",
                                icon = painterResource(id = R.drawable.lunch), // Example icon
                                items = lunchFoodHistory?.data ?: listOf(),
                                iconColor = Color(0xFF63A8CF),
                                onAddItemClick = {
                                    // Handle Add Item functionality here
                                    dietViewModel.setSelectedFoodOption(dietViewModel.foodCategoryOptions.get(1))
                                    navigateToDetail(DetailDestinations.FOOD_LIST_DETAIL_ROUTE)
                                },
                                navigateToDetail = navigateToDetail,
                                dietViewModel = dietViewModel

                            )
                        }
                        item {
//                            Spacer(modifier = Modifier.height(16.dp))
                            FoodCategoryCard(
                                index = 2,
                                categoryName = "Makan Malam",
                                icon = painterResource(id = R.drawable.dinner), // Example icon
                                items = dinnerFoodHistory?.data ?: listOf(),
                                iconColor = Color(0xFFE86F1F),
                                onAddItemClick = {
                                    // Handle Add Item functionality here
                                    dietViewModel.setSelectedFoodOption(dietViewModel.foodCategoryOptions.get(2))
                                    navigateToDetail(DetailDestinations.FOOD_LIST_DETAIL_ROUTE)
                                },
                                navigateToDetail = navigateToDetail,
                                dietViewModel = dietViewModel

                            )
                        }
                        item {
//                            Spacer(modifier = Modifier.height(16.dp))
                            FoodCategoryCard(
                                index = 3,
                                categoryName = "Camilan/Lainnya",
                                icon = painterResource(id = R.drawable.snack), // Example icon
                                items = otherFoodHistory?.data ?: listOf(),
                                iconColor = Color(0xFF7C1C89),
                                onAddItemClick = {
                                    // Handle Add Item functionality here
                                    dietViewModel.setSelectedFoodOption(dietViewModel.foodCategoryOptions.get(3))
                                    navigateToDetail(DetailDestinations.FOOD_LIST_DETAIL_ROUTE)
                                },
                                navigateToDetail = navigateToDetail,
                                dietViewModel = dietViewModel

                            )
                        }



                        item {
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TabSection(
    profile: Detail? = null,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp)

    ) {
        Row(

            verticalAlignment = Alignment.CenterVertically
        ) {
            val genderIcon = when (profile?.gender.toString().lowercase()) {
                "male" -> R.drawable.ic_male
                "female" -> R.drawable.ic_female
                else -> R.drawable.ic_male // default/fallback
            }
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    ), // Warna latar belakang lingkaran
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = genderIcon),
                    contentDescription = null,

                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .offset(y = 5.dp)
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(0.1.dp)
            ) {

                Text(
                    text = "Halo, ${profile?.name}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "${formattedCurrentDate()}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.5f
                    )
                )
            }
        }
        Row(


        )
        {

            Row(
                modifier = Modifier
//                .weight(1f)
                    .shadow(elevation = 2.5.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(16.dp)
                    .clickable {
                        navigateToDetail(DetailDestinations.FOOD_LIST_DETAIL_ROUTE)
                    },

                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.menu_icon),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(20.dp)
                )
//            Spacer(modifier = Modifier.width(8.dp))
//            Text(
//                text = "Menu Makanan",
//                color = textColor,
//                fontSize = 12.sp,
//                fontWeight = FontWeight.Bold
//
//            )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Row(
                modifier = Modifier
//                .weight(1f)
                    .shadow(elevation = 2.5.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(16.dp)
                    .clickable {
                        navigateToDetail(DetailDestinations.DIET_SCHEDULE_DETAIL_ROUTE)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.date_icon),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(20.dp)
                )
//            Spacer(modifier = Modifier.width(8.dp))
//            Text(
//                text = "Jadwal Diet",
//                color = textColor,
//                fontSize = 12.sp,
//                fontWeight = FontWeight.Bold
//            )
            }
        }
    }
}

@Composable
private fun WaterDropSection(
    onAdd: () -> Unit,
    onMinus: () -> Unit,
    currentWater: Int,
    maxWaterValue: Int,
    selectedValue: Int,
    navigateToDetail: (String) -> Unit
) {

    val waterValue = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0xFF63A8CF), fontWeight = FontWeight.Bold)) {
            append("$currentWater")
        }
        withStyle(
            style = SpanStyle(
                color = if (currentWater >= maxWaterValue) Color(0xFF63A8CF) else Color(0xFF63A8CF),
                fontWeight = FontWeight.Bold
            )
        ) {
            append("/$maxWaterValue mL")
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 21.dp)
            .shadow(elevation = 2.5.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(26.dp)
            .clickable {
                navigateToDetail(DetailDestinations.WATER_DETAIL_ROUTE)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        Color(0x4063A8CF), shape = RoundedCornerShape(8.dp)
                    )
                    .border(1.dp, color = Color.Unspecified, shape = RoundedCornerShape(8.dp))
                    .clickable(
                        enabled = currentWater - selectedValue > 0,
                        onClick = onMinus,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.minus_icon),
                    contentDescription = null,
                    tint = Color(0xFF63A8CF),
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(modifier = Modifier.width(70.dp))
            Icon(
                painter = painterResource(
                    id = R.drawable.water
                ),
                contentDescription = "Water Glass",
                tint = Color(0xFF63A8CF),
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(70.dp))
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        Color(0x4063A8CF), shape = RoundedCornerShape(8.dp)
                    )
                    .border(1.dp, color = Color.Unspecified, shape = RoundedCornerShape(8.dp))
                    .clickable(
                        onClick = onAdd,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.plus_icon),
                    contentDescription = null,
                    tint = Color(0xFF63A8CF),
                    modifier = Modifier
                        .size(30.dp)
                        .graphicsLayer(rotationZ = 180f)
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = waterValue, fontSize = 18.sp,
            color = Color(0xFF63A8CF)
        )
    }
}

@Composable
private fun UpcomingScheduleSection(
    modifier: Modifier = Modifier,
    data: ScheduleClosestResponse? = null,
    navigateToDetail: (String) -> Unit,
    dietViewModel: DietViewModel
) {
    Log.e(
        "TAG", "UpcomingScheduleSection: ${data}"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp)
            .shadow(elevation = 2.5.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            text = "Saran Makanan",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
        )

        Spacer(modifier = Modifier.height(16.dp))

        FoodItemCard(
            imageUrl = data?.data?.food?.image ?: "",
            title = data?.data?.food?.name ?: "",
            time = convertToHoursAndMinutes(data?.data?.scheduledAt ?: ""),
            calories = data?.data?.food?.calories ?: 0.0,
            protein = data?.data?.food?.protein ?: 0.0,
            serving_unit = data?.data?.food?.serving_unit ?: "",
            serving_amount = data?.data?.food?.serving_amount ?: 0.0,
            backgroundColor = ter,

            isTimeVisible = true,
            isCompleted = data?.data?.isCompleted ?: false,
            isBorderVisible = true,
            onClick = {
                Log.d("Debug", "Meal clicked: ${data?.data?.food?.name}")
                val foodId = data?.data?.food?.id.toString()
                navigateToDetail(DetailDestinations.foodDetailRouteWithId(foodId))
            },
//            backgroundColor = ter
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (data?.data?.isCompleted == false) {
            ActionButtons(
                onEatClick = { dietViewModel.setCompletedSchedule() },
                onChangeMenuClick = {
                    val scheduleId = data?.data?.id.toString()
                    navigateToDetail(DetailDestinations.foodRecomendationRouteWithId(scheduleId))

                })
        }

    }

}


@Composable
fun ActionButtons(
    onEatClick: () -> Unit, onChangeMenuClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onEatClick,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary), // Warna hijau
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
        ) {
            Text(text = "Makan", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.width(16.dp))

        OutlinedButton(
            onClick = onChangeMenuClick,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
        ) {
            Text(text = "Ganti Menu", fontSize = 16.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun ConsumptionHistory(
    modifier: Modifier = Modifier,
    dataToday: ScheduleADayResponse? = null,
    dataTomorrow: ScheduleADayResponse? = null,
    navigateToDetail: (String) -> Unit,
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
            text = "Jadwal Hari Ini",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        dataToday?.data?.forEach { meal ->
            ConsumptionRow(meal, navigateToDetail = navigateToDetail)
        }

//        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Jadwal Besok",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        dataTomorrow?.data?.forEach { meal ->
            ConsumptionRow(
                meal, navigateToDetail = navigateToDetail
            )
        }
    }
}

@Composable
fun ConsumptionRow(
    item: ScheduleDataItem,
    navigateToDetail: (String) -> Unit,

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
                text = convertToHoursAndMinutes(item.scheduledAt),
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
                .padding(14.dp)
                .clickable {
                    val foodId = item.food.id.toString()
                    navigateToDetail(DetailDestinations.foodDetailRouteWithId(foodId))
                },
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = item.food.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}