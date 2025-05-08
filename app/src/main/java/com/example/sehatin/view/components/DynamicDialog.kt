package com.example.sehatin.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehatin.R

@Composable
fun DynamicDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit,
    onConfirm: (() -> Unit)? = null,
    confirmText: String? = null,
    dismissText: String? = null,
    icon: Painter? = null,
    isError: Boolean = false,
    isSuccess: Boolean = false,
    isWarning: Boolean = false
) {
    val vectorImages = listOf(
        R.drawable.success,
        R.drawable.warning,
        R.drawable.fail
    )

    val color = when {
        isError -> MaterialTheme.colorScheme.error
        isSuccess -> MaterialTheme.colorScheme.primary // green
        isWarning -> Color(0xFFE86F1F) // yellow
        else -> MaterialTheme.colorScheme.primary
    }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            dismissText?.let {
                CustomButton(
                    text = dismissText,
                    modifier = Modifier.fillMaxWidth(),
                    isOutlined = true,
                    textColor = color,
                    onClick = {
//                        onConfirm?.invoke()
                        onDismiss()
                    },
                    outlinedColor = color

                )
            }
        },
        confirmButton = {
            confirmText?.let {

                CustomButton(
                    text = confirmText,
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = color,
                    onClick = {
                        onConfirm?.invoke()
                        onDismiss()
                    },
                )
            }
        },

        icon = {
            if (icon != null) {
                Icon(painter = icon, contentDescription = null)
            } else {
//                val iconColor = when {
//                    isError -> MaterialTheme.colorScheme.error
//                    isSuccess -> MaterialTheme.colorScheme.primary // green
//                    isWarning -> Color(0xFFFFC107) // yellow
//                    else -> MaterialTheme.colorScheme.primary
//                }
//                Icon(
//                    imageVector =
//
//
//                        Icons.Default.CheckCircle,
//                    contentDescription = null,
//                    tint = iconColor,
//                    modifier = androidx.compose.ui.Modifier
//                        .size(100.dp)
////                        .padding(8.dp)
//                )
                val iconIndex = when {
                    isError -> 2 // Fail icon
                    isSuccess -> 0 // Success icon
                    isWarning -> 1 // Warning icon
                    else -> 2 // Default to Fail icon
                }
                Image(
                    painter = painterResource(id = vectorImages[iconIndex]),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                )
            }
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal, color = Color.DarkGray
                ),
                lineHeight = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()

            )
        }
    )
}
