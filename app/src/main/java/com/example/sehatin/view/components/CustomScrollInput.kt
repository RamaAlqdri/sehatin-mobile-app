package com.example.yourappname // Ganti dengan nama package aplikasi Anda

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

/**
 * Data class untuk merepresentasikan opsi unit (misalnya cm, ft).
 */
data class Option(val index: Int, val label: String)

/**
 * Composable utama untuk input scroll dan ketik.
 *
 * @param modifier Modifier untuk kustomisasi tata letak.
 * @param firstUnitMax Nilai maksimum untuk unit pertama (misalnya, cm).
 * @param secondUnitMax Nilai maksimum untuk unit kedua (misalnya, ft, jika digunakan).
 * @param commaMax Nilai maksimum untuk bagian desimal (0-9 untuk satu digit).
 * @param unitOptions Daftar opsi unit yang tersedia.
 * @param initialValue Nilai awal untuk input.
 * @param onValueChanged Callback yang dipanggil ketika nilai akhir berubah.
 */
@Composable
fun CustomScrollInput(
    modifier: Modifier = Modifier,
    firstUnitMax: Int = 300,
    secondUnitMax: Int = 10, // Contoh untuk feet
    commaMax: Int = 9, // Untuk satu digit desimal (0-9)
    unitOptions: List<Option> = listOf(
        Option(0, "cm"),
    ),
    initialValue: Float = 0f,
    onValueChanged: (Float) -> Unit
) {
    val firstUnitRange = remember(firstUnitMax) { (0..firstUnitMax).toList() }
    val secondUnitRange = remember(secondUnitMax) { (0..secondUnitMax).toList() }
    val commaRange = remember(commaMax) { (0..commaMax).toList() }

    var selectedUnitOption by remember {
        mutableStateOf(unitOptions.find { it.index == 0 } ?: unitOptions.firstOrNull() ?: Option(0, "N/A"))
    }

    val currentUnitRange by remember(selectedUnitOption, firstUnitRange, secondUnitRange) {
        derivedStateOf {
            if (selectedUnitOption.index == 0) firstUnitRange else secondUnitRange
        }
    }
    val currentUnitMax by remember(selectedUnitOption, firstUnitMax, secondUnitMax) {
        derivedStateOf {
            if (selectedUnitOption.index == 0) firstUnitMax else secondUnitMax
        }
    }

    val unitListState = rememberLazyListState()
    val commaListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    fun parseValueParts(value: Float, currentMax: Int, currentCommaMax: Int): Pair<String, String> {
        val intPart = value.toInt().coerceIn(0, currentMax)
        val decimalPart = ((value - intPart) * 10).toInt().coerceIn(0, currentCommaMax)
        return Pair(intPart.toString(), decimalPart.toString())
    }

    val (initialIntText, initialDecimalText) = remember(initialValue, selectedUnitOption, currentUnitMax, commaMax) {
        parseValueParts(initialValue, currentUnitMax, commaMax)
    }

    var intPartText by remember(initialIntText) { mutableStateOf(initialIntText) }
    var decimalPartText by remember(initialDecimalText) { mutableStateOf(initialDecimalText) }


    // --- SINKRONISASI DARI SCROLL KE TEXTFIELD ---
    val selectedIntPartFromScroll by remember {
        derivedStateOf {
            val layoutInfo = unitListState.layoutInfo
            if (layoutInfo.visibleItemsInfo.isNotEmpty() && currentUnitRange.isNotEmpty()) {
                val selectedLazyColumnIndex = unitListState.firstVisibleItemIndex + 1
                val indexInUnitRange = selectedLazyColumnIndex - 1
                currentUnitRange.getOrNull(indexInUnitRange) ?: currentUnitRange.first()
            } else {
                currentUnitRange.firstOrNull() ?: 0
            }
        }
    }

    val selectedDecimalPartFromScroll by remember {
        derivedStateOf {
            val layoutInfo = commaListState.layoutInfo
            if (layoutInfo.visibleItemsInfo.isNotEmpty() && commaRange.isNotEmpty()) {
                val selectedLazyColumnIndex = commaListState.firstVisibleItemIndex + 1
                val indexInCommaRange = selectedLazyColumnIndex - 1
                commaRange.getOrNull(indexInCommaRange) ?: commaRange.first()
            } else {
                commaRange.firstOrNull() ?: 0
            }
        }
    }

    LaunchedEffect(selectedIntPartFromScroll) {
        if (intPartText != selectedIntPartFromScroll.toString()) {
            intPartText = selectedIntPartFromScroll.toString()
        }
    }

    LaunchedEffect(selectedDecimalPartFromScroll) {
        if (decimalPartText != selectedDecimalPartFromScroll.toString()) {
            decimalPartText = selectedDecimalPartFromScroll.toString()
        }
    }

    // --- SINKRONISASI DARI TEXTFIELD KE SCROLL ---
    LaunchedEffect(intPartText, selectedUnitOption) {
        val targetInt = intPartText.toIntOrNull()
        if (targetInt != null) {
            val indexInRange = currentUnitRange.indexOf(targetInt)
            if (indexInRange != -1 && indexInRange < unitListState.layoutInfo.totalItemsCount && currentUnitRange.isNotEmpty()) {
                // Periksa apakah indexInRange valid untuk scroll (tidak negatif)
                if (indexInRange >= 0) {
                    coroutineScope.launch {
                        unitListState.scrollToItem(indexInRange)
                    }
                }
            }
        } else if (intPartText.isBlank() && currentUnitRange.isNotEmpty()) {
            coroutineScope.launch {
                intPartText = currentUnitRange.first().toString() // Set ke nilai pertama jika kosong
                // unitListState.scrollToItem(0) // Otomatis ter-trigger oleh perubahan intPartText
            }
        }
    }

    LaunchedEffect(decimalPartText) {
        val targetDecimal = decimalPartText.toIntOrNull()
        if (targetDecimal != null) {
            val indexInRange = commaRange.indexOf(targetDecimal)
            if (indexInRange != -1 && indexInRange < commaListState.layoutInfo.totalItemsCount && commaRange.isNotEmpty()) {
                if (indexInRange >= 0) {
                    coroutineScope.launch {
                        commaListState.scrollToItem(indexInRange)
                    }
                }
            }
        } else if (decimalPartText.isBlank() && commaRange.isNotEmpty()) {
            coroutineScope.launch {
                decimalPartText = commaRange.first().toString()
                // commaListState.scrollToItem(0)
            }
        }
    }

    // --- PENGHITUNGAN NILAI AKHIR ---
    val finalValue by remember(selectedUnitOption, intPartText, decimalPartText, currentUnitMax) {
        derivedStateOf {
            val currentInt = intPartText.toIntOrNull() ?: 0
            val currentDecimal = decimalPartText.toIntOrNull() ?: 0

            val decimalAsFloat = currentDecimal.toFloat() / 10f
            val combined = currentInt.toFloat() + decimalAsFloat

            val maxForUnitFloat = currentUnitMax.toFloat() + (commaMax.toFloat() / 10f)
            combined.coerceIn(0f, maxForUnitFloat)
        }
    }

    LaunchedEffect(finalValue) {
        onValueChanged(finalValue)
    }

    // Inisialisasi scroll posisi berdasarkan nilai text field awal
    LaunchedEffect(Unit, selectedUnitOption) { // Hanya saat pertama kali atau unit berubah
        val initialIntVal = intPartText.toIntOrNull() ?: currentUnitRange.firstOrNull() ?: 0
        val initialDecimalVal = decimalPartText.toIntOrNull() ?: commaRange.firstOrNull() ?: 0

        val intIndex = currentUnitRange.indexOf(initialIntVal)
        if (intIndex != -1 && currentUnitRange.isNotEmpty() && intIndex >= 0) {
            coroutineScope.launch { unitListState.scrollToItem(intIndex) }
        }

        val decimalIndex = commaRange.indexOf(initialDecimalVal)
        if (decimalIndex != -1 && commaRange.isNotEmpty() && decimalIndex >= 0) {
            coroutineScope.launch { commaListState.scrollToItem(decimalIndex) }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            CustomNumericTextField(
                value = intPartText,
                onValueChange = { newValue ->
                    val filtered = newValue.filter { it.isDigit() }
                    if (filtered.isEmpty()) {
                        intPartText = "" // Biarkan kosong, akan dihandle jadi nilai pertama oleh LaunchedEffect
                    } else {
                        val num = filtered.toLongOrNull() // Gunakan Long untuk menghindari overflow sementara
                        if (num != null) {
                            if (num <= currentUnitMax) {
                                intPartText = num.toString()
                            } else {
                                intPartText = currentUnitMax.toString()
                            }
                            // Handle "00" -> "0"
                            if (intPartText.length > 1 && intPartText.startsWith("0")) {
                                intPartText = (intPartText.toIntOrNull() ?: 0).toString()
                            }
                        }
                    }
                },
                modifier = Modifier.width(80.dp)
            )

            Text(
                text = ",",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 2.dp)
            )

            CustomNumericTextField(
                value = decimalPartText,
                onValueChange = { newValue ->
                    val filtered = newValue.filter { it.isDigit() }
                    if (filtered.isEmpty()) {
                        decimalPartText = ""
                    } else {
                        val num = filtered.toIntOrNull()
                        if (num != null && filtered.length == 1 && num <= commaMax) { // Hanya satu digit
                            decimalPartText = num.toString()
                        } else if (num != null && filtered.length == 1 && num > commaMax) {
                            decimalPartText = commaMax.toString()
                        } else if (filtered.length > 1) { // Jika lebih dari 1 digit, ambil digit pertama
                            val firstDigit = filtered.first().toString().toIntOrNull()
                            if (firstDigit != null && firstDigit <= commaMax) {
                                decimalPartText = firstDigit.toString()
                            }
                        }
                    }
                },
                modifier = Modifier.width(50.dp)
            )

            Spacer(Modifier.width(16.dp))

            unitOptions.forEachIndexed { index, option ->
                radioButtonOption(
                    selected = (option.index == selectedUnitOption.index),
                    onClick = {
                        if (selectedUnitOption.index != option.index) {
                            selectedUnitOption = option
                            // Validasi ulang intPartText berdasarkan unit baru
                            val currentInt = intPartText.toIntOrNull() ?: 0
                            val newMax = if (option.index == 0) firstUnitMax else secondUnitMax
                            if (currentInt > newMax) {
                                intPartText = newMax.toString()
                            } else {
                                // Jika nilai masih valid, trigger re-evaluation dan scroll
                                intPartText = currentInt.toString()
                            }
                        }
                    },
                    label = option.label
                )
                if (index < unitOptions.size - 1) Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val currentTextFieldIntValue = intPartText.toIntOrNull()
            LazyColumn(
                state = unitListState,
                modifier = Modifier.height(160.dp).weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Item placeholder di awal untuk memungkinkan item pertama di tengah
                item { Spacer(Modifier.height( (160.dp - 70.dp) / 2 - 12.dp)) } // ( tinggi total - tinggi item ) / 2 - spasi antar item

                items(currentUnitRange.size) { index ->
                    val itemValue = currentUnitRange[index]
                    HeightBoxItem(
                        value = itemValue,
                        isSelected = (itemValue == currentTextFieldIntValue),
                    )
                }
                // Item placeholder di akhir
                item { Spacer(Modifier.height((160.dp - 70.dp) / 2 - 12.dp)) }
            }

            Text(
                text = ",",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            val currentTextFieldDecimalValue = decimalPartText.toIntOrNull()
            LazyColumn(
                state = commaListState,
                modifier = Modifier.height(160.dp).weight(0.7f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item { Spacer(Modifier.height((160.dp - 70.dp) / 2 - 12.dp)) }
                items(commaRange.size) { index ->
                    val itemValue = commaRange[index]
                    HeightBoxItem(
                        value = itemValue,
                        isSelected = (itemValue == currentTextFieldDecimalValue),
                        defaultWidth = 71.dp
                    )
                }
                item { Spacer(Modifier.height((160.dp - 70.dp) / 2 - 12.dp)) }
            }
            Spacer(Modifier.weight(0.5f)) // Untuk keseimbangan layout jika unit di sisi lain
        }
    }
}

/**
 * TextField kustom yang hanya menerima input numerik.
 */
@Composable
fun CustomNumericTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.headlineMedium.copy(
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface // Warna teks default
    ),
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        textStyle = textStyle,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(vertical = 8.dp, horizontal = 12.dp),
        decorationBox = { innerTextField ->
            Box(contentAlignment = Alignment.Center) {
                // Tidak ada placeholder "0" di sini agar bisa benar-benar kosong sesaat
                innerTextField()
            }
        }
    )
}

