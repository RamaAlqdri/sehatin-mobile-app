import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp
import com.example.sehatin.view.screen.authentication.register.personalize.Option


@Composable
fun CustomScrollInput(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    firstUnitMax: Int = 300,  // contoh range cm
    secondUnitMax: Int = 10,  // contoh range ft
    commaMax: Int = 99,       // contoh range bagian koma
    unitOptions: List<Option> = listOf(
        Option(0, "cm"),
        Option(1, "ft")
    ),
    // Callback untuk mengirim nilai Float yang sudah digabung
    onValueChanged: (Float) -> Unit
) {
    val firstUnitRange = (-1..firstUnitMax + 1).toList()
    val secondUnitRange = (-1..secondUnitMax + 1).toList()
    val commaRange = (-1..commaMax + 1).toList()

    var selectedUnitOption by remember { mutableStateOf(unitOptions[0]) }

    // Pilih range mana yang dipakai untuk kolom pertama (integer part)
    val currentUnitRange by remember(selectedUnitOption) {
        derivedStateOf {
            if (selectedUnitOption.index == 0) firstUnitRange else secondUnitRange
        }
    }
    val currentUnitMax by remember(selectedUnitOption) {
        derivedStateOf {
            if (selectedUnitOption.index == 0) firstUnitMax else secondUnitMax
        }
    }

    // State untuk LazyColumn
    val unitListState = rememberLazyListState()   // kolom pertama (angka depan)
    val commaListState = rememberLazyListState()  // kolom kedua (angka setelah koma)

    // Ambil nilai integer dari kolom pertama
    val selectedIntPart by remember {
        derivedStateOf {
            val index = unitListState.firstVisibleItemIndex + 1
            // Pastikan index masih dalam range valid (tidak -1, tidak melebihi boundary)
            if (index in currentUnitRange.indices) {
                currentUnitRange[index]
            } else {
                0
            }
        }
    }

    // Ambil nilai decimal (setelah koma) dari kolom kedua
    val selectedDecimalPart by remember {
        derivedStateOf {
            val index = commaListState.firstVisibleItemIndex + 1
            if (index in commaRange.indices) {
                commaRange[index]
            } else {
                0
            }
        }
    }

    // Gabungkan keduanya menjadi satu Float
    // Misal: 9 (angka depan) + 48/100 = 9.48
    // Anda bisa menambahkan logika konversi berbeda untuk ft/inch jika diperlukan.
    val finalValue by remember(selectedUnitOption, selectedIntPart, selectedDecimalPart) {
        derivedStateOf {
            when (selectedUnitOption.index) {
                0 -> {
                    // Jika unit cm: integer + decimal/100
                    selectedIntPart.toFloat() + (selectedDecimalPart.toFloat() / 100f)
                }
                1 -> {
                    // Jika unit ft: misalnya integer + decimal/100
                    // Atau jika decimal mewakili inches, Anda bisa buat logika konversi lain
                    selectedIntPart.toFloat() + (selectedDecimalPart.toFloat() / 100f)
                }
                else -> 0f
            }
        }
    }

    // Kirim finalValue ke parent setiap kali berubah
    LaunchedEffect(finalValue) {
        onValueChanged(finalValue)
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Kolom pertama (angka depan)
        LazyColumn(
            state = unitListState,
            modifier = Modifier.height(160.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(currentUnitRange.size) { index ->
                val isSelected = (index == unitListState.firstVisibleItemIndex + 1)
                if (index in currentUnitRange.indices) {
                    HeightBoxItem(
                        index = index,
                        isSelected = isSelected,
                        range = currentUnitRange,
                        maxValue = currentUnitMax
                    )
                }
            }
        }

        // Koma
        Text(
            text = ",",
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        // Kolom kedua (angka setelah koma)
        LazyColumn(
            state = commaListState,
            modifier = Modifier.height(160.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(commaRange.size) { index ->
                val isSelected = (index == commaListState.firstVisibleItemIndex + 1)
                if (index in commaRange.indices) {
                    HeightBoxItem(
                        index = index,
                        isSelected = isSelected,
                        range = commaRange,
                        defaultWidth = 71.dp,
                        maxValue = commaMax
                    )
                }
            }
        }

        // Pilihan unit (cm / ft)
        Column(
            modifier = Modifier.padding(start = 18.dp),
            horizontalAlignment = Alignment.Start
        ) {
            unitOptions.forEach { option ->
                radioButtonOption(
                    selected = (option == selectedUnitOption),
                    onClick = {
                        selectedUnitOption = option
                    },
                    label = option.label
                )
                if (option.index == 0) Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun getModifierForUnitSelection(
    isSelected: Boolean,
): Modifier {
    return Modifier
        .let {
            it.size(width = 50.dp, height = 30.dp)
        }
        .let {
            if (isSelected) {
                it.background(color = MaterialTheme.colorScheme.background)
            } else {
                it.background(
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(9.dp)
                )
            }
        }
        .let {
            if (isSelected) {
                it.border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(9.dp)
                )
            } else {
                it // Jika tidak dipilih, kembalikan modifier tanpa border
            }
        }
}


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
        Text(text = label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
fun getModifierForSelection(
    isSelected: Boolean,
    height: Dp,
    width: Dp
): Modifier {
    return Modifier
        .let {
            if (isSelected) {
                it.size(width = width, height = height)
            } else {
                it.size(width = width - 19.dp, height = height - 16.dp)
            }
        }
        .let {
            if (isSelected) {
                it.background(color = MaterialTheme.colorScheme.background)
            } else {
                it.background(
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }
        .let {
            if (isSelected) {
                it.border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(16.dp)
                )
            } else {
                it // Jika tidak dipilih, kembalikan modifier tanpa border
            }
        }
}

@Composable
fun HeightBoxItem(
    index: Int,
    isSelected: Boolean,
    range: List<Int>,
    defaultHeight: Dp = 64.dp,
    defaultWidth: Dp = 84.dp,
    maxValue: Int
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier =
        if (range[index] != -1 && range[index] != maxValue + 1) {
            getModifierForSelection(isSelected, defaultHeight, defaultWidth)
        } else {
            Modifier // Fallback modifier
        }
    ) {
        if (range[index] == -1 || range[index] == maxValue + 1) {
            Text(
                text = "",
                fontWeight = FontWeight.SemiBold,
                color = if (isSelected) Color.Black else Color.Gray,
                fontSize = if (isSelected) 31.sp else 22.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        } else {
            Text(
                text = range[index].toString(),
                fontWeight = FontWeight.SemiBold,
                color = if (isSelected) Color.Black else Color.Gray,
                fontSize = if (isSelected) 31.sp else 22.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}