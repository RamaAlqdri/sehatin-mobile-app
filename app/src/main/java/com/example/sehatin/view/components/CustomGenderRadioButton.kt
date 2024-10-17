package com.example.sehatin.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomGenderRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
    defaultWidth: Dp = 116.dp,
    defaultHeight: Dp = 133.dp,
    modifier: Modifier = Modifier,
    defaultIconSize: Dp = 24.dp,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.secondary,
    icon: Painter? = null,
    iconSpacing: Dp = 8.dp,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
) {
    val borderColor = if (selected) selectedColor else unselectedColor
    val textColor = if (selected) selectedColor else unselectedColor

    Box(

        modifier = modifier
            .background(
                color = borderColor.copy(alpha = 0.1f),
                shape = shape
            )
            .height(defaultHeight)
            .width(defaultWidth)
            .clickable(onClick = onClick)
//            .padding(16.dp)
            .let {
                if (selected) {
                    it.border(BorderStroke(1.dp, borderColor), shape = RoundedCornerShape(16.dp))
                } else {
                    it // Jika tidak dipilih, kembalikan modifier tanpa border
                }
            },
        contentAlignment = Alignment.CenterStart
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
//                .background(color = Color.Red)
        ){

            if (icon != null) {
                Image(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(defaultIconSize)
                )
                Spacer(modifier = Modifier.width(iconSpacing))
            }
            Text(
                text = label,
                fontSize = 16.sp,
                color = textColor
            )
        }

//        if (selected) {
//            Box(
//                modifier = Modifier
//                    .align(Alignment.CenterEnd)
//                    .size(24.dp)
//                    .background(color = selectedColor, shape = RoundedCornerShape(12.dp))
//            )
//        }
    }
}
