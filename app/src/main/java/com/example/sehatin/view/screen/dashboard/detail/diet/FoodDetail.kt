package com.example.sehatin.view.screen.dashboard.detail.diet

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.flowlayout.FlowRow
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.compose.back
import com.example.compose.ter
import com.example.compose.textColor
import com.example.sehatin.R
import com.example.sehatin.common.ResultResponse
import com.example.sehatin.data.model.response.FoodDetailResponse
import com.example.sehatin.navigation.SehatInSurface
import com.example.sehatin.view.components.CustomTopAppBar
import com.example.sehatin.view.screen.dashboard.home.DashboardViewModel
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import kotlinx.coroutines.launch


@Composable
fun FoodDetail(
    foodId: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    dashboardViewModel: DashboardViewModel
) {

    Log.e("FoodDetail", "FoodDetail: $foodId")

    LaunchedEffect(foodId) {
        dashboardViewModel.getFoodDetail(foodId)
    }

    val foodState by dashboardViewModel.foodDetailState.collectAsStateWithLifecycle(initialValue = null)

    when (val state = foodState) {
        is ResultResponse.Loading -> {
            Log.e("RESULT", "Loading")
        }

        is ResultResponse.Success -> {
            FoodDetailContent(
                item = state.data,
                onBackClick = onBackClick
            )
        }

        is ResultResponse.Error -> {
            Log.e("RESULT", "Error: ${state.error}")
        }

        else -> {

        }
    }

//    FoodDetail(
//        onBackClick = onBackClick,
//        item = FakeData.foods[0]
//    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FoodDetailContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    item: FoodDetailResponse
) {
    val sheetMaxHeight = (getScreenHeightDp() - 100).dp
    val sheetNormalHeight = (getScreenHeightDp() - 300).dp
    var sheetHeight by remember { mutableStateOf(sheetNormalHeight) }
    val animatedSheetHeight by animateDpAsState(
        targetValue = sheetHeight,
        animationSpec = tween(durationMillis = 300),
        label = "sheetAnimation"
    )

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    SehatInSurface(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(back),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
//                    Image(
//                        painter = painterResource(id = R.drawable.telor), // Ganti jika ingin pakai `item.data.image` dengan `rememberAsyncImagePainter`
//                        contentDescription = "Food Image",
//                        modifier = Modifier.fillMaxWidth(),
//                        contentScale = ContentScale.Crop
//                    )
                    AsyncImage(
                        model = item.data.image, // Load image from URL
                        contentDescription = item.data.name,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )

                    CustomTopAppBar(
                        title = "",
                        showNavigationIcon = true,
                        modifier = Modifier.statusBarsPadding(),
                        onBackClick = onBackClick
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(animatedSheetHeight)
                            .align(Alignment.BottomCenter)
                            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                            .background(Color.White)
                            .pointerInput(Unit) {
                                coroutineScope.launch {
                                    detectVerticalDragGestures(
                                        onVerticalDrag = { _, dragAmount ->
                                            sheetHeight = (sheetHeight - dragAmount.dp).coerceIn(
                                                sheetNormalHeight,
                                                sheetMaxHeight
                                            )
                                        },
                                        onDragEnd = {
                                            sheetHeight = if (sheetHeight > (sheetNormalHeight + sheetMaxHeight) / 2)
                                                sheetMaxHeight
                                            else sheetNormalHeight
                                        }
                                    )
                                }
                            }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 21.dp, vertical = 16.dp)
                                .verticalScroll(scrollState),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
//                            Box(
//                                modifier = Modifier
//                                    .width(60.dp)
//                                    .height(6.dp)
//                                    .background(back, shape = RoundedCornerShape(3.dp))
//                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = item.data.name,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Nutritional Info Row
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                val nutritionItems = listOf(
                                    Triple("Kalori", item.data.calories.toInt(), 0),
                                    Triple("Protein", item.data.protein.toInt(), 1),
                                    Triple("Lemak", item.data.fat.toInt(), 2),
                                    Triple("Karbo", item.data.carb.toInt(), 3),
                                    Triple("Serat", item.data.fiber.toInt(), 4)
                                )

                                FlowRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 0.dp),
                                    mainAxisSpacing = 2.dp,
                                    crossAxisSpacing = 10.dp,
                                    mainAxisAlignment = FlowMainAxisAlignment.Center,
                                    crossAxisAlignment = FlowCrossAxisAlignment.Center
                                ) {
                                    nutritionItems.forEach { (label, value, position) ->
                                        NutritionInfo(
                                            units = label,
                                            weight = value,
                                            position = position
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = item.data.description,
                                fontSize = 18.sp,
                                color = textColor,
                                textAlign = TextAlign.Justify, // ⬅️ ini yang bikin teks rata kiri kanan
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NutritionInfo(
    units: String,
    weight: Int = 0,
    position: Int = 0
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 4.dp)
    )
    {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(ter, RoundedCornerShape(50.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = when (position) {
                    0 -> R.drawable.calories
                    1 -> R.drawable.chicken
                    2 -> R.drawable.bubble
                    3 -> R.drawable.sosis
                    else -> R.drawable.bubble
                }),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp) // Adds spacing between items
        ) {
            Text(
                text = weight.toString()+"g",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
            Text(
                text = units,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun getScreenHeightDp(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp
}