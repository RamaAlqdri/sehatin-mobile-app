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

data class WeightResponse(

    @field:SerializedName("data") val data: List<WeightHistoryItem>,

    @field:SerializedName("message") val message: String,

    @field:SerializedName("statusCode") val statusCode: Int,

    @field:SerializedName("timestamp") val timestamp: String
)

data class WeightHistoryItem(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("weight") val weight: Double,
    @field:SerializedName("createdAt") val createdAt: String,
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
    @field:SerializedName("water") val water: Double,
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

data class ScheduleClosestResponse(
    @field:SerializedName("data") val data: ScheduleDataItem,

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

    @field:SerializedName("calories_target") val caloriesTarget: String,

    @field:SerializedName("water_target") val waterTarget: String,

    @field:SerializedName("scheduled_at") val scheduledAt: String,

    @field:SerializedName("createdAt") val createdAt: String,

    @field:SerializedName("is_completed") val isCompleted: Boolean,

    @field:SerializedName("food") val food: FoodItem
)

data class FoodDetailResponse(
    @field:SerializedName("data") val data: FoodItem,

    @field:SerializedName("message") val message: String,

    @field:SerializedName("statusCode") val statusCode: Int,

    @field:SerializedName("timestamp") val timestamp: String
)

data class FoodItem(
    @field:SerializedName("id") val id: String,

    @field:SerializedName("name") val name: String,

    @field:SerializedName("description") val description: String,

    @field:SerializedName("calories") val calories: Double,

    @field:SerializedName("protein") val protein: Double,

    @field:SerializedName("fat") val fat: Double,
    @field:SerializedName("serving_amount") val serving_amount: Double,
    @field:SerializedName("serving_unit") val serving_unit: String,

    @field:SerializedName("carb") val carb: Double,

    @field:SerializedName("fiber") val fiber: Double,

    @field:SerializedName("image") val image: String,
)

data class ProgressItem(
    @field:SerializedName("persentase") val persentase: Double,
    @field:SerializedName("target") val target: Double,

    @field:SerializedName("short_message") val short_message: String,

    @field:SerializedName("desc") val desc: String,
)




