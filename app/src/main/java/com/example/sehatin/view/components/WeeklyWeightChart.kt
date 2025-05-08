package com.example.sehatin.view.components

import android.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.sehatin.data.model.response.WeightHistoryItem
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.time.LocalDate
import java.time.format.DateTimeFormatter



data class WeeklyWeightInput(
    val dateLabel: String,  // Menggunakan format bulan dan tanggal, misalnya: "04 Mei"
    val actual: Float?,     // Berat yang tercatat
    val target: Float       // Target berat badan
)

fun mapToWeeklyWeightFromApi(input: List<WeightHistoryItem>?): List<WeeklyWeightInput> {
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") // Format yang sesuai dengan data API

    return input?.map { weightData ->
        val date = LocalDate.parse(weightData.createdAt, formatter)
        // Mengambil tanggal dan bulan dari data yang diterima
        val dateLabel = date.format(DateTimeFormatter.ofPattern("dd MMM"))

        WeeklyWeightInput(
            dateLabel = dateLabel,  // Menampilkan bulan dan tanggal
            actual = weightData.weight.toFloat(),  // Berat badan yang tercatat
            target = 64f  // Menetapkan target default
        )
    } ?: emptyList()  // Jika data null, kembalikan list kosong
}
@Composable
fun WeeklyWeightChart(
    weightData: List<WeeklyWeightInput>
) {
    LaunchedEffect(weightData) {

        // Logika untuk memperbarui chart jika data berubah
        // Misalnya, jika Anda menggunakan LiveData atau StateFlow
        // Anda bisa memanggil invalidate() pada chart di sini
        // Namun, dalam kasus ini, kita hanya menggunakan AndroidView
        // untuk menampilkan chart, jadi tidak perlu melakukan apa-apa di sini
        // invalidate() // Memperbarui tampilan chart
        // Jika Anda menggunakan LiveData atau StateFlow, Anda bisa memanggil invalidate() pada chart di sini


    }

    val colorScheme = MaterialTheme.colorScheme
    AndroidView(factory = { context ->
        LineChart(context).apply {
            val actualEntries = mutableListOf<Entry>()
            val dateLabels = mutableListOf<String>()

            weightData.forEachIndexed { index, week ->
                dateLabels.add(week.dateLabel) // Menggunakan tanggal dan bulan
                week.actual?.let {
                    actualEntries.add(Entry(index.toFloat(), it))
                }
            }

            val actualDataSet = LineDataSet(actualEntries, "").apply {
                val softBlue = colorScheme.primary.toArgb()
                color = softBlue
                setDrawValues(false)
                setDrawCircles(true)
                setCircleColor(softBlue)
                circleRadius = 5f
                lineWidth = 2.5f
                mode = LineDataSet.Mode.CUBIC_BEZIER
            }

            data = LineData(actualDataSet)

            description.isEnabled = false
            legend.isEnabled = false

            // Sumbu X (Tanggal dan Bulan)
            xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(dateLabels) // Menampilkan bulan dan tanggal
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                setDrawGridLines(false)
                setDrawAxisLine(true)
                textColor = Color.DKGRAY
                labelRotationAngle = 0f // Label tegak
                textSize = 11f
            }

            // Sumbu Y (Berat Badan)
            axisLeft.apply {
                // Otomatis menyesuaikan rentang sumbu Y berdasarkan data yang ada
                val minWeight = weightData.minOfOrNull { it.actual ?: 0f } ?: 0f
                val maxWeight = weightData.maxOfOrNull { it.actual ?: 0f } ?: 100f
                axisMinimum = minWeight - 5f // Memberi sedikit ruang di bawah
                axisMaximum = maxWeight + 5f // Memberi sedikit ruang di atas
                textColor = Color.DKGRAY
                gridColor = Color.LTGRAY
                textSize = 11f
            }


            axisRight.isEnabled = false
            setTouchEnabled(false)
            setPinchZoom(false)
            setScaleEnabled(false)

            invalidate()
        }
    }, modifier = Modifier
        .fillMaxWidth()
        .height(260.dp)) // Tentukan ukuran chart
}






@Composable
fun WeeklyWeightChartFromInput(inputData: List<WeightHistoryItem>?) {
    // Mengonversi data input menjadi WeeklyWeightInput
    val chartData = remember(inputData) { mapToWeeklyWeightFromApi(inputData) }
    WeeklyWeightChart(chartData)
}

// --- Contoh Pemakaian ---
