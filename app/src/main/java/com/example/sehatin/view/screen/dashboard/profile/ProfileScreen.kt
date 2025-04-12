package com.example.sehatin.view.screen.dashboard.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.back
import com.example.compose.textColor
import com.example.sehatin.R
import com.example.sehatin.common.AccountList
import com.example.sehatin.common.getProfileItems
import com.example.sehatin.navigation.SehatInSurface

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    val temp = getProfileItems()
    ProfileScreen(
        modifier = modifier,
        id = 0,
        data = temp
    )
}

@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    id: Int = 0,
    data: List<AccountList>
) {
    SehatInSurface(
        modifier = modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .background(back),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                HeaderSection(
                )
                Spacer(modifier = Modifier.height(20.dp))
                BodySection(
                    data = data
                )
            }
        }
    }
}

@Composable
private fun HeaderSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_pict),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.size(9.dp))
        Text(
            text = "Ramadhani Al-Qadri",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = textColor
        )
        Text(
            text = "ramadhanialqadri12@gmail.com",
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
            color = Color(0xFF9C9C9C)
        )
    }
}

@Composable
private fun BodySection(
    modifier: Modifier = Modifier,
    data: List<AccountList>
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 21.dp)
            .shadow(elevation = 1.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 18.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn (
            modifier = Modifier.fillMaxWidth()
        ){
            itemsIndexed(data) { index, item ->
                val isLastItem = index == data.size - 1
                ItemRow(
                    icon = item.icon,
                    title = item.title,
                    color = item.color
                )

                if (!isLastItem) {
                    Spacer(modifier = Modifier.height(13.dp))
                }
            }
        }
    }
}

@Composable
fun ItemRow(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    color: Color
) {

    Row (
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF3F3F3))
            .padding(vertical = 15.dp, horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = color
        )
    }
}