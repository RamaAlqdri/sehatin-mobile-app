package com.example.sehatin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SehatinApp(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Red)
    ) {
        Row(
            modifier = modifier
                .background(color = Color.Green)
                .height(100.dp)
                .fillMaxWidth()
        ) {
        }
        Spacer(
            modifier = modifier
                .height(16.dp)
        )
        Row(
            modifier = modifier
                .background(color = Color.Yellow)
                .height(100.dp)
                .fillMaxWidth()
        ) {
        }
        Row(
            modifier = modifier
                .background(color = Color.Blue)
                .height(100.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = modifier
                    .background(color = Color.Magenta)
                    .width(50.dp)
                    .height(50.dp)
            ) {

            }
            Column(
                modifier = modifier
                    .background(color = Color.White)
                    .width(50.dp)
                    .height(50.dp)
            ) {

            }
            Column(
                modifier = modifier
                    .background(color = Color.Black)
                    .width(50.dp)
                    .height(50.dp)
            ) {

            }

        }
    }
}