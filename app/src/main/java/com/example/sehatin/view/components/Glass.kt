package com.example.sehatin.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.example.compose.backGlass
import com.example.compose.backValue
import com.example.compose.waterGlass
import com.example.sehatin.R

@Composable
fun WaterIntake(
    onAdd: () -> Unit,
    onMinus: () -> Unit,
    onSelectedChange: (Int) -> Unit,
    currentWater: Int,
    maxWaterValue: Int,
    selectedValue: Int,
    enableButton: Boolean // 👈 Tambahkan ini
) {

    var percentage by remember { mutableIntStateOf(((currentWater.toFloat() / maxWaterValue) * 100).toInt()) }

    LaunchedEffect(currentWater) {
        percentage = ((currentWater.toFloat() / maxWaterValue) * 100).toInt()
    }

    val waterValue = buildAnnotatedString {
        withStyle(style = SpanStyle(color = waterGlass, fontWeight = FontWeight.Bold)) {
            append("$currentWater")
        }
        withStyle(
            style = SpanStyle(
                color = if (currentWater >= maxWaterValue) waterGlass else backValue,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("/$maxWaterValue ml")
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 21.dp)
            .shadow(elevation = 2.5.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(26.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        backGlass,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(1.dp, color = Color.Unspecified, shape = RoundedCornerShape(8.dp))
                    .clickable(
//                        enabled = enableButton && currentWater - selectedValue > 0,
                        onClick =
                            onMinus,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.minus_icon),
                    contentDescription = null,
                    tint = waterGlass,
                    modifier = Modifier
                        .size(20.dp)
                        .graphicsLayer(rotationZ = 180f)
                )
            }
            Spacer(modifier = Modifier.width(45.dp))
            Canvas(modifier = Modifier.size(width = 134.dp, height = 144.dp)) {
                drawRoundedGlassShape(this)
                drawRoundedWaterShape(this, percentage)
            }
            Spacer(modifier = Modifier.width(45.dp))
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        backGlass,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(1.dp, color = Color.Unspecified, shape = RoundedCornerShape(8.dp))
                    .clickable(
                        enabled = enableButton,
                        onClick = onAdd,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.plus_icon),
                    contentDescription = null,
                    tint = waterGlass,
                    modifier = Modifier
                        .size(20.dp)
                        .graphicsLayer(rotationZ = 180f)
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = waterValue,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        WaterDialog(
            onSelectedChange = onSelectedChange,
            selectedValue = selectedValue
        )

    }
}

private fun drawRoundedGlassShape(drawScope: DrawScope) {
    val width = drawScope.size.width
    val height = drawScope.size.height

    val topWidth = width * 0.9f
    val bottomWidth = width * 0.73f
    val cornerRadiusBottom = 50f
    val cornerRadiusTop = 10f

    val path = Path().apply {
        // Top Left Arc
        arcTo(
            rect = Rect(
                left = (width - topWidth) / 2,
                top = 0f,
                right = (width - topWidth) / 2 + 2 * cornerRadiusTop,
                bottom = 2 * cornerRadiusTop
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 90f,
            forceMoveTo = true
        )

        // Top Line
        lineTo((width + topWidth) / 2 - cornerRadiusTop, 0f)

        // Top Right Arc
        arcTo(
            rect = Rect(
                left = (width + topWidth) / 2 - 2 * cornerRadiusTop,
                top = 0f,
                right = (width + topWidth) / 2,
                bottom = 2 * cornerRadiusTop
            ),
            startAngleDegrees = 270f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        // Right Line to bottom arc
        lineTo((width + bottomWidth) / 2, height - cornerRadiusBottom)

        // Bottom Right Arc
        arcTo(
            rect = Rect(
                left = (width + bottomWidth) / 2 - 2 * cornerRadiusBottom,
                top = height - 2 * cornerRadiusBottom,
                right = (width + bottomWidth) / 2,
                bottom = height
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        // Bottom Line
        lineTo((width - bottomWidth) / 2 + cornerRadiusBottom, height)

        // Bottom Left Arc
        arcTo(
            rect = Rect(
                left = (width - bottomWidth) / 2,
                top = height - 2 * cornerRadiusBottom,
                right = (width - bottomWidth) / 2 + 2 * cornerRadiusBottom,
                bottom = height
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        // Left Line to top
        lineTo((width - topWidth) / 2, cornerRadiusTop)

        close()
    }

    drawScope.drawPath(
        path = path,
        color = backGlass,
        style = Fill
    )
}

private fun drawRoundedWaterShape(drawScope: DrawScope, percentage: Int) {
    val width = drawScope.size.width
    val height = drawScope.size.height

    val topWidth = width * 0.9f
    val bottomWidth = width * 0.73f
    val cornerRadius = 50f
    val cornerRadiusTop = 10f

    val cappedPercentage = percentage.coerceIn(0, 100)
    val waterHeight = height * (cappedPercentage / 100f)

    val currentTopWidth = topWidth - ((topWidth - bottomWidth) * (1 - (waterHeight / height)))

    // === Path gelas, digunakan untuk clipPath ===
    val glassPath = Path().apply {
        arcTo(
            rect = Rect(
                left = (width - topWidth) / 2,
                top = 0f,
                right = (width - topWidth) / 2 + 2 * cornerRadiusTop,
                bottom = 2 * cornerRadiusTop
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 90f,
            forceMoveTo = true
        )

        lineTo((width + topWidth) / 2 - cornerRadiusTop, 0f)

        arcTo(
            rect = Rect(
                left = (width + topWidth) / 2 - 2 * cornerRadiusTop,
                top = 0f,
                right = (width + topWidth) / 2,
                bottom = 2 * cornerRadiusTop
            ),
            startAngleDegrees = 270f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        lineTo((width + bottomWidth) / 2, height - cornerRadius)

        arcTo(
            rect = Rect(
                left = (width + bottomWidth) / 2 - 2 * cornerRadius,
                top = height - 2 * cornerRadius,
                right = (width + bottomWidth) / 2,
                bottom = height
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        lineTo((width - bottomWidth) / 2 + cornerRadius, height)

        arcTo(
            rect = Rect(
                left = (width - bottomWidth) / 2,
                top = height - 2 * cornerRadius,
                right = (width - bottomWidth) / 2 + 2 * cornerRadius,
                bottom = height
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        lineTo((width - topWidth) / 2, cornerRadiusTop)

        close()
    }

    // === Path air ===
    val waterPath = Path().apply {
        moveTo((width - currentTopWidth) / 2, height - waterHeight)
        lineTo((width + currentTopWidth) / 2, height - waterHeight)
        lineTo((width + bottomWidth) / 2, height - cornerRadius)

        arcTo(
            rect = Rect(
                left = (width + bottomWidth) / 2 - 2 * cornerRadius,
                top = height - 2 * cornerRadius,
                right = (width + bottomWidth) / 2,
                bottom = height
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        lineTo((width - bottomWidth) / 2 + cornerRadius, height)

        arcTo(
            rect = Rect(
                left = (width - bottomWidth) / 2,
                top = height - 2 * cornerRadius,
                right = (width - bottomWidth) / 2 + 2 * cornerRadius,
                bottom = height
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        close()
    }

    // === Gambar air yang ter-clip dalam gelas ===
    drawScope.clipPath(glassPath) {
        drawPath(
            path = waterPath,
            color = waterGlass,
            style = Fill
        )
    }
}
