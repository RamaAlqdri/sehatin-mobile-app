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
import com.example.sehatin.view.components.CustomButton
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
//    navController: NavHostController
) {
    val vectorImages = listOf(
        R.drawable.on_boarding_1,
        R.drawable.on_boarding_2,
        R.drawable.on_boarding_3,
        R.drawable.on_boarding_4,
    )


    val titles = listOf(
        stringResource(R.string.on_boarding_tittle_1),
        stringResource(R.string.on_boarding_tittle_2),
        stringResource(R.string.on_boarding_tittle_3),
        stringResource(R.string.on_boarding_tittle_4),
    )
    val descriptions = listOf(
        stringResource(R.string.on_boarding_desc_1),
        stringResource(R.string.on_boarding_desc_2),
        stringResource(R.string.on_boarding_desc_3),
        stringResource(R.string.on_boarding_desc_4),
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
        ButtonSection(pagerState = pagerState)

    }
}

@Composable
fun ButtonSection(
    pagerState: PagerState
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
                .height(100.dp)
                .padding(top = 25.dp)
        ) {

            CustomButton(
                text =
                if (pagerState.currentPage == pagerState.pageCount - 1) {
                    stringResource(id = R.string.sign_up)
                } else {
                    stringResource(id = R.string.on_boarding_next_page)
                },
                isOutlined = true,
                modifier = Modifier,
                onClick = {
                    if (pagerState.currentPage == pagerState.pageCount - 1) {
                        // Logika jika currentPage - 1 memenuhi kondisi
                    } else {
                        navigateToNextPage() // Aksi yang dilakukan jika kondisi tidak terpenuhi
                    }
                }
            )
            if (pagerState.currentPage == pagerState.pageCount - 1) {
                Row {
                    Text(text = stringResource(id = R.string.already_have_acc),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = -0.15.sp
                        )
                    Text(
                        text = " " + stringResource(id = R.string.sign_in),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = -0.15.sp
                    )

                }
            }
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
            BackgroundWithCurve()

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
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(241.dp)
                    .height(55.dp)
            )

        }
    }
}

@Composable
fun BackgroundWithCurve() {
    // Menggunakan Canvas untuk menggambar lengkungan
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.60f)
    ) {
        // Buat Path untuk lengkungan
        val path = Path().apply {
            moveTo(0f, size.height * 0.75f) // Mulai di titik 75% dari atas
            quadraticTo(
                size.width / 2, // Kontrol titik di tengah
                size.height, // Posisi vertikal di bagian bawah
                size.width, // Posisi horizontal di bagian akhir (kanan)
                size.height * 0.75f // Titik akhir dari lengkungan
            )
            lineTo(size.width, 0f) // Garis ke pojok kanan atas
            lineTo(0f, 0f) // Garis ke pojok kiri atas
            close() // Tutup path
        }

        // Gambar latar belakang hijau dengan lengkungan
        drawPath(path = path, color = onBoardingBGcolor) // Hijau
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