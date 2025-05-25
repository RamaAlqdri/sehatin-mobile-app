package com.example.sehatin.data.model.response

import com.google.gson.annotations.SerializedName

data class GetUserResponse(

	@field:SerializedName("data")
	val data: Detail,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int,

	@field:SerializedName("timestamp")
	val timestamp: String
)


data class Detail(

	@field:SerializedName("birthday")
	val birthday: String?,

	@field:SerializedName("goal")
	val goal: String?,

	@field:SerializedName("gender")
	val gender: String?,

	@field:SerializedName("activity")
	val activity: String?,

	@field:SerializedName("bmr")
	val bmr: String,

	@field:SerializedName("weight")
	val weight: String?,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("verified_at")
	val verifiedAt: String,

	@field:SerializedName("name")
	val name: String?,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("height")
	val height: String?,

	@field:SerializedName("weight_target")
	val weight_target: String?,

	@field:SerializedName("bmi")
	val bmi: String
) {

	fun isProfileComplete(): Boolean {
		return !name.isNullOrBlank() &&
				!height.isNullOrBlank() &&
				!weight.isNullOrBlank() &&
				!birthday.isNullOrBlank() &&
				!gender.isNullOrBlank() &&
				!activity.isNullOrBlank() &&
				!goal.isNullOrBlank()
	}

}
