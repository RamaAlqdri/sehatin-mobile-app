package com.example.sehatin.data.model.response

import com.google.gson.annotations.SerializedName

data class OtpRequest(

	@field:SerializedName("email")
	val email: String
)

data class OtpResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int,

	@field:SerializedName("timestamp")
	val timestamp: String
)
