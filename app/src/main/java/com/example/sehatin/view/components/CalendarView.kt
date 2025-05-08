package com.example.sehatin.view.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.ter
import com.example.compose.textColor
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*
import com.example.sehatin.R
import java.time.Clock
import java.time.ZoneId

@Composable
fun CalendarView(
    selectedDate: Date,
    OnDateSelected: (Date) -> Unit,

    ) {


//    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var currentYearMonth by remember { mutableStateOf(YearMonth.now()) }
    val today = LocalDate.now()

    Column(
        modifier = Modifier
            .padding(horizontal = 21.dp)
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(1.dp, color = Color.Unspecified, shape = RoundedCornerShape(8.dp))
                    .clickable(
                        onClick = {
                            currentYearMonth =
                                if (currentYearMonth.month == java.time.Month.JANUARY) {
                                    YearMonth.of(today.year, java.time.Month.DECEMBER)
                                } else {
                                    currentYearMonth.minusMonths(1)
                                }
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.back_arrow),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .graphicsLayer(rotationZ = 180f)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(40.dp)
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currentYearMonth.month.getDisplayName(TextStyle.FULL, Locale("id")),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(1.dp, color = Color.Unspecified, shape = RoundedCornerShape(8.dp))
                    .clickable(
                        onClick = {
                            currentYearMonth =
                                if (currentYearMonth.month == java.time.Month.DECEMBER) {
                                    YearMonth.of(today.year, java.time.Month.JANUARY)
                                } else {
                                    currentYearMonth.plusMonths(1)
                                }
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.back_arrow),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                )
            }

        }

        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 1.dp, RoundedCornerShape(16.dp))
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(vertical = 12.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 9.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("Min", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab").forEach {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            val days = generateMonthCalendar(currentYearMonth)

            val selectedLocalDate = remember(selectedDate) {
                selectedDate.toInstant()
                    .atZone(ZoneId.of("UTC+8"))
                    .toLocalDate()
            }

//            Log.e(
//                "DateItem",
//                "date: $selectedLocalDate"
//            )

            Column {
                days.chunked(7).forEach { week ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        week.forEach { date ->
                            DateItem(
                                date = date,
                                isSelected = date == selectedLocalDate,
                                isToday = date == today,
                                isOtherMonth = date.month != currentYearMonth.month,
                                onClick = {
                                    val zoneId = ZoneId.of("UTC+8")
                                    val zonedDateTime = date.atTime(12, 0).atZone(zoneId) // <- set jam 12 siang
                                    val instant = zonedDateTime.toInstant()
                                    Log.e(
                                        "DateItem",
                                        "date: $date, selectedDate: $selectedLocalDate"
                                    )
                                    OnDateSelected(Date.from(instant))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DateItem(
    date: LocalDate,
    isSelected: Boolean,
    isToday: Boolean,
    isOtherMonth: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.primary
        isToday -> ter
        else -> Color.White
    }

    val textColor =
        if (isSelected) Color.White else if (isOtherMonth) Color(0xFF9C9C9C) else Color.Black




    Column(
        modifier = Modifier
            .padding(4.dp)
            .size(40.dp)
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .border(
                1.dp,
                color = if (isSelected) Color.White else ter,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
        )
//        if (isOtherMonth == false) {
//            Icon(
//                painter = painterResource(id = R.drawable.date_sep),
//                contentDescription = "Selected Date",
//                tint = if (isSelected) Color.White else MaterialTheme.colorScheme.primary,
//            )
//        }
    }
}

fun generateMonthCalendar(yearMonth: YearMonth): List<LocalDate> {
    val firstDayOfMonth = yearMonth.atDay(1)
    val lastDayOfMonth = yearMonth.atEndOfMonth()
    val firstDayOfWeek = firstDayOfMonth.minusDays(firstDayOfMonth.dayOfWeek.value.toLong() % 7)
    val lastDayOfWeek = lastDayOfMonth.plusDays(6 - lastDayOfMonth.dayOfWeek.value.toLong() % 7)

    return (firstDayOfWeek..lastDayOfWeek).toList()
}

operator fun LocalDate.rangeTo(other: LocalDate): List<LocalDate> {
    val dates = mutableListOf<LocalDate>()
    var current = this
    while (current <= other) {
        dates.add(current)
        current = current.plusDays(1)
    }
    return dates
}