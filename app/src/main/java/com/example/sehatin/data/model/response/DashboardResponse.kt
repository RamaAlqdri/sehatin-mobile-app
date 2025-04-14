package com.example.sehatin.data.model.response

import com.google.gson.annotations.SerializedName

data class CaloriesADayRequest(
    @SerializedName("date") val date: String
)

data class WaterHistoryRequest(
    @SerializedName("water") val water: Double
)

data class CreateWaterHistoryResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("statusCode")
    val statusCode: Int,

    @field:SerializedName("timestamp")
    val timestamp: String
)

data class DietProgressResponse(

    @field:SerializedName("data") val data: ProgressItem,

    @field:SerializedName("message") val message: String,

    @field:SerializedName("statusCode") val statusCode: Int,

    @field:SerializedName("timestamp") val timestamp: String
)

data class CaloriesADayResponse(

    @field:SerializedName("data") val data: CaloriesItem,

    @field:SerializedName("message") val message: String,

    @field:SerializedName("statusCode") val statusCode: Int,

    @field:SerializedName("timestamp") val timestamp: String
)

data class WaterHistoryResponse(

    @field:SerializedName("data") val data: List<WaterHistoryItem>,

    @field:SerializedName("message") val message: String,

    @field:SerializedName("statusCode") val statusCode: Int,

    @field:SerializedName("timestamp") val timestamp: String
)

data class WaterHistoryItem(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("water") val calories: Double,
    @field:SerializedName("createdAt") val createdAt: String,
)

data class CaloriesHistoryResponse(

    @field:SerializedName("data") val data: List<CaloriesHistoryItem>,

    @field:SerializedName("message") val message: String,

    @field:SerializedName("statusCode") val statusCode: Int,

    @field:SerializedName("timestamp") val timestamp: String
)

data class CaloriesHistoryItem(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("calories") val calories: Double,
    @field:SerializedName("createdAt") val createdAt: String,
    )

data class WaterADayResponse(

    @field:SerializedName("data") val data: WaterItem,

    @field:SerializedName("message") val message: String,

    @field:SerializedName("statusCode") val statusCode: Int,

    @field:SerializedName("timestamp") val timestamp: String
)

data class ScheduleADayResponse(

    @field:SerializedName("data") val data: List<ScheduleDataItem>,

    @field:SerializedName("message") val message: String,

    @field:SerializedName("statusCode") val statusCode: Int,

    @field:SerializedName("timestamp") val timestamp: String
)

data class CaloriesItem(
    @field:SerializedName("calories") val calories: Double,
    @field:SerializedName("target") val target: Double,

    )

data class WaterItem(
    @field:SerializedName("water") val water: Double,
    @field:SerializedName("target") val target: Double,

    )

data class ScheduleDataItem(
    @field:SerializedName("id") val id: String,

    @field:SerializedName("calories_burned") val caloriesBurned: String,

    @field:SerializedName("water_consum") val waterConsum: String,

    @field:SerializedName("calories_target") val caloriesTarget: String,

    @field:SerializedName("water_target") val waterTarget: String,

    @field:SerializedName("scheduled_at") val scheduledAt: String,

    @field:SerializedName("createdAt") val createdAt: String,

    @field:SerializedName("is_completed") val isCompleted: Boolean,

    @field:SerializedName("food") val food: FoodItem
)

data class FoodItem(
    @field:SerializedName("id") val id: String,

    @field:SerializedName("name") val name: String,

    @field:SerializedName("description") val description: String,

    @field:SerializedName("calories") val calories: String,

    @field:SerializedName("protein") val protein: String,

    @field:SerializedName("fat") val fat: String,

    @field:SerializedName("carb") val carb: String,

    @field:SerializedName("fiber") val fiber: String,

    @field:SerializedName("image") val image: String
)

data class ProgressItem(
    @field:SerializedName("persentase") val persentase: Double,

    @field:SerializedName("short_message") val short_message: String,

    @field:SerializedName("desc") val desc: String,
)


