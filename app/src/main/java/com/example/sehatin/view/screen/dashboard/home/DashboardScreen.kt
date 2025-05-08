package com.example.sehatin.view.screen.dashboard.home


import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.compose.caloriesColor
import com.example.compose.waterColor
import com.example.sehatin.R
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.Detail
import com.example.sehatin.data.model.response.DietProgressResponse
import com.example.sehatin.data.model.response.ScheduleADayResponse
import com.example.sehatin.data.model.response.ScheduleDataItem
import com.example.sehatin.navigation.DetailDestinations
import com.example.sehatin.navigation.SehatInSurface
import com.example.sehatin.utils.convertToHoursAndMinutes
import com.example.sehatin.view.components.CircularProgressBar
import com.example.sehatin.view.components.MealCard
import com.example.sehatin.view.components.WeeklyWeightChartFromInput
import com.example.sehatin.view.components.WeeklyWeightInput
import com.example.sehatin.viewmodel.DashboardViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onSnackClick: (Long, String) -> Unit,
    dashboardViewModel: DashboardViewModel,
    navigateToDetail: (String) -> Unit // 👈 Tambahan
) {
    val vectorImages = listOf(
        R.drawable.ic_male,
        R.drawable.ic_female,
        R.drawable.calories,
        R.drawable.water
    )

    val state = rememberPullToRefreshState(

    )


    var profile by remember { mutableStateOf<Detail?>(null) }
    var workSchedule by remember { mutableStateOf<List<ScheduleDataItem>?>(null) }

    val isRefreshing by dashboardViewModel.isRefreshing.collectAsStateWithLifecycle()
    val weightHistoryState by dashboardViewModel.weightHistoryState.collectAsStateWithLifecycle()
    val dietProgressState by dashboardViewModel.dietProgressState.collectAsStateWithLifecycle(
        initialValue = ResultResponse.None
    )
    val waterADayState by dashboardViewModel.waterADayState.collectAsStateWithLifecycle(
        initialValue = ResultResponse.None
    )
    val caloriesADayState by dashboardViewModel.caloriesADayState.collectAsStateWithLifecycle(
        initialValue = ResultResponse.None
    )

    val scheduleADayState by dashboardViewModel.scheduleADayState.collectAsStateWithLifecycle(
        initialValue = ResultResponse.None
    )

    val dietProgressData by dashboardViewModel.dietProgress.collectAsStateWithLifecycle(initialValue = null)
    val caloriesADay by dashboardViewModel.caloriesADay.collectAsStateWithLifecycle(initialValue = null)
    val waterADay by dashboardViewModel.waterADay.collectAsStateWithLifecycle(initialValue = null)
    val scheduleADay by dashboardViewModel.scheduleADay.collectAsStateWithLifecycle(initialValue = null)
    val weightHistory by dashboardViewModel.weightHistory.collectAsStateWithLifecycle(initialValue = null)
    // Konversi data weightHistory menjadi WeeklyWeightInput
//    val chartData = remember(weightHistory) { mapToWeeklyWeight(weightHistory) }

////     Set up refresh behavior
//    LaunchedEffect(state.isRefreshing) {
//        if (state.isRefreshing) {
//            dashboardViewModel.refresh()
//        }
//    }
//
//    // Update pull-to-refresh state based on ViewModel state
//    LaunchedEffect(isRefreshing) {
//        if (!isRefreshing) {
//            state.endRefresh()
//        }
//    }

    LaunchedEffect(Unit) {
        profile = dashboardViewModel.getUserDetail()
        dashboardViewModel.refresh()
        Log.e("DETAIL", "DashboardScreen: $profile")


        workSchedule = dashboardViewModel.getSavedSchedule()
        Log.e("WORKER SCHEDULE", "DashboardScreen: $workSchedule")

    }
    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            dashboardViewModel.refresh()  // Panggil refresh untuk memperbarui data
        }
    }

    LaunchedEffect(dietProgressState) {
        when (dietProgressState) {
            is ResultResponse.Loading -> {
                Log.e("RESULT", "Loading")

            }

            is ResultResponse.Success -> {
                Log.e("RESULT", "Success")
                Log.e(
                    "RESULT",
                    "Success: ${(dietProgressState as ResultResponse.Success<DietProgressResponse>).data}"
                )
            }

            is ResultResponse.Error -> {
                Log.e("RESULT", "Error: ${(dietProgressState as ResultResponse.Error).error}")
            }

            else -> {}
        }
    }

