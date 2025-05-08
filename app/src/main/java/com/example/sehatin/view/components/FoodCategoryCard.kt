package com.example.sehatin.view.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import com.example.sehatin.R
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.compose.textColor
import com.example.sehatin.data.model.response.DietResponse.FoodHistoryItem
import com.example.sehatin.navigation.DetailDestinations
import com.example.sehatin.utils.capitalizeWords
import com.example.sehatin.viewmodel.DietViewModel

@Composable
fun FoodCategoryCard(
    index: Int,
    categoryName: String,
    iconColor: Color,
    icon: Painter,
    items: List<FoodHistoryItem>,
    onAddItemClick: () -> Unit,
    navigateToDetail: (String) -> Unit,
    dietViewModel: DietViewModel
) {
    var expanded by remember { mutableStateOf(false) }
    val totalCalories = items.sumOf { it.calories }

    Column(
        verticalArrangement = Arrangement.spacedBy(-40.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp)
            .padding(bottom = 12.dp)

    ) {
        // Card Header
        Card(
            modifier = Modifier
                .shadow(elevation = 2.5.dp, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 12.dp),

//            elevation = 4.dp,
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        ) {
            Column(

            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = icon,
                        contentDescription = categoryName,
                        modifier = Modifier.size(32.dp),
                        tint = iconColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = categoryName,
//                        style = MaterialTheme.typography.titleMedium
                        color = MaterialTheme.colorScheme.onBackground, fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    if (totalCalories.toInt() != 0) {

                        Row {

                            Text(
                                "${totalCalories}",
                                color = Color(0xFFF7931A),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                " kcal", color = Color(0xFFF8A8A8A), fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                    }


                    IconButton(
                        onClick = onAddItemClick

                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_item),
                            contentDescription = "Expand/Collapse",
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
//                    IconButton(onClick = { expanded = !expanded }) {
//                        Icon(
//                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
//                            contentDescription = "Expand/Collapse"
//                        )
//                    }
                }
            }

        }


        if (!items.isEmpty()) {

            Column(

                modifier = Modifier
                    .fillMaxWidth()
//                .absoluteOffset(y = - 40.dp)

//                .offset(y = -40.dp)
                    .animateContentSize(
                        animationSpec = tween(durationMillis = 300) // Smooth transition animation
                    )
                    .zIndex(-1f)

                    .padding(vertical = 8.dp)
//                .shadow(elevation = 2.5.dp, RoundedCornerShape(16.dp))
                    .background(color = Color(0xFFE0E0E0), RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp)
//                .wrapContentHeight()
//                .wrapContentSize()
                    .padding(top = 28.dp, bottom = 4.dp),

//                    .height(1.dp)

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()

                ) {
                    Text(
                        text = "${items.size} item",
//                        style = MaterialTheme.typography.titleMedium
                        color = Color(0xFF717171), fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                    )

                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "Expand/Collapse",
                            tint = Color(0xFF717171),

                            )
                    }

                }

                if (expanded) {

                    items.forEach {

                        DashedLine(
                            color = Color(0xFFBCBCBC),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {
                                        navigateToDetail(DetailDestinations.foodDetailRouteWithId(it.food.id))
                                        dietViewModel.setSelectedFoodOption(
                                            dietViewModel.foodCategoryOptions.get(index)
                                        )
                                    }
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)


                            ) {
                                Text(
                                    text = it.food.name,
//                        style = MaterialTheme.typography.titleMedium
                                    color = MaterialTheme.colorScheme.primary, fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                                Text(
                                    text = "${it.serving_amount.toInt()} ${it.serving_unit.capitalizeWords()}",
//                        style = MaterialTheme.typography.titleMedium
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                )

                            }
                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.weight(0.3f)
                            ) {

                                Text(
                                    "${it.calories}", color = Color(0xFFF7931A), fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                                Text(
                                    " kcal",
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                )
                            }

                        }
                        Spacer(modifier = Modifier.height(8.dp))

                    }
                }


            }
        }


    }
}

data class FoodCardItem(val name: String, val calories: Int)

//@Composable
//fun PreviewFoodCategoryCard() {
//    FoodCategoryCard(
//        categoryName = "Sarapan",
//        icon = painterResource(id = R.drawable.calories), // Use an icon for breakfast
//        items = listOf(
//            FoodItem("Ikan", 300),
//            FoodItem("Roti", 300)
//        ),
//        onAddItemClick = {
//            // Add item functionality
//        }
//    )
//}
