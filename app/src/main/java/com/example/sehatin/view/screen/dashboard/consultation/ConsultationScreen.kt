package com.example.sehatin.view.screen.dashboard.consultation

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.back
import com.example.compose.textColor
import com.example.sehatin.R
import com.example.sehatin.common.MessageData
import com.example.sehatin.navigation.SehatInSurface
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ConsultationScreen(modifier: Modifier = Modifier) {
    ConsultationScreen(
        modifier = modifier,
        id = 0
    )
}

@Composable
private fun ConsultationScreen(
    modifier: Modifier = Modifier,
    id: Int = 0
) {
    val messages = remember { mutableStateListOf<MessageData>() }
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val view = LocalView.current

    fun isLastItemVisible(): Boolean {
        return listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == messages.size - 1
    }

    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.height
            val keypadHeight = screenHeight - rect.bottom
            if (keypadHeight > screenHeight * 0.15) {
                if (isLastItemVisible()) {
                    coroutineScope.launch {
                        listState.animateScrollToItem(messages.size - 1)
                    }
                }
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    SehatInSurface(
        modifier = modifier
            .imePadding()
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(back),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 21.dp, end = 21.dp, bottom = 12.dp)
                    .shadow(elevation = 2.dp, RoundedCornerShape(10.dp))
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Konsultasi",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        view.clearFocus()
                    }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 85.dp),
                    state = listState,
                    reverseLayout = false
                ) {
                    items(messages) { message ->
                        ChatBubble(message)
                    }
                }

                ChatInputField(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(start = 21.dp, end = 21.dp, bottom = 20.dp),
                    textState = textState,
                    onTextChange = { textState = it },
                    onSendClick = {
                        if (textState.text.isNotBlank()) {
                            messages.add(MessageData(textState.text, true))

                            coroutineScope.launch {
                                delay(500)
                                messages.add(
                                    MessageData(
                                        "Jawaban dari bot untuk: ${textState.text}",
                                        false
                                    )
                                )

                                if (!isLastItemVisible()) {
                                    listState.animateScrollToItem(messages.size - 1)
                                }
                            }

                            textState = TextFieldValue("")

                            coroutineScope.launch {
                                if (!isLastItemVisible()) {
                                    listState.animateScrollToItem(messages.size - 1)
                                }
                            }
                        }
                    },
                    onFocusChange = { focused ->
                        if (focused && isLastItemVisible()) {
                            coroutineScope.launch {
                                listState.animateScrollToItem(messages.size - 1)
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ChatBubble(message: MessageData) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp, vertical = 8.dp),
        contentAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Text(
            text = message.text,
            fontSize = 12.sp,
            color = if (message.isUser) Color.White else textColor,
            modifier = Modifier
                .widthIn(max = (0.8f * LocalConfiguration.current.screenWidthDp).dp)
                .background(
                    if (message.isUser) MaterialTheme.colorScheme.primary else Color.White,
                    shape = if (message.isUser) RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 0.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    ) else RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                )
                .padding(12.dp)
        )
    }
}

@Composable
fun ChatInputField(
    modifier: Modifier = Modifier,
    textState: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
    onSendClick: () -> Unit,
    onFocusChange: (Boolean) -> Unit
) {
    var textFieldHeight by remember { mutableStateOf(50.dp) }
    val maxTextFieldHeight = 300.dp

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = textState,
            onValueChange = { newText ->
                onTextChange(newText)
                val textLength = newText.text.length
                textFieldHeight = when {
                    textLength < 30 -> 50.dp
                    textLength in 30..80 -> 100.dp
                    textLength in 80..150 -> 200.dp
                    else -> maxTextFieldHeight
                }
            },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 12.sp,
                color = textColor
            ),
            placeholder = {
                Text(
                    text = "Ketikkan pesan",
                    color = Color(0xFF9A9CAD),
                    fontSize = 12.sp
                )
            },
            modifier = Modifier
                .shadow(elevation = 1.dp, RoundedCornerShape(10.dp))
                .background(Color.White, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(16.dp))
                .weight(1f)
                .heightIn(min = 50.dp, max = maxTextFieldHeight)
                .onFocusChanged { focusState ->
                    onFocusChange(focusState.isFocused)
                },
            singleLine = false,
            maxLines = 5,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .size(56.dp)
                .clickable(
                    onClick = {
                        onSendClick()
                    },
                )
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.send),
                contentDescription = "Attach File",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}