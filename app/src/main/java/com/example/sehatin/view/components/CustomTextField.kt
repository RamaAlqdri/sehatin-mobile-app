package com.example.sehatin.view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehatin.R


@Composable
fun CustomTextField(
    value: String,
    placeholder: String,
    onChange: (String) -> Unit,
    isError: Boolean,
//    icon: ImageVector,
    errorMessage: String,
    isPassword: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    borderWidth: Dp = 1.dp,
    modifier: Modifier = Modifier,
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }



// Floating placeholder
//    val isFocusedOrFilled = value.isNotEmpty()


//    val animatedBorderColor by animateColorAsState(
//        targetValue = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
//        animationSpec = tween(durationMillis = 300)
//    )

//    val placeholderFontSize by animateFloatAsState(
//        targetValue = if (isFocusedOrFilled) 12f else 16f,
//        animationSpec = tween(durationMillis = 10)
//    )

//    val placeholderOffsetY by animateFloatAsState(
//        targetValue = if (isFocusedOrFilled) (-14f) else 0f,
//        animationSpec = tween(durationMillis = 50)
//    )

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val animatedBorderWidth by animateDpAsState(
        targetValue = if (isFocused) 2.dp else borderWidth,
        animationSpec = tween(durationMillis = 100)
    )

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .height(54.dp)
                .fillMaxWidth()
                .border(
                    width = animatedBorderWidth,
                    color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
//                    color = animatedBorderColor,
                    shape = RoundedCornerShape(16.dp)
                )
//                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp)),
            , contentAlignment = Alignment.CenterStart
        ) {
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .padding(start = 21.dp)
//                        .offset(y = placeholderOffsetY.dp)
                )
            }

            BasicTextField(
                value = value,
                interactionSource = interactionSource,
                onValueChange = {
                    if (!it.contains("\n"))
                        onChange(it)
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start

                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction
                ),
                visualTransformation = if (isPassword && !showPassword) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 21.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            innerTextField()
                        }
                        if (isPassword) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clickable(
                                        onClick = { showPassword = !showPassword },
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = if (showPassword) painterResource(id = R.drawable.open_eye) else painterResource(
                                        id = R.drawable.close_eye
                                    ),
                                    contentDescription = if (showPassword) "Show Password" else "Hide Password",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 0.dp)
            )
        }

        if (isError) {
            AnimatedVisibility(
                visible = isError,
                enter = fadeIn(tween(300)) + expandVertically(expandFrom = Alignment.Top, animationSpec = tween(300)),
                exit = fadeOut(tween(200)) + shrinkVertically(shrinkTowards = Alignment.Top, animationSpec = tween(200))
            ) {
                Text(
                    text = errorMessage,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    textAlign = TextAlign.Start
                )
            }
        } else {
            Spacer(modifier = Modifier.height(16.dp)) // Menambahkan spacer tetap saat tidak ada error
        }
    }
}