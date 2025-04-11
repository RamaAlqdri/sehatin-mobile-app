package com.example.sehatin.data.model.response

import com.google.gson.annotations.SerializedName

data class VerifyOtpRequest(

	@field:SerializedName("otp_code")
	var otpCode: String,

	@field:SerializedName("email")
	var email: String
)

data class VerifyOtpResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int,

	@field:SerializedName("timestamp")
	val timestamp: String
)