//    LaunchedEffect(weightHistory) {
//        // Mengupdate chart setiap kali weightHistory berubah
//        if (weightHistory != null) {
//            // Misalnya, Anda bisa memanggil refresh atau tindakan lainnya di sini
//        }
//    }

//    LaunchedEffect(caloriesADayState){
//        when (caloriesADayState) {
//            is ResultResponse.Loading -> {
//                Log.e("RESULT", "Loading")
//
//            }
//
//            is ResultResponse.Success -> {
//                Log.e("RESULT", "Success")
//                Log.e(
//                    "RESULT",
//                    "Success: ${(caloriesADayState as ResultResponse.Success<CaloriesADayResponse>).data}"
//                )
//            }
//
//            is ResultResponse.Error -> {
//                Log.e("RESULT", "Error: ${(caloriesADayState as ResultResponse.Error).error}")
//            }
//
//            else -> {}
//        }
//    }




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
            modifier = modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxSize()
                ) {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween

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
                                    modifier = Modifier
//                                        .weight(1f)
                                        .shadow(elevation = 2.5.dp, RoundedCornerShape(10.dp))
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color.White)
                                        .padding(10.dp)
                                        .clickable {
                                            navigateToDetail(DetailDestinations.STATISTIC_DETAIL_ROUTE)
                                        },

                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.statistic),
                                        contentDescription = "",
                                        tint = Color.Unspecified,
                                        modifier = Modifier.size(32.dp)
                                    )

                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                        }

                        item {
                            Box(
                                modifier = Modifier
//                .border(shape = RoundedCornerShape(16.dp), width = 0.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .clip(shape = RoundedCornerShape(16.dp))
//                .border(width = 0.dp, shape = RoundedCornerShape(16.dp), color = Color.White)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 18.dp)
                                        .fillMaxHeight()
                                ) {

                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxHeight()

                                    ) {

                                        Text(
                                            text = "Kemajuan Diet",
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 12.sp
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(-2.dp)
                                        ) {

                                            Text(
                                                text = dietProgressData?.data?.short_message ?: " ",
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 28.sp
                                            )
                                            Box(
                                                modifier = Modifier.widthIn(max = 200.dp) // Set the maximum width
                                            ) {
                                                Text(
                                                    text = dietProgressData?.data?.desc ?: " ",
                                                    color = MaterialTheme.colorScheme.onPrimary,
                                                    fontWeight = FontWeight.Normal,
                                                    fontSize = 12.sp
                                                )
                                            }
                                        }
                                    }

                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize(),
//                        .background(Color(0xFF8BC34A)), // Warna hijau sebagai background layar
                                        contentAlignment = Alignment.Center
                                    ) {
                                        val persentase = dietProgressData?.data?.persentase ?: 0.99
                                        CircularProgressBar(
                                            percentage = persentase.toFloat(),
                                            size = 110.dp,
                                            strokeWidth = 16.dp,
                                            backgroundColor = Color.White.copy(0.3f), // Warna abu-abu terang
                                            progressColor = Color.White,
                                            textColor = Color.White
                                        )
                                    }

                                }
                            }
                            Spacer(modifier = Modifier.height(22.dp))
                        }



                        item {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()

                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp))
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .height(79.dp)
//                    .width(151.dp)

