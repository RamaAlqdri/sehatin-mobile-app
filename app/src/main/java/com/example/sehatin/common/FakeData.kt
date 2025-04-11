package com.example.sehatin.common

import android.graphics.drawable.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.sehatin.R


data class Meal(
    val title: String,
    val time: String,
    val calories: Int,
    val imageRes: Int
)

data class WaterConsumptionItem(
    val time: String,
    val amount: String
)

data class CaloriesConsumptionItem(
    val time: String,
    val amount: String
)

data class Consumption(
    val time: String,
    val foodType: String
)

data class MessageData(
    val text: String,
    val isUser: Boolean
)

// Model Data
data class FoodItem(
    val name: String,
    val time: String,
    val calories: Int,
    val weight: Int,
    val imageRes: Int // Resource ID untuk gambar
)

data class AccountList(
    val title: String,
    val icon: Int,
    val color: Color
)

data class FoodDetail(
    val name: String,
    val listDetail: List<FoodDetailItem>,
    val description: String
)

data class FoodDetailItem(
    val weight: Int,
    val units: String
)

object FakeData {
    val meals = listOf(
        Meal("Salad", "09.15", 1499, R.drawable.salad),
        Meal("Telur Ceplok", "12.30", 1499, R.drawable.telur),
        Meal("Telur Ceplok", "12.30", 1499, R.drawable.telur),
        Meal("Telur Ceplok", "12.30", 1499, R.drawable.telur),
        Meal("Telur Ceplok", "12.30", 1499, R.drawable.telur),
        Meal("Roti Tawar", "17.30", 1499, R.drawable.roti)
    )

    val foods = listOf(
        FoodDetail(
            "Telur Rebus", listOf(
                FoodDetailItem(10, "kcaL"),
                FoodDetailItem(0, "gram"),
                FoodDetailItem(8, "gram"),
                FoodDetailItem(11, "gram")
            ),
            "Telur rebus adalah sumber protein tinggi yang kaya akan nutrisi penting, seperti vitamin B12, vitamin D, selenium, dan kolin, yang mendukung fungsi otak dan kesehatan tubuh.\n\n" +
                    "Dengan hanya sekitar 68–78 kalori per butir, telur rebus merupakan pilihan yang ideal untuk diet karena rendah kalori, mudah dicerna, dan memberikan rasa kenyang lebih lama.\n\n" +
                    "Kandungan lemak sehatnya membantu memenuhi kebutuhan energi, sementara proses perebusan menjaga nutrisinya tanpa tambahan lemak atau minyak, menjadikannya pilihan praktis dan bergizi."
        ),
    )

    val WaterHistory = listOf(
        WaterConsumptionItem("08.30", "250"),
        WaterConsumptionItem("12.30", "250"),
        WaterConsumptionItem("15.30", "250")
    )

    val CaloriesHistory = listOf(
        CaloriesConsumptionItem("08.30", "250"),
        CaloriesConsumptionItem("12.30", "250"),
        CaloriesConsumptionItem("15.30", "250")
    )

    val ConsumptionHistory = listOf(
        Consumption("08.30", "Breakfast"),
        Consumption("12.30", "Lunch"),
        Consumption("15.30", "Dinner")
    )

    val foodItems = listOf(
        FoodItem("Salad", "12.30", 1499, 500, R.drawable.salad),
        FoodItem("Oatmeal", "08.00", 250, 200, R.drawable.roti),
        FoodItem("Grilled Chicken", "19.00", 600, 300, R.drawable.telur),
        FoodItem("Salad", "12.30", 1499, 500, R.drawable.salad),
        FoodItem("Oatmeal", "08.00", 250, 200, R.drawable.roti),
        FoodItem("Grilled Chicken", "19.00", 600, 300, R.drawable.telur)
    )

}

@Composable
fun getProfileItems(): List<AccountList> {
    return listOf(
        AccountList("Profile", R.drawable.update_data, MaterialTheme.colorScheme.primary),
        AccountList("Settings", R.drawable.lock, MaterialTheme.colorScheme.primary),
        AccountList("Help", R.drawable.logout, Color(0xFFC93E3E))
    )
}