/**
 * Modifier untuk tombol radio pilihan unit.
 */
@Composable
fun getModifierForUnitSelection(
    isSelected: Boolean,
): Modifier {
    return Modifier
        .size(width = 60.dp, height = 40.dp)
        .background(
            color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), // Warna tidak terpilih
            shape = RoundedCornerShape(9.dp)
        )
        .border(
            BorderStroke(
                1.dp,
                if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
            ),
            shape = RoundedCornerShape(9.dp)
        )
}

/**
 * Composable untuk satu opsi tombol radio.
 */
@Composable
fun radioButtonOption(
    selected: Boolean,
    onClick: () -> Unit,
    label: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = getModifierForUnitSelection(isSelected = selected).clickable(onClick = onClick)
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Modifier untuk item yang bisa dipilih di LazyColumn.
 */
@Composable
fun getModifierForSelection(
    isSelected: Boolean,
    height: Dp,
    width: Dp
): Modifier {
    val targetColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f) // Warna tidak terpilih
    val targetBorderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.3f)
    val targetShape = RoundedCornerShape(if (isSelected) 16.dp else 12.dp)

    return Modifier
        .size(
            width = if (isSelected) width else width - 19.dp,
            height = if (isSelected) height else height - 16.dp
        )
        .background(color = targetColor, shape = targetShape)
        .border(BorderStroke(1.dp, targetBorderColor), shape = targetShape)
}

