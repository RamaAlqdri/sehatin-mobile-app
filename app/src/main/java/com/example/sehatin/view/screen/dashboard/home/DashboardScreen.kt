package com.example.sehatin.view.screen.dashboard.home


import android.content.ClipData.Item
import android.util.Log
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.caloriesColor
import com.example.compose.waterColor
import com.example.sehatin.R
import com.example.sehatin.common.FakeData
import com.example.sehatin.common.Meal
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.CaloriesADayResponse
import com.example.sehatin.data.model.response.Detail
import com.example.sehatin.data.model.response.DietProgressResponse
import com.example.sehatin.data.model.response.ScheduleADayResponse
import com.example.sehatin.data.resource.Resource
import com.example.sehatin.data.store.DataStoreManager
import com.example.sehatin.di.factory.DashboardViewModelFactory
import com.example.sehatin.di.factory.RegisterViewModelFactory
import com.example.sehatin.navigation.DetailDestinations
import com.example.sehatin.navigation.SehatInSurface
import com.example.sehatin.utils.convertToHoursAndMinutes
import com.example.sehatin.view.components.CircularProgressBar
import com.example.sehatin.view.components.MealCard
import com.example.sehatin.view.components.RefreshIndicator
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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

    val isRefreshing by dashboardViewModel.isRefreshing.collectAsStateWithLifecycle()
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
        Log.e("DETAIL", "DashboardScreen: $profile")
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
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(
                                            color = Color(0x2370A44C),
                                        )
                                ) {
                                    Image(
                                        painter = painterResource(id = vectorImages[0]),
                                        contentDescription = null,

                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(RoundedCornerShape(50.dp))
                                    )
                                }
                                Spacer(modifier = Modifier.width(15.dp))
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(0.1.dp)
                                ) {

                                    Text(
                                        text = "Halo, ${profile?.name}",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 15.sp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                    )
                                    Text(
                                        text = "${formattedCurrentDate()}",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
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
                                                text = "${dietProgressData?.data?.short_message ?: " "}",
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 28.sp
                                            )
                                            Text(
                                                text = "${dietProgressData?.data?.desc ?: " " }",
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                fontWeight = FontWeight.Normal,
                                                fontSize = 12.sp
                                            )
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
                                                    text = caloriesADay?.data?.calories.toString() ?:"0"
                                                )
                                                Text(
                                                    color = caloriesColor.copy(alpha = 0.5f),
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    text = "/${caloriesADay?.data?.target.toString() ?:"0"}"
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
                                                    text = waterADay?.data?.water.toString() ?:"0"
                                                )
                                                Text(
                                                    color = waterColor.copy(alpha = 0.5f),
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    text = "/${waterADay?.data?.target.toString() ?:"0"}"
                                                )
                                                Text(
                                                    color = MaterialTheme.colorScheme.onBackground.copy(
                                                        alpha = 0.5f
                                                    ),
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    text = " ml"
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
                            ScheduleSection(
//                                data = FakeData.meals
                                data = scheduleADay
                            )
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
    data: ScheduleADayResponse? = null

) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .shadow(elevation = 1.5.dp, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(20.dp)
    ) {
        Text(
            text = "Jadwal Terdekat",
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            if (data != null) {
                items(data.data) { meal ->
                    MealCard(
                        //MASIH PAKE DUMMY KARENA APINYA TIDAK PROVIDE DATA
                        imageUrl = meal.food.image,
                        title = meal.food.name,
                        time = convertToHoursAndMinutes(meal.scheduledAt),
                        calories = meal.food.calories.toDouble(),
                    )
                }
            }
        }
    }
}