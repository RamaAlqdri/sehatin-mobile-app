package com.example.sehatin.view.screen.dashboard.detail.diet

import SearchBar
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.compose.back
import com.example.sehatin.R
import com.example.sehatin.common.FakeData
import com.example.sehatin.common.FoodItem
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.navigation.DetailDestinations
import com.example.sehatin.navigation.SehatInSurface
import com.example.sehatin.view.components.CustomTopAppBar
import com.example.sehatin.view.components.FoodItemCard
import com.example.sehatin.viewmodel.DietViewModel
import kotlin.unaryMinus

@Composable
fun FoodListDetail(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    dietViewModel: DietViewModel,
    navigateToDetail: (String) -> Unit // 👈 Tambahan
) {

    //Never Pass ViewModel here. Pass value from the viewModel. Semua yang ada di sini anggap dari viewmodel
//    var textState by remember { mutableStateOf("") }
//    var item by remember { mutableStateOf(FakeData.foodItems) }

    FoodListDetail(
        modifier = modifier,
        onBackClick = onBackClick,
        id = 1,
        navigateToDetail = navigateToDetail,
//        item = item,
//        query = textState,
//        onQueryChange = { textState = it },
//        onSearch = {
//            item = FakeData.foodItems.filter { foodItem ->
//                foodItem.name.contains(it, ignoreCase = true)
//            }
//        },
        dietViewModel = dietViewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FoodListDetail(
    modifier: Modifier = Modifier,
    dietViewModel: DietViewModel,
    navigateToDetail: (String) -> Unit, // 👈 Tambahan
//    item: List<FoodItem>,
//    query: String = "",
    onBackClick: () -> Unit,
//    onQueryChange: (String) -> Unit = {},
//    onSearch: (String) -> Unit = {},
    id: Int = 0
) {

//    val foodName by dietViewModel.foodName.collectAsStateWithLifecycle()
    val state = rememberPullToRefreshState(

    )
    val isRefreshing by dietViewModel.isRefreshing.collectAsStateWithLifecycle()

    val foodName by dietViewModel.foodName.collectAsStateWithLifecycle()
    val foodFilterState by dietViewModel.foodFilterState.collectAsStateWithLifecycle()

    val foodData by dietViewModel.foodFilter.collectAsStateWithLifecycle()


//    LaunchedEffect(foodFilterState) {
//        if (foodFilterState is ResultResponse.Success) {
//            dietViewModel.refresh()
//        }
//    }


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
            val focusManager = LocalFocusManager.current
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        focusManager.clearFocus()
                    },
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
                        title = "Daftar Makanan",
                        showNavigationIcon = true,
                        onBackClick = onBackClick // 👈 Ini penting!
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    SearchBar(
                        query = foodName,
                        onQueryChange = { dietViewModel.setFoodName(it) }, // ubah foodName di ViewModel
                        onSearch = { dietViewModel.refresh() }
                    )
                    Spacer(modifier = Modifier.height(25.dp))
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

                            if (foodData?.data != null) {

                                foodData?.data?.forEach { item ->
                                    FoodItemCard(
//                                    food = item,
                                        imageUrl = item.image,
                                        title = item.name,
                                        calories = item.calories,
                                        protein = item.protein,


                                        isTimeVisible = false,
                                        isBorderVisible = false,
                                        backgroundColor = Color(0xFFF3F3F3),
                                        onClick = {
                                            Log.d("Debug", "Meal clicked: ${item.name}")
//                            navigateToDetail(
//                                DetailDestinations.FOOD_DETAIL_ROUTE
//                            )

                                            val foodId = item.id.toString()
                                            navigateToDetail(
                                                DetailDestinations.foodDetailRouteWithId(
                                                    foodId
                                                )
                                            )
                                        },
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                            } else {
                                EmptyItemInfo(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp)
                                        .offset(y = (-50).dp)
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyItemInfo(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.menu_icon),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "OMG!",
            fontSize = 24.sp,
            color = Color(0xFF999999),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "There is no food available for that name",
            fontSize = 12.sp,
            color = Color(0xFF999999),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Let's try another one",
            fontSize = 12.sp,
            color = Color(0xFF999999),
            textAlign = TextAlign.Center
        )
    }
}