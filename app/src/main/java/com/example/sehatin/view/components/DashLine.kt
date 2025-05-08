package com.example.sehatin.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp


@Composable
fun DashedLine(
    color: Color = Color.Gray,
    strokeWidth: Float = 2f,
    dashLength: Float = 12f,
    gapLength: Float = 20f,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(3.dp)
) {
    Canvas (modifier = modifier) {
        val paint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            this.color = color.toArgb()
            this.strokeWidth = this@Canvas.size.height
            strokeCap = android.graphics.Paint.Cap.ROUND
            style = android.graphics.Paint.Style.STROKE
            pathEffect = android.graphics.DashPathEffect(floatArrayOf(dashLength, gapLength), 0f)
        }

        drawContext.canvas.nativeCanvas.drawLine(
            0f,
            size.height / 2,
            size.width,
            size.height / 2,
            paint
        )
    }
}
