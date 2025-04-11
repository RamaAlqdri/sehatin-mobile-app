package com.example.sehatin.data.model.response

import com.google.gson.annotations.SerializedName

data class CaloriesADayRequest(
    @field:SerializedName("date")
    val date: String
)

data class DietProgressResponse(

    @field:SerializedName("data")
    val data: Double,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("statusCode")
    val statusCode: Int,

    @field:SerializedName("timestamp")
    val timestamp: String
)

data class CaloriesADayResponse(

    @field:SerializedName("data")
    val data: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("statusCode")
    val statusCode: Int,

    @field:SerializedName("timestamp")
    val timestamp: String
)

data class WaterADayResponse(

    @field:SerializedName("data")
    val data: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("statusCode")
    val statusCode: Int,

    @field:SerializedName("timestamp")
    val timestamp: String
)

data class ScheduleADayResponse(

    @field:SerializedName("data")
    val data: List<DataItem>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("statusCode")
    val statusCode: Int,

    @field:SerializedName("timestamp")
    val timestamp: String
)

data class DataItem(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("calories_burned")
    val caloriesBurned: String,

    @field:SerializedName("water_consum")
    val waterConsum: String,

    @field:SerializedName("scheduled_at")
    val scheduledAt: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("is_completed")
    val isCompleted: Boolean
)


