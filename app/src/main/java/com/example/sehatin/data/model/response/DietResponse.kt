package com.example.sehatin.data.model.response

import com.google.gson.annotations.SerializedName

class DietResponse {
    data class UpdateScheduleResponse(

        @field:SerializedName("message") val message: String,

        @field:SerializedName("statusCode") val statusCode: Int,

        @field:SerializedName("timestamp") val timestamp: String
    )

    data class FoodScheduleUpdate(
        @SerializedName("food_id") val food_id: String
    )

    data class FoodFilterResponse(

        @field:SerializedName("data") val data: List<FoodItem>,

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

        @field:SerializedName("carb") val carb: Double,

        @field:SerializedName("fiber") val fiber: Double,

        @field:SerializedName("image") val image: String
    )
}