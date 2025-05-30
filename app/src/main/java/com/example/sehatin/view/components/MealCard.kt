package com.example.sehatin.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.compose.sec
import com.example.sehatin.R
import com.example.sehatin.utils.capitalizeWords

@Composable
fun MealCard(
    imageUrl: String,
    title: String, time: String, calories: Double, serving_amount: Double, serving_unit: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(color = Color.Unspecified, shape = RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha=0.3f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
            .clickable(
                onClick =
                    onClick,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
//        Image(
//            painter = painterResource(id = imageRes),
//            contentDescription = title,
//            modifier = Modifier
//                .size(64.dp)
//                .clip(RoundedCornerShape(8.dp))
//        )
        AsyncImage(
            model = imageUrl, // Load image from URL
            contentDescription = title,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title.capitalizeWords(),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.alarm), // Replace with your time icon
                    contentDescription = "Time",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = time,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    painter = painterResource(id = R.drawable.calories), // Replace with your fire icon
                    contentDescription = "Calories",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "$calories kcal",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.width(6.dp))

                Icon(
                    painter = painterResource(id = R.drawable.menu_icon),
                    contentDescription = "Serving",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${serving_amount.toInt()} ${serving_unit.capitalizeWords()}",
//                    overflow = TextOverflow.,
                    maxLines = 1,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary
                )
//                Spacer(modifier = Modifier.width(4.dp))
//                Text(
//                    text = "$serving_amount ",
//                    color = MaterialTheme.colorScheme.primary,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 12.sp
//                )
//                Text(
//                    text = "$serving_unit",
//                    color = MaterialTheme.colorScheme.primary,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 12.sp
//                )
            }
        }
    }
}