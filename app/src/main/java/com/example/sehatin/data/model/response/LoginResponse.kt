package com.example.sehatin.data.model.response

import com.google.gson.annotations.SerializedName

data class LoginRequest(

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String
)

data class ChangePasswordAuthRequest(
    @field:SerializedName("old_password")
    val oldPassword: String,

    @field:SerializedName("new_password")
    val newPassword: String
)

data class ResetPasswordRequest(

    @field:SerializedName("new_password")
    val new_password: String
)

data class ChangePasswordResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("statusCode")
    val statusCode: Int,

    @field:SerializedName("timestamp")
    val timestamp: String
)

data class LoginResponse(

    @field:SerializedName("data")
    val data: Data,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("statusCode")
    val statusCode: Int,

    @field:SerializedName("timestamp")
    val timestamp: String
)

data class Data(

    @field:SerializedName("access_token")
    val accessToken: String
)
