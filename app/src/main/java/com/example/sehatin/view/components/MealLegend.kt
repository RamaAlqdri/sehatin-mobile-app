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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valentinilk.shimmer.shimmer

data class MealLegendItem(val name: String, val color: Color, val kalori: Int)

@Composable
fun MealLegend(legendItems: List<MealLegendItem>?, isLoading: Boolean) {

    if (isLoading) {
        // Show loading skeleton (Shimmer effect)
        Column(modifier = Modifier.shimmer()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column() {


                    Box(
                        modifier = Modifier
                            .height(26.dp)
                            .width(150.dp)
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
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column() {


                    Box(
                        modifier = Modifier
                            .height(26.dp)
                            .width(150.dp)
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
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column() {


                    Box(
                        modifier = Modifier
                            .height(26.dp)
                            .width(150.dp)
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
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column() {


                    Box(
                        modifier = Modifier
                            .height(26.dp)
                            .width(150.dp)
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
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
        }
        return
    } else {


        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(4.dp),

            ) {
            legendItems?.forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(item.color, RoundedCornerShape(2.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            item.name, color = Color(0xFFF8A8A8A), fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    Row {

                        Text(
                            "${item.kalori}", color = Color(0xFFF7931A), fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            " kcal", color = Color(0xFFF8A8A8A), fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
