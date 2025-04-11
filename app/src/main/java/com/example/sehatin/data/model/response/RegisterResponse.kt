package com.example.sehatin.data.model.response

import com.google.gson.annotations.SerializedName


data class RegisterRequest(

	val email: String,

	val password: String,
)

data class RegisterResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int,

	@field:SerializedName("timestamp")
	val timestamp: String
)
