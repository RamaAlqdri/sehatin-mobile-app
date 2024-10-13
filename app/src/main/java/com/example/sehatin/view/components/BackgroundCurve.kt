package com.example.sehatin.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import com.example.compose.onBoardingBGcolor

@Composable
fun BackgroundCurve(
    modifier: Modifier = Modifier,
    containerHeight: Float = 0.60f,
    curveHeightRatio: Float = 0.75f // Menambahkan parameter untuk mengatur rasio lengkungan
) {
    // Menggunakan Canvas untuk menggambar lengkungan
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(containerHeight)
    ) {
        // Buat Path untuk lengkungan
        val path = Path().apply {
            moveTo(0f, size.height * curveHeightRatio) // Menggunakan curveHeightRatio sebagai titik awal lengkungan
            quadraticTo(
                size.width / 2, // Kontrol titik di tengah
                size.height, // Posisi vertikal di bagian bawah
                size.width, // Posisi horizontal di bagian akhir (kanan)
                size.height * curveHeightRatio // Menggunakan curveHeightRatio sebagai titik akhir lengkungan
            )
            lineTo(size.width, 0f) // Garis ke pojok kanan atas
            lineTo(0f, 0f) // Garis ke pojok kiri atas
            close() // Tutup path
        }

        // Gambar latar belakang hijau dengan lengkungan
        drawPath(path = path, color = onBoardingBGcolor) // Hijau
    }
}
