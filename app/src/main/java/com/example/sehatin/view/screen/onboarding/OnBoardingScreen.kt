package com.example.sehatin.view.screen.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.compose.onBoardingBGcolor
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Path
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.sehatin.R
import com.example.sehatin.navigation.MainDestinations
import com.example.sehatin.view.components.BackgroundCurve
import com.example.sehatin.view.components.CustomButton
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
//    navController: NavHostController
    navigateToRoute: (String, Boolean) -> Unit,
    setOnBoardingCompleted: (Boolean) -> Unit

) {
    val vectorImages = listOf(
        R.drawable.on_boarding_1,
        R.drawable.on_boarding_2,
        R.drawable.on_boarding_4,
    )


    val titles = listOf(
        stringResource(R.string.on_boarding_tittle_1),
        stringResource(R.string.on_boarding_tittle_2),
        stringResource(R.string.on_boarding_tittle_3),
    )
    val descriptions = listOf(
        stringResource(R.string.on_boarding_desc_1),
        stringResource(R.string.on_boarding_desc_2),
        stringResource(R.string.on_boarding_desc_3),
    )
    val pagerState = rememberPagerState(pageCount = { vectorImages.size })
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
        ) { page ->
            TopContainer(
                vectorResId = vectorImages[page],
                title = titles[page],
                description = descriptions[page]
            )

        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            pageCount = vectorImages.size,
            modifier = Modifier
                .padding(top = 40.dp)
                .align(Alignment.CenterHorizontally),
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
        )
        ButtonSection(pagerState = pagerState,
            navigateToRoute = navigateToRoute,
            setOnBoardingCompleted = setOnBoardingCompleted
        )
    }
}

@Composable
fun ButtonSection(
    pagerState: PagerState,
    navigateToRoute: (String, Boolean) -> Unit,
    setOnBoardingCompleted: (Boolean) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    fun navigateToNextPage() {
        coroutineScope.launch {
            pagerState.animateScrollToPage(
                page = pagerState.currentPage + 1
            )
        }
    }

    fun navigateToPreviousPage() {
        coroutineScope.launch {
            if (pagerState.currentPage > 0) {
                pagerState.animateScrollToPage(
                    page = pagerState.currentPage - 1
                )
            }
        }
    }
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(0.dp, 20.dp)
            .fillMaxHeight(1f)
//                .background(color = Color.Blue)
    ) {

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(90.dp)
                .padding(top = 15.dp)
        ) {

            CustomButton(

                text =
                if (pagerState.currentPage == pagerState.pageCount - 1) {
                    stringResource(id = R.string.get_started)
                } else {
                    stringResource(id = R.string.on_boarding_next_page)
                },
                textColor = MaterialTheme.colorScheme.primary,
                isOutlined = true,
                modifier = Modifier,
                onClick = {
                    if (pagerState.currentPage == pagerState.pageCount - 1) {
                        setOnBoardingCompleted(true)
                        navigateToRoute(MainDestinations.LOGIN_ROUTE, true)

                    } else {
                        navigateToNextPage() // Aksi yang dilakukan jika kondisi tidak terpenuhi
                    }
                }
            )
        }
    }
}


@Composable
fun TopContainer(
    vectorResId: Int,
    title: String,
    description: String
) {
    val cloudVector = R.drawable.cloud
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Box(
            modifier = Modifier
        ) {
            BackgroundCurve()

            Image(
                painter = painterResource(id = cloudVector),
                contentDescription = null,
                modifier = Modifier
                    .size(110.dp)
                    .offset(x = 5.dp, y = 120.dp)
            )
            Image(
                painter = painterResource(id = cloudVector),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .offset(x = 180.dp, y = 40.dp)
            )
            Image(
                painter = painterResource(id = cloudVector),
                contentDescription = null,
                modifier = Modifier
                    .size(140.dp)
                    .offset(x = 250.dp, y = 170.dp)
            )
            Surface(
                modifier = Modifier
                    .size(10.dp)
                    .offset(x = 100.dp, y = 100.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.background
            ) {
            }
            Surface(
                modifier = Modifier
                    .size(10.dp)
                    .offset(x = 250.dp, y = 150.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.background
            ) {
            }
            Surface(
                modifier = Modifier
                    .size(10.dp)
                    .offset(x = 320.dp, y = 70.dp),
                shape = CircleShape, // menentukan bentuk lingkaran
                color = MaterialTheme.colorScheme.tertiary // warna latar lingkaran
            ) {
            }
            Image(
                painter = painterResource(id = vectorResId),
                contentDescription = null,
                modifier = Modifier
                    .size(
                        if (vectorResId == R.drawable.on_boarding_2) 270.dp else 320.dp
                    )
                    .align(Alignment.BottomCenter)
                    .padding(
                        bottom = if (vectorResId == R.drawable.on_boarding_2) 40.dp else
                            20.dp
                    )
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
//                .background(color = Color.Red)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.secondary,
                lineHeight = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(270.dp)
                    .height(69.dp)
            )

        }
    }
}


@Composable
fun HorizontalPagerIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = Color.Black,
    inactiveColor: Color = Color.Gray
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        for (i in 0 until pageCount) {
            val color = if (i == pagerState.currentPage) activeColor else inactiveColor
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(9.dp)
                    .background(color, shape = CircleShape)
            )
        }
    }
}