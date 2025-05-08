package com.example.sehatin.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.compose.backValue
import com.example.compose.ter
import com.example.compose.waterGlass
import com.example.sehatin.common.FoodItem
import com.example.sehatin.R
import com.example.sehatin.utils.capitalizeWords

@Composable
fun FoodItemCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String = "", time: String = "", calories: Double = 0.0,
    protein: Double = 0.0,
    serving_amount: Double = 0.0,
    serving_unit: String = "",
    isTimeVisible: Boolean = false,
    isBorderVisible: Boolean = false,
    isCompleted: Boolean = false,
    backgroundColor: Color = Color.White,
    onClick: () -> Unit = {}
) {
    val textColor = if (isCompleted) Color(0xFF9B9CAC) else MaterialTheme.colorScheme.primary // Abu-abu medium
    val iconColor = if (isCompleted) Color(0xFF9B9CAC) else MaterialTheme.colorScheme.primary // Abu-abu terang
    val borderColor = if (isBorderVisible) (if (isCompleted) Color(0xFF9B9CAC) else MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)) else Color.Transparent
    val bgColor = if (isCompleted) Color(0xFFEF2F2F2) else backgroundColor // abu-abu background

    val grayscaleMatrix = ColorMatrix().apply { setToSaturation(0f) }
    val caloriesValue = buildAnnotatedString {
        withStyle(style = SpanStyle(color = textColor, fontWeight = FontWeight.Bold)) {
            append("$calories")
        }
        withStyle(style = SpanStyle(color = textColor, fontWeight = FontWeight.Normal)) {
            append(" kcaL")
        }
    }

//    val weightValue = buildAnnotatedString {
//        withStyle(style = SpanStyle(color = textColor, fontWeight = FontWeight.Bold)) {
//            append("$protein")
//        }
//        withStyle(style = SpanStyle(color = textColor, fontWeight = FontWeight.Normal)) {
//            append(" gram")
//        }
//    }
    val weightValue = buildAnnotatedString {
        withStyle(style = SpanStyle(color = textColor, fontWeight = FontWeight.Bold)) {
            append("${serving_amount.toInt()}")
        }
        withStyle(style = SpanStyle(color = textColor, fontWeight = FontWeight.Normal)) {
            append(" ${serving_unit.capitalizeWords()}")
        }
    }

    Column(
        modifier = modifier
            .background(bgColor, shape = RoundedCornerShape(14.dp))
            .border(BorderStroke(1.dp, borderColor), shape = RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
                colorFilter = if (isCompleted) ColorFilter.colorMatrix(grayscaleMatrix) else null
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isTimeVisible) {
                        Icon(
                            painter = painterResource(id = R.drawable.alarm),
                            contentDescription = "Time",
                            tint = iconColor,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = time,
                            fontSize = 10.sp,
                            color = textColor
                        )
                        Spacer(modifier = Modifier.width(9.dp))
                    }

                    Icon(
                        painter = painterResource(id = R.drawable.calories),
                        contentDescription = "Calories",
                        tint = iconColor,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = caloriesValue,
                        fontSize = 10.sp,
                        color = textColor
                    )

                    Spacer(modifier = Modifier.width(9.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.menu_icon),
                        contentDescription = "Weight",
                        tint = iconColor,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = weightValue,
                        fontSize = 10.sp,
                        color = textColor
                    )
                }
            }
        }
    }
}
