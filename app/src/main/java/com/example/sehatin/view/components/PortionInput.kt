package com.example.sehatin.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.sehatin.utils.capitalizeWords

@Composable
fun PortionInput(
    modifier: Modifier,
    label: String, // Label untuk bagian "Porsi"
    value: String, // Nilai input
    onValueChange: (String) -> Unit // Fungsi untuk mengubah nilai input
) {

    Row(

        horizontalArrangement = Arrangement.spacedBy(-32.dp),
        modifier = modifier

//            .fillMaxWidth()
//            .padding(8.dp)


    ) {


        // Membuat field input untuk angka saja
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,

            modifier = Modifier
                .weight(1f)
                .zIndex(1f)
                .background(Color.White, RoundedCornerShape(16.dp))
                .height(64.dp)
//                .padding(8.dp)
            ,

            textStyle =
                MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number // Hanya menerima angka
            ),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                focusedLabelColor = MaterialTheme.colorScheme.primary
            ),
            singleLine = true // Agar input hanya satu baris
        )

        var width by remember { mutableStateOf(0f) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    width = coordinates.size.width.toFloat() // Ambil lebar dari koordinat container
                }
                .weight(1f)
                .height(64.dp)
//                .padding(8.dp)
                .background(Color(0xFFF3F3F3), RoundedCornerShape(16.dp))
//                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            val textSize = remember(width) {
                when {
                    width > 300f -> 20.sp // Ukuran besar jika cukup ruang
                    width > 200f -> 16.sp // Ukuran sedang jika ruang sedikit
                    else -> 12.sp // Ukuran kecil jika ruang terbatas
                }
            }
            Text(
                maxLines = 1, // Pastikan hanya satu baris
//                overflow = TextOverflow.Visible, // Jika teks terlalu panjang, akan dipotong dengan elipsis
                modifier = Modifier.fillMaxWidth() .padding(start = 30.dp, end = 10.dp)// Mengisi lebar yang tersedia
                ,text = label.capitalizeWords(),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}