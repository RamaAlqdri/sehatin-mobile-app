package com.example.sehatin.view.screen.dashboard.diet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.back
import com.example.compose.backGlass
import com.example.compose.backValue
import com.example.compose.ter
import com.example.compose.textColor
import com.example.compose.waterGlass
import com.example.sehatin.R
import com.example.sehatin.common.CaloriesConsumptionItem
import com.example.sehatin.common.Consumption
import com.example.sehatin.common.FakeData
import com.example.sehatin.navigation.SehatInSurface
import com.example.sehatin.view.components.CalendarView
import com.example.sehatin.view.components.CustomTopAppBar
import com.example.sehatin.view.components.FoodItemCard
import com.example.sehatin.view.components.WaterDialog

@Composable
fun DietScreen(
    modifier: Modifier = Modifier
) {
    // CAll API Here
    DietScreen(
        modifier = modifier,
        id = 1
    )
}

@Composable
private fun DietScreen(
    modifier: Modifier = Modifier,
    id: Int = 0
) {

    var currentWater by remember { mutableIntStateOf(1500) }
    val maxWaterValue by remember { mutableIntStateOf(3000) }
    var selectedValue by remember { mutableIntStateOf(100) }

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

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        TabSection()
                    }

                    item {
                        Spacer(modifier = Modifier.height(14.dp))
                        WaterDropSection(
                            onAdd = {
                                currentWater += selectedValue
                            },
                            onMinus = {
                                currentWater -= selectedValue
                            },
                            currentWater = currentWater,
                            maxWaterValue = maxWaterValue,
                            selectedValue = selectedValue
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        UpcomingScheduleSection()
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        ConsumptionHistory()
                    }

                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun TabSection(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .shadow(elevation = 2.5.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.menu_icon),
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Food Menus",
                color = textColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold

            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            modifier = Modifier
                .weight(1f)
                .shadow(elevation = 2.5.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.date_icon),
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Diet Schedule",
                color = textColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun WaterDropSection(
    onAdd: () -> Unit,
    onMinus: () -> Unit,
    currentWater: Int,
    maxWaterValue: Int,
    selectedValue: Int
) {

    val waterValue = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
            append("$currentWater")
        }
        withStyle(
            style = SpanStyle(
                color = if (currentWater >= maxWaterValue) Color.White else Color.White,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("/$maxWaterValue ml")
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 21.dp)
            .shadow(elevation = 2.5.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(26.dp),
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
                        Color(0x40FFFFFF),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(1.dp, color = Color.Unspecified, shape = RoundedCornerShape(8.dp))
                    .clickable(
                        enabled = currentWater - selectedValue > 0,
                        onClick =
                            onMinus,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.minus_icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
            Spacer(modifier = Modifier.width(70.dp))
            Icon(
                painter = painterResource(
                    id = R.drawable.water
                ),
                contentDescription = "Water Glass",
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.width(70.dp))
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        Color(0x40FFFFFF),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(1.dp, color = Color.Unspecified, shape = RoundedCornerShape(8.dp))
                    .clickable(
                        onClick = onAdd,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.plus_icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                        .graphicsLayer(rotationZ = 180f)
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = waterValue,
            fontSize = 18.sp
        )
    }
}

@Composable
private fun UpcomingScheduleSection(
    modifier: Modifier = Modifier
) {
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
            text = "Upcoming Schedule",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
        )

        Spacer(modifier = Modifier.height(16.dp))

        FoodItemCard(
            food = FakeData.foodItems[0],
            isTimeVisible = true,
            isBorderVisible = true,
            backgroundColor = ter
        )

        Spacer(modifier = Modifier.height(16.dp))

        ActionButtons(
            onEatClick = { },
            onChangeMenuClick = { }
        )
    }

}


@Composable
fun ActionButtons(
    onEatClick: () -> Unit,
    onChangeMenuClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onEatClick,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary), // Warna hijau
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
        ) {
            Text(text = "Eat", fontSize = 14.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.width(16.dp))

        OutlinedButton(
            onClick = onChangeMenuClick,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
        ) {
            Text(text = "Change Menu", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun ConsumptionHistory(
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
            text = "Today's Schedule",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        FakeData.ConsumptionHistory.forEach { item ->
            ConsumptionRow(item)
        }
        Text(
            text = "Tomorrow's Schedule",
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

@Composable
fun ConsumptionRow(
    item : Consumption
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
                text = item.foodType,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}