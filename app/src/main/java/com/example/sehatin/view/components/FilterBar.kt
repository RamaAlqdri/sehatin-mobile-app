package com.example.sehatin.view.components

import android.content.ContextWrapper
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import java.text.SimpleDateFormat

import java.util.Date
import java.util.Locale
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun FilterBar(
    selectedLabel: String,
    onSelect: (String) -> Unit,
    onDateRangeSelected: (Date, Date) -> Unit // format dd/MM/yyyy
) {
    val startDate = remember { mutableStateOf("") }
    val endDate = remember { mutableStateOf("") }

    // Animatable for scaling button
//    val scale = remember { Animatable(1f) }
//    val coroutineScope = rememberCoroutineScope()

    Row(modifier = Modifier.padding(horizontal = 21.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("H", "M", "B", "R").forEach {
                if (it == "R") return@forEach // jangan render tombol "R", hanya untuk internal logika
                val isSelected = selectedLabel == it
                val _it = it
                // Use state to track whether the button is pressed
                var isPressed by remember { mutableStateOf(false) }

                // Animatable for scaling animation
                val scale = remember { Animatable(1f) }

                // Animate the scale change based on button press
//                LaunchedEffect(isSelected) {
//                    // Animate the scaling when pressed/released
//                    scale.animateTo(
//                        targetValue = if (isSelected) 0.9f else 1f, // Shrink when pressed
////                        animationSpec = tween(durationMillis = 10, easing = EaseIn)
//                    )
//                }

                // Animate background and border color
                val backgroundColor by animateColorAsState(
                    targetValue = if (isSelected) Color(0xFF88B04B) else Color.Transparent,
//                    animationSpec = tween(durationMillis = 10, easing = EaseIn)
                )

                val borderColor by animateColorAsState(
                    targetValue = if (isSelected) Color.Transparent else Color(0xFF88B04B),
//                    animationSpec = tween(durationMillis = 10, easing = EaseIn)
                )

                val textColor by animateColorAsState(
                    targetValue = if (isSelected) Color.White else Color(0xFF88B04B),
//                    animationSpec = tween(durationMillis = 10, easing = EaseIn)
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
//                        .shadow(
//                            elevation = 2.5.dp,
//                            shape = RoundedCornerShape(8.dp),
//                        )
//                        .scale(scale) // Apply scale animation
                        .background(backgroundColor, shape = RoundedCornerShape(8.dp))
//                        .clickable {
//                            // Triggering the selection and color change
//                            onSelect(_it)
//                        }
                        .scale(scale.value) // Apply scale animation
                        .size(40.dp)
                        .border(1.dp, borderColor, shape = RoundedCornerShape(8.dp))
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    // Trigger shrinking effect on press
                                    scale.animateTo(0.9f, animationSpec = tween(durationMillis = 10, easing = FastOutLinearInEasing))
                                    tryAwaitRelease() // Wait until the press is released
                                    // After release, reset the button to normal size
                                    scale.animateTo(1f, animationSpec = tween(durationMillis = 10, easing = FastOutLinearInEasing))
                                    onSelect(_it) // Trigger selection on release
                                },
//                                onTap = {
//                                    // Triggering the selection and color change
//                                    isPressed = true
//                                    onSelect(_it)
//                                    // After tap, reset the button to normal size
//                                    isPressed = false
//                                }
                            )
                        }
                ) {
                    Text(it, color = textColor)
                }
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        CustomDateRangePicker(
            startDate = startDate.value,
            endDate = endDate.value,
            onDateRangeSelected = { start, end ->
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())


                startDate.value = formatter.format(start)
                endDate.value = formatter.format(end)
                onDateRangeSelected(start, end)
            }

        )
    }
}