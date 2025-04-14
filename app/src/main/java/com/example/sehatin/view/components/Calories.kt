package com.example.sehatin.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehatin.R

@Composable
fun CaloriesProgress(
    modifier: Modifier = Modifier,
    currentCalories: Int = 0,
    maxCaloriesValue: Int = 0,
//    onAdd : () -> Unit
) {

    var percentage by remember { mutableIntStateOf(((currentCalories.toFloat() / maxCaloriesValue) * 100).toInt()) }

    LaunchedEffect(currentCalories) {
        percentage = ((currentCalories.toFloat() / maxCaloriesValue) * 100).toInt()
    }

    val caloriesValue = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0xFFE58B32), fontWeight = FontWeight.Bold)) {
            append("$currentCalories")
        }
        withStyle(
            style = SpanStyle(
                color = if (currentCalories >= maxCaloriesValue) Color(0xFFE58B32) else Color(
                    0x99E58B32
                ),
                fontWeight = FontWeight.Bold
            )
        ) {
            append("/$maxCaloriesValue kcaL")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp)
            .shadow(elevation = 2.5.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(26.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(
                id = when {
                    percentage in 25..49 -> R.drawable.cal_25
                    percentage in 50..74 -> R.drawable.cal_50
                    percentage in 75..99 -> R.drawable.cal_75
                    percentage >= 100 -> R.drawable.cal_100
                    else -> R.drawable.cal_0
                }
            ),
            contentDescription = "Water Glass",
            tint = Color.Unspecified,
            modifier = Modifier.size(134.dp)
        )
//        Button(
//            onClick = onAdd,
//            modifier = Modifier
//                .padding(top = 16.dp)
//                .size(200.dp, 40.dp)
//        ) {
//            Text(text = "Add Calories", fontSize = 14.sp)
//        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = caloriesValue ,
            fontSize = 18.sp
        )
    }

}