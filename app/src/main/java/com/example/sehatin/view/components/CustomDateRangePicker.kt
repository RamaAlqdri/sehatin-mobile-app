package com.example.sehatin.view.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDateRangePicker(
    startDate: String,
    endDate: String,
    onDateRangeSelected: (Date, Date) -> Unit
) {
    val showBottomSheet = remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true // Skip partially expanded state
    )
    val datePickerState = rememberDateRangePickerState()
    val formatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    val coroutineScope = rememberCoroutineScope()

    // Show bottom sheet when user clicks the "Pilih Tanggal" button
    if (showBottomSheet.value) {
        LaunchedEffect(showBottomSheet.value) {
            coroutineScope.launch {
                bottomSheetState.show()
            }
        }

        ModalBottomSheet(
            onDismissRequest = { showBottomSheet.value = false },
            sheetState = bottomSheetState,
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.background
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp, 16.dp)
            ) {
                Text(
                    text = "Pilih Rentang Tanggal",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
//                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                )

                DateRangePicker(
                    state = datePickerState,
                    showModeToggle = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp) // Menyesuaikan tinggi kalender agar tidak terpotong
//                        .padding(8.dp) // Padding agar kalender tidak terlalu rapat dengan tepi
                    , headline = null, // Hapus headline agar lebih sederhana
                    title = null,
                    colors = DatePickerDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.background,
//                        titleContentColor = Color.Black,
//                        headlineContentColor = Color.Black,
//                        weekdayContentColor = Color.Black,
//                        subheadContentColor = Color.Black,
//                        yearContentColor = Color.Green,
//                        currentYearContentColor = Color.Red,
//                        selectedYearContainerColor = Color.Red,
//                        disabledDayContentColor = Color.Gray,
//                        todayDateBorderColor = Color.Blue,
//                        dayInSelectionRangeContainerColor = Color.LightGray,
//                        dayInSelectionRangeContentColor = Color.White,
//                        selectedDayContainerColor = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
//                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = {
                        showBottomSheet.value = false
                    }) {
                        Text("Batal", color = MaterialTheme.colorScheme.onSurface)
                    }
                    TextButton(onClick = {
                        val startMillis = datePickerState.selectedStartDateMillis
                        val endMillis = datePickerState.selectedEndDateMillis

                        if (startMillis != null && endMillis != null) {
                            val zoneId = ZoneId.of("UTC+8")

                            // Konversi dari millis → LocalDate
                            val localStart =
                                Instant.ofEpochMilli(startMillis).atZone(zoneId).toLocalDate()
                            val localEnd =
                                Instant.ofEpochMilli(endMillis).atZone(zoneId).toLocalDate()

                            // Tambahkan waktu tetap (misal jam 12 siang) agar jelas dalam hari itu
                            val zonedStartDateTime = localStart.atTime(12, 0).atZone(zoneId)
                            val zonedEndDateTime = localEnd.atTime(12, 0).atZone(zoneId)

                            // Konversi ke Date
                            val _startDate = Date.from(zonedStartDateTime.toInstant())
                            val _endDate = Date.from(zonedEndDateTime.toInstant())



                            onDateRangeSelected(
                                _startDate,
                                _endDate
                            )
                        }
                        showBottomSheet.value = false
                    }) {
                        Text("OK", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }

    // Custom OutlinedButton with the style similar to the one in the image
    OutlinedButton(
        onClick = { showBottomSheet.value = true },
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White, // Background color of the button
            contentColor = Color(0xFF88B04B) // Text color of the button
        ),
        modifier = Modifier

            .fillMaxWidth()
            .height(40.dp) // Height for the button
//            .padding(horizontal = 16.dp)
        , // Padding to add space around the button
        border = BorderStroke(1.dp, Color(0xFFCFCFCF)), // Border color and width
        shape = RoundedCornerShape(8.dp) // Rounded corners
    ) {
        Text(
            text = if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                "$startDate – $endDate"
            } else {
                "Pilih Tanggal"
            },
            color = if (startDate.isNotEmpty()) MaterialTheme.colorScheme.primary else Color(
                0xFFCFCFCF
            ), // Text color as shown in the image// Text color as shown in the image
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}