/**
 * Composable untuk satu item angka di LazyColumn.
 */
@Composable
fun HeightBoxItem(
    value: Int?, // Sekarang menerima nullable Int
    isSelected: Boolean,
    defaultHeight: Dp = 70.dp,
    defaultWidth: Dp = 70.dp,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = getModifierForSelection(isSelected, defaultHeight, defaultWidth)
    ) {
        if (value != null) {
            Text(
                text = value.toString(),
                fontWeight = FontWeight.SemiBold,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                fontSize = if (isSelected) 32.sp else 20.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        } else {
            // Box kosong untuk placeholder jika value null (seharusnya tidak terjadi dengan items(range.size))
            Spacer(Modifier.size(defaultWidth - 19.dp, defaultHeight - 16.dp ))
        }
    }
}

// Contoh Penggunaan dengan Preview
@Preview(showBackground = true)
@Composable
fun CustomScrollInputPreview() {
    MaterialTheme { // Pastikan ada MaterialTheme di root aplikasi Anda
        var currentValue by remember { mutableStateOf(0f) }
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CustomScrollInput(
                initialValue = 165.5f,
                onValueChanged = { newValue ->
                    currentValue = newValue
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text("Nilai Saat Ini: $currentValue", fontSize = 18.sp)
        }
    }
}