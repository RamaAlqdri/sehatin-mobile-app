package com.example.sehatin.view.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@Composable
fun AgeDisplay(
    selectedDate: String,
    modifier: Modifier = Modifier
) {
    val age = calculateAge(selectedDate) // Fungsi untuk menghitung usia dari tanggal
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp) // Ukuran sesuai dengan contoh gambar
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Text(
            text = age.toString(),
            style = MaterialTheme.typography.headlineMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 36.sp, // Ukuran teks sesuai dengan contoh gambar
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}


fun calculateAge(birthDate: String): Int {
    if(birthDate.isEmpty()){
        return 0
    }
    return try {
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy", Locale.getDefault())
        val birthLocalDate = LocalDate.parse(birthDate, formatter)
        val now = LocalDate.now()
        Period.between(birthLocalDate, now).years
    } catch (e: Exception) {
        0
    }
}

//fun calculateAge(birthDate: String): Int {
//    return try {
//        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//        val birthDateParsed = dateFormatter.parse(birthDate)
//
//        birthDateParsed?.let {
//            val now = Calendar.getInstance()
//            val birthCalendar = Calendar.getInstance().apply {
//                time = birthDateParsed
//            }
//
//            var age = now.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)
//
//            if (now.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
//                age--
//            }
//
//            age
//        } ?: 0
//    } catch (e: Exception) {
//        0
//    }
//}
