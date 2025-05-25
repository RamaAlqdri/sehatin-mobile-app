package com.example.sehatin.data.model.response

import com.google.gson.annotations.SerializedName

class DietResponse {

    data class CreateFoodHistoryResponse(

        @field:SerializedName("message") val message: String,

        @field:SerializedName("statusCode") val statusCode: Int,

        @field:SerializedName("timestamp") val timestamp: String
    )

    data class FetchFoodHistoryResponse(

        @field:SerializedName("data") val data: FoodHistoryItem,


        @field:SerializedName("message") val message: String,

        @field:SerializedName("statusCode") val statusCode: Int,

        @field:SerializedName("timestamp") val timestamp: String
    )

    data class FetchManyFoodHistoryResponse(

        @field:SerializedName("data") val data: List<FoodHistoryItem>,


        @field:SerializedName("message") val message: String,

        @field:SerializedName("statusCode") val statusCode: Int,

        @field:SerializedName("timestamp") val timestamp: String
    )


    data class FetchSummaryResponse(

        @field:SerializedName("data") val data: SummaryItem,


        @field:SerializedName("message") val message: String,

        @field:SerializedName("statusCode") val statusCode: Int,

        @field:SerializedName("timestamp") val timestamp: String
    )

    data class SummaryItem(
        @field:SerializedName("totalCalories") val totalCalories: Double,
        @field:SerializedName("totalTargetCalories") val totalTargetCalories: Double,
        @field:SerializedName("averageCalories") val averageCalories: Double,
        @field:SerializedName("caloriesPerMealType") val caloriesPerMealType: MealTypeItem,
        @field:SerializedName("groupedCalories") val groupedCalories: Map<String, MealTypeItem>,
        @field:SerializedName("foodHistory") val foodHistory: List<FoodHistoryItem>,

    )

    data class MealTypeItem(
        @field:SerializedName("breakfast") val breakfast: Double,
        @field:SerializedName("lunch") val lunch: Double,
        @field:SerializedName("dinner") val dinner: Double,
        @field:SerializedName("other") val other: Double,

    )

    data class FoodHistoryItem(
        @field:SerializedName("id") val id: String,

        @field:SerializedName("createdAt") val createdAt: String,

        @field:SerializedName("meal_type") val meal_type: String,

        @field:SerializedName("serving_amount") val serving_amount: Double,

        @field:SerializedName("serving_unit") val serving_unit: String,

        @field:SerializedName("calories") val calories: Double,
        @field:SerializedName("food") val food: com.example.sehatin.data.model.response.FoodItem,

    )

    data class CreateFoodHistoryRequest(
        @SerializedName("food_id") val food_id: String,
        @SerializedName("meal_type") val meal_type: String,
        @SerializedName("serving_amount") val serving_amount: Double,
        @SerializedName("date") val date: String,
    )

    data class DeleteFoodHistoryRequest(
        @SerializedName("food_id") val food_id: String,
        @SerializedName("meal_type") val meal_type: String,
        @SerializedName("date") val date: String,
    )

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
        @field:SerializedName("serving_amount") val serving_amount: Double,
        @field:SerializedName("serving_unit") val serving_unit: String,


        @field:SerializedName("carb") val carb: Double,

        @field:SerializedName("fiber") val fiber: Double,

        @field:SerializedName("image") val image: String
    )
}