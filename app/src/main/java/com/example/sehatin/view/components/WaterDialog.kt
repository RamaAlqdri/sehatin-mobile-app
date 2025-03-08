package com.example.sehatin.view.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sehatin.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import com.example.compose.backGlass
import com.example.compose.numberBack
import com.example.compose.numberPick
import com.example.compose.waterColor
import com.example.compose.waterGlass
import kotlinx.coroutines.launch
import kotlin.compareTo
import kotlin.div
import kotlin.text.compareTo


@Composable
fun CenteredNumberPicker(
    range: IntRange,
    initialValue: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit
) {

    val listState =
        rememberLazyListState(initialFirstVisibleItemIndex = range.indexOf(initialValue))

    val selectedValue by remember {
        derivedStateOf {
            val center = listState.layoutInfo.viewportEndOffset / 2
            val closestItem = listState.layoutInfo.visibleItemsInfo.minByOrNull {
                kotlin.math.abs(it.offset + it.size / 2 - center)
            }
            closestItem?.let {
                range.toList()[it.index]
            } ?: initialValue
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(100.dp)
            .height(140.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, waterGlass, RoundedCornerShape(16.dp))
                .background(backGlass)
                .align(Alignment.Center)
        )

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(vertical = 40.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            flingBehavior = rememberSnapFlingBehavior(listState),
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(range.toList().size) { index ->
                val value = range.toList()[index]
                val isSelected = selectedValue == value

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(60.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) Color.Transparent else numberBack)
                ) {
                    Text(
                        text = value.toString(),
                        fontSize = if (isSelected) 24.sp else 18.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.Black else numberPick
                    )
                }
            }
        }
    }
    LaunchedEffect(selectedValue) {
        onValueChange(selectedValue)
    }
}

@Composable
fun PickerExample(
    selectedValue: Int,
    onSelectedChange: (Int) -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "$selectedValue mL",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        CenteredNumberPicker(
            range = 0..250,
            initialValue = selectedValue,
            onValueChange = onSelectedChange
        )
    }
}

@Composable
fun WaterDialog(
    modifier: Modifier = Modifier,
    onSelectedChange: (Int) -> Unit,
    selectedValue: Int
) {
    var isDialogOpen by remember { mutableStateOf(false) }
    var currentValue by remember { mutableIntStateOf(selectedValue) }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Water Intake",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF7F7F7F),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(100.dp, 40.dp)
                .border(2.dp, waterColor, RoundedCornerShape(20.dp))
                .background(Color.White, RoundedCornerShape(20.dp))
                .clickable { isDialogOpen = true }
        ) {
            Text(
                text = "$selectedValue mL",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }

    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = { isDialogOpen = false },
            title = { Text("Edit Value", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Icon (contoh icon air)
                    Icon(
                        painter = painterResource(id = R.drawable.water),
                        contentDescription = "Water Icon",
                        tint = waterGlass,
                        modifier = Modifier
                            .size(40.dp)
                            .background(backGlass, CircleShape)
                            .padding(8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Input field dengan satuan "mL"
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        PickerExample(
                            selectedValue = selectedValue,
                            onSelectedChange = {
                                currentValue = it
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Row {
                        Button(
                            onClick = {
                                isDialogOpen = false
                            },
                            colors = ButtonDefaults.buttonColors(waterColor),
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            onClick = {
                                isDialogOpen = false
                                onSelectedChange(currentValue)
                            },
                            colors = ButtonDefaults.buttonColors(waterColor),
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Confirm")
                        }
                    }
                }
            },
            confirmButton = {

            },
            dismissButton = {
            }
        )
    }
}