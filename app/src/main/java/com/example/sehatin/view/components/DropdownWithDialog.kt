package com.example.sehatin.view.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
//import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.example.sehatin.viewmodel.RadioOptionFoodCategory
import kotlinx.coroutines.flow.StateFlow


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DropdownWithDialog(
    options: List<RadioOptionFoodCategory>, // Daftar pilihan untuk dropdown
    selectedOption: StateFlow<RadioOptionFoodCategory>, // Pilihan yang sedang dipilih
    onOptionSelected: (RadioOptionFoodCategory) -> Unit // Fungsi yang dipanggil ketika pilihan dipilih
) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) } // Menampilkan dialog



    // Menampilkan Dropdown dan opsi yang dipilih
    Column() {
        OutlinedButton(
            modifier = Modifier,
//                .padding(horizontal = 4.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent, // Set the background color to transparent
                contentColor = MaterialTheme.colorScheme.primary // Set the content text color
            ),
            contentPadding = PaddingValues(start = 6.dp),
            border = null,
            onClick = { showDialog = true },

//            shape = MaterialTheme.shapes.medium, // Use the default shape
//            elevation = ButtonDefaults.elevatedButtonElevation(), // Optional: Add shadow (elevation)

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp) // Add padding to the row
//                    .then(Modifier.weight(1f)) // Make the row take up available space


            ) {

                Text(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    text = selectedOption.value.label)

                // Menampilkan ikon panah ke bawah
                androidx.compose.material3.Icon(
                    modifier = Modifier
//                        .padding(start = 8.dp) // Add padding to the icon
                        .size(32.dp), // Set the size of the icon
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown Arrow",
                    tint = MaterialTheme.colorScheme.primary // Set the icon color
                )

            }
        }

        // Menampilkan dialog jika showDialog bernilai true
        if (showDialog) {
            AlertDialog(
                modifier = Modifier
                    .fillMaxWidth()
//                    .padding(vertical = 8.dp)
                    .background(color = Color.Unspecified, shape = RoundedCornerShape(32.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(32.dp)
                    )
                    .clip(
                        RoundedCornerShape(32.dp)
                    ),
                onDismissRequest = { showDialog = false },
                confirmButton = { null } ,
                title = { null },
                text = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
//                            .heightIn(min = 0.dp) // Menghindari ruang kosong yang tidak perlu
                            .padding(bottom = 14.dp) // Menghilangkan padding ekstra
                    ) {
                        // Menyusun konten dalam kolom dan memusatkan semua elemen
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp) // Menambahkan jarak antar item
                        ) {
                            options.forEach { option ->
                                TextButton(
                                    modifier = Modifier,
                                    colors = ButtonDefaults.textButtonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = MaterialTheme.colorScheme.primary
                                    ),
                                    onClick = {

                                        onOptionSelected(option) // Memanggil fungsi ketika pilihan dipilih

                                        showDialog = false // Menutup dialog setelah memilih
                                    }
                                ) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 20.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        textAlign = TextAlign.Center,
                                        text = option.label
                                    )
                                }
                            }
                        }
                    }
                },

                )
        }
    }
}

