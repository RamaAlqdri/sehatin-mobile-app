package com.example.sehatin.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.onPrimaryLight
import com.example.compose.primaryDark
import com.example.compose.primaryLight
import android.util.Log
@Composable
fun CircularProgressBar(
    percentage: Float, // Nilai progress dalam bentuk desimal (0f - 1f)
    modifier: Modifier = Modifier,
    size: Dp = 100.dp, // Ukuran lingkaran
    strokeWidth: Dp = 12.dp, // Ketebalan lingkaran
    backgroundColor: Color = Color.LightGray, // Warna background progress
    progressColor: Color = Color.White, // Warna progress
    textColor: Color = Color.White // Warna teks
) {
    Log.d("CircularProgressBar", "Percentage: $percentage")
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val circleSize = size.toPx() - strokeWidth.toPx()

            // Background lingkaran
            drawArc(
                color = backgroundColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round),
                size = Size(circleSize, circleSize),
                topLeft = Offset(
                    (size.toPx() - circleSize) / 2f,
                    (size.toPx() - circleSize) / 2f
                )
            )

            // Progress lingkaran
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = 360f * (percentage/100),
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round),
                size = Size(circleSize, circleSize),
                topLeft = Offset(
                    (size.toPx() - circleSize) / 2f,
                    (size.toPx() - circleSize) / 2f
                )
            )
        }

        // Teks di tengah progress bar
        Text(
            text = "${(percentage).toInt()}%",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
