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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.sehatin.R

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
//    navController: NavHostController
) {
    val vectorImages = listOf(
        R.drawable.on_boarding_1,
//        R.drawable.ob_2,
//        R.drawable.ob_3,
    )
    val cloudVector = R.drawable.cloud


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
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // Bagian atas dengan latar belakang hijau dan lengkungan ke latar putih
        Box(
            modifier = Modifier

//                .weight(1f)
//                .background(MaterialTheme.colorScheme.background)
//                .background(color = Color.Blue)
        ) {
            BackgroundWithCurve()

            Image(
                painter = painterResource(id = cloudVector),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .offset(x = -50.dp, y = 100.dp)
            )
            Image(
                painter = painterResource(id = cloudVector),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .offset(x = 170.dp, y = 40.dp)
            )
            Image(
                painter = painterResource(id = cloudVector),
                contentDescription = null,
                modifier = Modifier
                    .size(180.dp)
                    .offset(x = 270.dp, y = 170.dp)
            )
            Surface(
                modifier = Modifier
                    .size(10.dp)
                    .offset(x = 100.dp, y = 100.dp),
                shape = CircleShape, // menentukan bentuk lingkaran
                color = MaterialTheme.colorScheme.background // warna latar lingkaran
            ) {
            }
            Surface(
                modifier = Modifier
                    .size(10.dp)
                    .offset(x = 250.dp, y = 150.dp),
                shape = CircleShape, // menentukan bentuk lingkaran
                color = MaterialTheme.colorScheme.background // warna latar lingkaran
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
                painter = painterResource(id = vectorImages[0]),
                contentDescription = null,
                modifier = Modifier
                    .size(325.dp)
                    .align(Alignment.BottomCenter)
            )


        }

        // Bagian bawah dengan latar belakang putih
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
//                .background(color = Color.Red)
        ) {
            Text(
                text = "Welcome to SehatIn",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Ready to start your healthy journey?",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(241.dp)
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