package com.example.sehatin.view.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import com.valentinilk.shimmer.shimmer

@Composable
fun CaloriesBarChart(
    labels: List<String>?,
    breakfastData: List<Float>?,
    lunchData: List<Float>?,
    dinnerData: List<Float>?,
    snackData: List<Float>?,
    maxHeight: Dp = 65.dp,
    isLoading: Boolean
) {

    if (isLoading) {
        // Show loading skeleton (Shimmer effect)
        Column(modifier = Modifier.shimmer()) {
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
            Box(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .background(
                        Color(0xFF3E7136).copy(alpha = 0.4f),
                        shape = RoundedCornerShape(8.dp)
                    )
            )
            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )
        }
        return
    } else {


        Log.e(
            "CaloriesBarChart",
            "labels: $labels, breakfastData: $breakfastData, lunchData: $lunchData, dinnerData: $dinnerData, snackData: $snackData"
        )
        // Data yang akan digunakan untuk menggambar setiap bar (terpisah per kategori)
        val groupedData = labels?.mapIndexed { index, label ->
            StackedData(
                inputs = listOf(
                    breakfastData?.get(index) ?: 0f,
                    lunchData?.get(index) ?: 0f,
                    dinnerData?.get(index) ?: 0f,
                    snackData?.get(index) ?: 0f
                ),
                colors = listOf(
                    Color(0xFFE8C37B), // Makan Pagi
                    Color(0xFF7FB2D9), // Makan Siang
                    Color(0xFFD26466), // Makan Malam
                    Color(0xFF7D5BA6)  // Snack
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            // Menggambar setiap grup bar kategori untuk setiap hari
            Row(


                modifier = Modifier
                    .fillMaxWidth()
//                .height(maxHeight)

//                .padding(horizontal = 10.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                groupedData?.forEachIndexed { index, stackedData ->
//                Log.e("CaloriesBarChart", "index: $index, stackedData: $stackedData")
                    // Setiap kolom mewakili satu hari
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 2.dp)
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                    )
                    {
                        stackedData.inputs.forEachIndexed { i, input ->
//                        Log.e("CaloriesBarChart", "input: $input, color: ${stackedData.colors[i]}")

                            StackedBar(
                                value = input,
                                color = stackedData.colors[i],
                                maxHeight = maxHeight
                            )
                        }
                    }
                }
            }

            // Menambahkan Label Hari (Sumbu X) di bawah bar chart
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                labels?.forEach {
                    Text(
                        modifier = Modifier
//                        .fillMaxWidth()
                            .weight(1f)
                            .padding(top = 8.dp),
                        text = it,

                        color = Color.Black,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

// Fungsi untuk menggambar bar dalam bentuk Column untuk stacked bar chart
@Composable
private fun StackedBar(
    value: Float,
    color: Color,
    maxHeight: Dp
) {
    val itemHeight = remember(value) { value * maxHeight.value / 1000 }

    Column(
//        verticalArrangement = Arrangement.Bottom,
//        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
//            .padding(horizontal = 2.dp)
            .fillMaxWidth()

    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight.dp)
                .background(color)
        )
    }
}

data class StackedData(
    val inputs: List<Float>,  // Nilai untuk setiap kategori per grup
    val colors: List<Color>   // Warna untuk setiap kategori per grup
)
