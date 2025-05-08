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
import androidx.compose.material.Divider
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
fun FoodHistory(
    items: List<Pair<String, Int>>?,
    total: Int?,
    isLoading: Boolean
) {

    if (isLoading) {
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
    } else {

        Column(


        ) {
            Text(
                "Dikonsumsi", fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            items?.forEach { (name, kcal) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        name, color = Color(0xFFF8A8A8A), fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Row {

                        Text(
                            "${kcal}", color = Color(0xFFF7931A), fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            " kcal", color = Color(0xFFF8A8A8A), fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    }

                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = Color(0xFFB7D78A),
                thickness = 2.dp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Total", color = Color.Black, fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Row {

                    Text(
                        "${total}", color = Color(0xFFF7931A), fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        " kcal", color = Color(0xFFF8A8A8A), fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }
    }
}