//                    .fillMaxWidth(0.55f)
                                        .padding(vertical = 16.dp, horizontal = 16.dp)
                                        .clickable {
                                            navigateToDetail(DetailDestinations.CALORIES_DETAIL_ROUTE)
                                        }
                                ) {

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
//                    .shadow(4.dp, shape = RoundedCornerShape(16.dp))
                                            .fillMaxSize()


                                    ) {
                                        Column {
                                            Text(
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color.Black,
                                                text = "Kalori"
                                            )
                                            Row {

                                                Text(
                                                    color = caloriesColor,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    text = caloriesADay?.data?.calories.toString()
                                                        ?: "0"
                                                )
                                                Text(
                                                    color = caloriesColor.copy(alpha = 0.5f),
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    text = "/${caloriesADay?.data?.target.toString() ?: "0"}"
                                                )
                                                Text(
                                                    color = MaterialTheme.colorScheme.onBackground.copy(
                                                        alpha = 0.5f
                                                    ),
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    text = " kcal"
                                                )
                                            }
                                        }
                                        Image(
                                            painter = painterResource(id = vectorImages[2]),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .absoluteOffset(y = 4.dp)
                                                .size(30.dp)
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp))
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                        .height(79.dp)
//                    .width(151.dp)
//                    .fillMaxWidth()
                                        .padding(vertical = 16.dp, horizontal = 16.dp)
                                        .clickable {
                                            navigateToDetail(DetailDestinations.WATER_DETAIL_ROUTE)
                                        }
                                ) {

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
//                    .shadow(4.dp, shape = RoundedCornerShape(16.dp))
                                            .fillMaxSize()


                                    ) {
                                        Column(

                                        ) {
                                            Text(
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color.Black,
                                                text = "Air"
                                            )
                                            Row {

                                                Text(
                                                    color = waterColor,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    text = waterADay?.data?.water.toString() ?: "0"
                                                )
                                                Text(
                                                    color = waterColor.copy(alpha = 0.5f),
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    text = "/${waterADay?.data?.target.toString() ?: "0"}"
                                                )
                                                Text(
                                                    color = MaterialTheme.colorScheme.onBackground.copy(
                                                        alpha = 0.5f
                                                    ),
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    text = " mL"
                                                )
                                            }
                                        }
                                        Image(
                                            painter = painterResource(id = vectorImages[3]),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .absoluteOffset(y = 4.dp)
                                                .size(33.dp)
                                        )
                                    }
                                }


                            }
                            Spacer(modifier = Modifier.height(22.dp))
                        }

                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(elevation = 1.5.dp, shape = RoundedCornerShape(16.dp))
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White)
                                    .padding(20.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    Text(
                                        text = "Berat Anda Saat ini",
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Row(


                                    ) {

                                        Text(
                                            text = "${weightHistory?.data?.get(weightHistory?.data?.lastIndex ?: 0)?.weight.toString() ?: "0"}",
                                            color = MaterialTheme.colorScheme.primary,
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                        Text(
                                            text = " kg",
                                            color = Color(0xFFA5A5A5),
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Normal,
                                        )
                                    }

                                }

//                                Text(
//                                    text = "Perkembangan",
//                                    color = Color(0xFF8A8A8A),
//                                    fontSize = 14.sp,
//                                    fontWeight = FontWeight.Normal,
//                                )

//                                Spacer(modifier = Modifier.height(16.dp))


                                weightHistory?.let {
                                    WeeklyWeightChartFromInput(it.data)
                                }
                            }
                            Spacer(modifier = Modifier.height(22.dp))
                        }
                        item {
                            ScheduleSection(
                                data = scheduleADay,
                                navigateToFoodDetail = navigateToDetail // ⬅️ parameternya harus sesuai
                            )
                            Spacer(modifier = Modifier.height(22.dp))
                        }
                    }
                }
            }
        }
    }
}

fun formattedCurrentDate(): String? {
    val today = LocalDate.now()
    val indonesianLocale = Locale("id", "ID")
    val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", indonesianLocale)
    val formattedDate = today.format(formatter)

    return formattedDate
}

//fun formatPercentageToFloat(percentage: Double): Float {
//    Log.d("Debug", "Raw percentage: $percentage")
//    val decimalValue = percentage / 100.0
//    Log.d("Debug", "Converted to decimal: $decimalValue")
//    return (Math.round(decimalValue * 10.0) / 10.0f)
//}

@Composable
private fun ScheduleSection(
    modifier: Modifier = Modifier,
//    data: List<Meal>,
    data: ScheduleADayResponse? = null,
    navigateToFoodDetail: (String) -> Unit // 👈 Tambahan

) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
//            .height(400.dp)
            .shadow(elevation = 1.5.dp, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(20.dp)
    ) {
        Text(
            text = "Saran Makanan Hari Ini",
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        data?.data?.forEach { meal ->
            MealCard(
                imageUrl = meal.food.image,
                title = meal.food.name,
                time = convertToHoursAndMinutes(meal.scheduledAt),
                calories = meal.food.calories.toDouble(),
                serving_amount = meal.food.serving_amount.toDouble(),
                serving_unit = meal.food.serving_unit,
                onClick = {
                    val foodId = meal.food.id.toString()
                    navigateToFoodDetail(DetailDestinations.foodDetailRouteWithId(foodId))
                }
            )
        }
    }
}