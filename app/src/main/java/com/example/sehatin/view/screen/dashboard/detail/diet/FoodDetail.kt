package com.example.sehatin.view.screen.dashboard.detail.diet

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.back
import com.example.compose.ter
import com.example.compose.textColor
import com.example.sehatin.R
import com.example.sehatin.common.FakeData
import com.example.sehatin.common.FoodDetail
import com.example.sehatin.navigation.SehatInSurface
import com.example.sehatin.view.components.CustomTopAppBar
import kotlinx.coroutines.launch


@Composable
fun FoodDetail(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    FoodDetail(
        onBackClick = {

        },
        item = FakeData.foods[0]
    )
}

@Composable
private fun FoodDetail(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    item: FoodDetail
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
                    .background(back),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.telor),
                        contentDescription = "Food Image",
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )

                    CustomTopAppBar(
                        title = "",
                        showNavigationIcon = true,
                        modifier = Modifier
                            .statusBarsPadding()
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
                                        onVerticalDrag = { change, dragAmount ->
                                            sheetHeight = (sheetHeight - dragAmount.dp).coerceIn(
                                                sheetNormalHeight,
                                                sheetMaxHeight
                                            )
                                        },
                                        onDragEnd = {
                                            sheetHeight =
                                                if (sheetHeight > (sheetNormalHeight + sheetMaxHeight) / 2) {
                                                    sheetMaxHeight
                                                } else {
                                                    sheetNormalHeight
                                                }
                                        }
                                    )
                                }
                            }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 21.dp, vertical = 16.dp)
                                .verticalScroll(
                                    if (sheetHeight == sheetMaxHeight) scrollState else rememberScrollState(
                                        0
                                    )
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Box(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(6.dp)
                                    .background(back, shape = RoundedCornerShape(3.dp))
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = item.name,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                item.listDetail.forEach {
                                    NutritionInfo(
                                        units = it.units,
                                        weight = it.weight,
                                        position = item.listDetail.indexOf(it)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = item.description ,
                                fontSize = 13.sp,
                                color = textColor
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
    )
    {
        Box(
            modifier = Modifier
                .size(25.dp)
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
                modifier = Modifier.size(15.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                text = weight.toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
            Text(
                text = units,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.absoluteOffset(x = 0.dp, y = (-9).dp)
            )
        }
    }
}

@Composable
fun getScreenHeightDp(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenHeightDp
}