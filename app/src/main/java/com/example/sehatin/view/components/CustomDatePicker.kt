package com.example.sehatin.view.components

import android.app.DatePickerDialog
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehatin.R
import java.util.Calendar

@Composable
fun CustomDatePicker(
    label: String,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    isError: Boolean,
    errorMessage: String,
    borderWidth: Dp = 1.dp,
    modifier: Modifier = Modifier,
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val dateDialog = remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth()
                .border(
                    width = borderWidth,
                    color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable {
                    dateDialog.value = true
                },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 21.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (selectedDate.isEmpty()) label else selectedDate,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = if (selectedDate.isEmpty()) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f) // Menggunakan weight agar Text mengisi sisa ruang
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar), // Ganti dengan icon yang sesuai
                    contentDescription = "Calendar Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            if (dateDialog.value) {
                val context = LocalContext.current
                // DatePickerDialog dengan tombol Cancel dan penutupan otomatis
                val datePickerDialog = DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val selectedDateFormatted = "$dayOfMonth/${month + 1}/$year"
                        onDateSelected(selectedDateFormatted)
                        dateDialog.value = false
                    },
                    year, month, day
                )

                // Mengatur tombol cancel agar dialog ditutup jika ditekan
                datePickerDialog.setOnCancelListener {
                    dateDialog.value = false // Menutup dialog saat tombol Cancel ditekan
                }

                datePickerDialog.show()
            }
        }

        if (isError) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textAlign = TextAlign.Start
            )
        }
    }
}


