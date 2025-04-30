package com.example.sehatin.data.model.response

import com.google.gson.annotations.SerializedName

class ConsultationResponse {


    data class GetMessageResponse(

        @field:SerializedName("data") val data: List<MessageItem>,

        @field:SerializedName("message") val message: String,

        @field:SerializedName("statusCode") val statusCode: Int,

        @field:SerializedName("timestamp") val timestamp: String
    )

    data class SendMessageRequest(
        @SerializedName("content") val content: String,
        @SerializedName("sender") val sender: String,
    )

    data class MessageItem (
        @field:SerializedName("id")
        val id: String,
        @field:SerializedName("sender")
        val sender: String,
        @field:SerializedName("content")
        val content: String,
        @field:SerializedName("createdAt")
        val createdAt: String,
    )

    data class SendMessageResponse(

        @field:SerializedName("message")
        val message: String,

        @field:SerializedName("statusCode")
        val statusCode: Int,

        @field:SerializedName("timestamp")
        val timestamp: String
    )


}