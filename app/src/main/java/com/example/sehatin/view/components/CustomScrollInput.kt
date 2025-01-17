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
    firstUnitMax: Int = 300,  // Parameter cmMax
    secondUnitMax: Int = 10,   // Parameter ftMax
    commaMax:Int=99,
    unitOptions: List<Option> = listOf(
        Option(0, "cm"),
        Option(1, "ft")
    )  // Parameter unitOptions
) {

    val firstUnitRange = (-1..firstUnitMax + 1).toList()
    val secondUnitRange = (-1..secondUnitMax + 1).toList()

    var selectedUnitOption by remember { mutableStateOf(unitOptions[0]) }


    val commaRange = (-1..commaMax+1).toList()

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
    // LazyListState for height and weight columns
    val unitListState = rememberLazyListState()
    val commaListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    // Derive the selected height and weight from the middle item in the viewport

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Height Picker
        LazyColumn(
            state = unitListState,
            modifier = Modifier.height(160.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
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

        Text(
            text = ",",
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
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
        Column(
            modifier = Modifier.padding(start = 18.dp),
            horizontalAlignment = Alignment.Start
        ) {
            unitOptions.forEach { option ->
                radioButtonOption(
                    selected = (option == selectedUnitOption),
                    onClick = {
                        selectedUnitOption = option
//                        coroutineScope.launch {
//                            heightListState.scrollToItem(0)
//                        }
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

//fun cmToFt(front: Int, back: Int): Pair<Int, Int> {
//    val totalCm = front + back / 100.0  // Menggabungkan integer dan bagian belakang
//    val totalFeet = totalCm / 30.48
//    val feet = totalFeet.toInt()
//    val inches = ((totalFeet - feet) * 12).toInt()
//    return Pair(feet, inches)
//}
//
//fun ftToCm(feet: Int, inches: Int): Pair<Int, Int> {
//    val totalFeet = feet + inches / 12.0  // Menggabungkan feet dan inci
//    val totalCm = totalFeet * 30.48
//    val cm = totalCm.toInt()
//    val remainingCm = (totalCm - cm) * 100
//    return Pair(cm, remainingCm.toInt())
//}

