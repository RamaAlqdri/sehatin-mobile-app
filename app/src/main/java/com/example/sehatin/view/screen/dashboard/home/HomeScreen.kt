package com.example.sehatin.view.screen.dashboard.home

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.caloriesColor
import com.example.compose.waterColor
import com.example.sehatin.R
import com.example.sehatin.view.components.CircularProgressBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSnackClick: (Long, String) -> Unit
) {
    val vectorImages = listOf(
        R.drawable.ic_male,
        R.drawable.ic_female,
        R.drawable.calories,
        R.drawable.water
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .padding(horizontal = 21.dp, vertical = 20.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = modifier.height(38.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        shape = CircleShape
                    )
                    .clip(CircleShape)
//                    .size(38.dp)
            ) {
                Image(
                    painter = painterResource(id = vectorImages[0]),
                    contentDescription = null,
                    modifier = Modifier
                        .absoluteOffset(y = 4.dp)
                        .size(38.dp)
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(-8.dp),
//                modifier = modifier.background(color = Color.Gray)

            ) {

                Text(
                    text = "Halo, Rama",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Minggu, 27 Januari 2025",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
            }
        }
        Box(
            modifier = modifier

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
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .fillMaxHeight()
            ) {

                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier.fillMaxHeight()

                ) {

                    Text(
                        text = "Kemajuan Diet",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = modifier.height(2.dp))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(-10.dp)
                    ) {

                        Text(
                            text = "Hebat!",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp
                        )
                        Text(
                            text = "Kamu berada di jalur yang tepat",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Normal,
                            fontSize = 10.sp
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
//                        .background(Color(0xFF8BC34A)), // Warna hijau sebagai background layar
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressBar(
                        percentage = 0.90f, // Progress 69%
                        size = 110.dp,
                        strokeWidth = 16.dp,
                        backgroundColor = Color.White.copy(0.3f), // Warna abu-abu terang
                        progressColor = Color.White,
                        textColor = Color.White
                    )
                }

            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .fillMaxWidth()

        ) {
            Box(
                modifier = modifier
                    .weight(1f)
                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp))
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    .height(79.dp)
//                    .width(151.dp)

//                    .fillMaxWidth(0.55f)
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
//                    .shadow(4.dp, shape = RoundedCornerShape(16.dp))
                        .fillMaxSize()


                ) {
                    Column {
                        Text(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            text = "Kalori"
                        )
                        Row {

                            Text(
                                color = caloriesColor,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                text = "500"
                            )
                            Text(
                                color = caloriesColor.copy(alpha = 0.5f),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                text = "/1000"
                            )
                            Text(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
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
                modifier = modifier
                    .weight(1f)
                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp))
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    .height(79.dp)
//                    .width(151.dp)
//                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
//                    .shadow(4.dp, shape = RoundedCornerShape(16.dp))
                        .fillMaxSize()


                ) {
                    Column {
                        Text(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            text = "Air"
                        )
                        Row {

                            Text(
                                color = waterColor,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                text = "500"
                            )
                            Text(
                                color = waterColor.copy(alpha = 0.5f),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                text = "/1000"
                            )
                            Text(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
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

    }
}