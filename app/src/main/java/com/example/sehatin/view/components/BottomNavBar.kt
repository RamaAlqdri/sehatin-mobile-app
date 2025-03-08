package com.example.sehatin.view.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.bNavBarColor
import com.example.compose.ter
import com.example.sehatin.R
import com.example.sehatin.navigation.SehatInSurface

enum class HomeSections(
    @StringRes val title: Int,
    val icon: Int,
    val route: String
) {
    Dashboard(R.string.dashboard, R.drawable.home, "dashboard"),
    Consultation(R.string.consultation, R.drawable.consultation, "consultation"),
    Diet(R.string.Diet, R.drawable.diet, "Diet"),
    Profile(R.string.profile, R.drawable.profile, "Profile"),
}

@Composable
fun SehatInBottomBar(
    tabs: Array<HomeSections>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    contentColor: Color = bNavBarColor
) {
    val currentIndex = tabs.indexOfFirst { it.route == currentRoute }

    SehatInSurface(
        modifier = modifier,
        color = color,
        contentColor = contentColor
    ) {
        BoxWithConstraints(
            modifier = modifier
                .navigationBarsPadding()
                .fillMaxWidth()
                .height(70.dp)
                .background(Color.White)
                .padding(horizontal = 21.dp),
            contentAlignment = Alignment.Center
        ) {
            with(this) {

                val tabWidth = maxWidth / tabs.size
                val animatedOffset by animateDpAsState(
                    targetValue = (currentIndex * tabWidth.value).dp,
                    animationSpec = tween(durationMillis = 300),
                    label = "bottomBarAnimation"
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(tabWidth, 60.dp)
                            .offset(x = animatedOffset)
                            .background(ter, shape = RoundedCornerShape(12.dp))
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    tabs.forEachIndexed { index, section ->
                        val isSelected = currentIndex == index
                        val tint = if (isSelected) MaterialTheme.colorScheme.primary else Color(
                            0xFF9A9CAD
                        )

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clickable(
                                    onClick = { navigateToRoute(section.route) },
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                )
                                .padding(vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Icon(
                                imageVector = ImageVector.vectorResource(id = section.icon),
                                contentDescription = stringResource(section.title),
                                tint = tint,
                                modifier = Modifier.size(24.dp)
                            )

                            AnimatedVisibility(
                                visible = isSelected,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                Text(
                                    text = stringResource(section.title),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = tint
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}