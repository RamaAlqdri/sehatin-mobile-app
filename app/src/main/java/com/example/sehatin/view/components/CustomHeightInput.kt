import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.Dp

@Composable
fun CustomHeightInput(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
) {


    val heightRange = (0..200).toList()
    val weightRange = (0..99).toList()

    // LazyListState for height and weight columns
    val heightListState = rememberLazyListState()
    val weightListState = rememberLazyListState()

    // Derive the selected height and weight from the middle item in the viewport

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Height Picker
        LazyColumn(
            state = heightListState,
            modifier = Modifier.height(160.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(heightRange.size) { index ->
                val isSelected = (index == heightListState.firstVisibleItemIndex + 1)
                HeightBoxItem(index = index, isSelected = isSelected, range = heightRange)
            }
        }

        Text(
            text = ",",
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )

        // Weight Picker
        LazyColumn(
            state = weightListState,
            modifier = Modifier.height(160.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(weightRange.size) { index ->
                val isSelected = (index == weightListState.firstVisibleItemIndex + 1)
                HeightBoxItem(
                    index = index, isSelected = isSelected, range = weightRange,
                    defaultWidth = 71.dp
                )
            }
        }

        // Units (e.g., cm and kg)
        Column(
            modifier = Modifier.padding(start = 18.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "cm", fontSize = 18.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "ft", fontSize = 18.sp, color = Color.Gray)
        }
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
    defaultWidth: Dp = 84.dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = getModifierForSelection(isSelected, defaultHeight, defaultWidth)
    ) {
        Text(
            text = range[index].toString(),
            fontWeight = FontWeight.SemiBold,
            color = if (isSelected) Color.Black else Color.Gray,
            fontSize = if (isSelected) 31.sp else 22.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}
