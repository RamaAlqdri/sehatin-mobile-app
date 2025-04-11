package com.example.sehatin.data.model.response

import com.google.gson.annotations.SerializedName

data class PersonalizeResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int,

	@field:SerializedName("timestamp")
	val timestamp: String
)

data class NameRequest(
	@field:SerializedName("name")
	val name: String
)

data class BirthdayRequest(
	@field:SerializedName("birthday")
	val birthday: String
)

data class GenderRequest(
	@field:SerializedName("gender")
	val gender: String
)

data class HeightRequest(
	@field:SerializedName("height")
	val height: Float
)

data class WeightRequest(
	@field:SerializedName("weight")
	val weight: Float
)

data class ActivityRequest(
	@field:SerializedName("activity")
	val activity: String
)

data class GoalRequest(
	@field:SerializedName("goal")
	val goal: String
)


