package com.example.sehatin.view.screen.dashboard.detail.diet

import SearchBar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.back
import com.example.sehatin.R
import com.example.sehatin.common.FakeData
import com.example.sehatin.common.FoodItem
import com.example.sehatin.navigation.SehatInSurface
import com.example.sehatin.view.components.CustomTopAppBar
import com.example.sehatin.view.components.FoodItemCard
import kotlin.unaryMinus

@Composable
fun FoodListDetail(
    modifier: Modifier = Modifier
) {

    //Never Pass ViewModel here. Pass value from the viewModel. Semua yang ada di sini anggap dari viewmodel
    var textState by remember { mutableStateOf("") }
    var item by remember { mutableStateOf(FakeData.foodItems) }

    FoodListDetail(
        modifier = modifier,
        id = 1,
        item = item,
        query = textState,
        onQueryChange = { textState = it },
        onSearch = {
            item = FakeData.foodItems.filter { foodItem ->
                foodItem.name.contains(it, ignoreCase = true)
            }
        }
    )
}

@Composable
private fun FoodListDetail(
    modifier: Modifier = Modifier,
    item: List<FoodItem>,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    id: Int = 0
) {
    SehatInSurface(
        modifier = modifier.fillMaxSize(),
    ) {
        val focusManager = LocalFocusManager.current
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus()
                },
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .background(back),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CustomTopAppBar(
                    title = "Food List",
                    showNavigationIcon = true
                )
                Spacer(modifier = Modifier.height(14.dp))
                SearchBar(
                    query = query,
                    onQueryChange = onQueryChange,
                    onSearch = onSearch
                )
                Spacer(modifier = Modifier.height(25.dp))
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
                    if (item.isNotEmpty()) {
                        item.forEach { item ->
                            FoodItemCard(
                                food = item,
                                isTimeVisible = false,
                                isBorderVisible = false,
                                backgroundColor = Color(0xFFF3F3F3)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    } else {
                        EmptyItemInfo()
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyItemInfo(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.menu_icon),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "OMG!",
            fontSize = 24.sp,
            color = Color(0xFF999999),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "There is no food available for that name",
            fontSize = 12.sp,
            color = Color(0xFF999999),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Let's try another one",
            fontSize = 12.sp,
            color = Color(0xFF999999),
            textAlign = TextAlign.Center
        )
    }
}