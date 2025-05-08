package com.example.sehatin.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valentinilk.shimmer.shimmer

@Composable
fun KaloriSummary(
    totalKalori: Int?,
    rataRata: Int?,
    target: Int?,
    isLoading: Boolean
) {
    if (isLoading) {
        // Show loading skeleton (Shimmer effect)
        Column(modifier = Modifier.shimmer()) {
            Box(
                modifier = Modifier
                    .height(18.dp)
                    .width(100.dp)
                    .background(Color(0xFF3E7136).copy(alpha = 0.4f), shape = RoundedCornerShape(8.dp))
            )
            Spacer(
                modifier = Modifier
                    .height(16.dp)

            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column() {

                    Box(
                        modifier = Modifier
                            .height(18.dp)
                            .width(100.dp)
                            .background(Color(0xFF3E7136).copy(alpha = 0.4f), shape = RoundedCornerShape(8.dp))
                    )
                    Spacer(
                        modifier = Modifier
                            .height(8.dp)

                    )

                    Box(
                        modifier = Modifier
                            .height(28.dp)
                            .width(150.dp)
                            .background(Color(0xFF3E7136).copy(alpha = 0.4f), shape = RoundedCornerShape(8.dp))
                    )
                    Spacer(
                        modifier = Modifier
                            .height(8.dp)

                    )
                    Box(
                        modifier = Modifier
                            .height(18.dp)
                            .width(160.dp)
                            .background(Color(0xFF3E7136).copy(alpha = 0.4f), shape = RoundedCornerShape(8.dp))
                    )


                }

                Column() {
                    Box(
                        modifier = Modifier
                            .height(18.dp)
                            .width(100.dp)
                            .background(Color(0xFF3E7136).copy(alpha = 0.4f), shape = RoundedCornerShape(8.dp))
                    )
                }
            }
        }
    } else {
        // Show actual data when it's loaded
        Column() {
            Text(
                text = "Catatan",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Total Kalori",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF8A8A8A),
                    )
                    Text(
                        text = "$totalKalori",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE58B32),
                    )
                    Text(
                        text = "Rata-rata harian: $rataRata",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF8A8A8A),
                    )
                }

                Text(
                    text = "Target: $target kcal",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF8A8A8A),
                )
            }
        }
    }
}

