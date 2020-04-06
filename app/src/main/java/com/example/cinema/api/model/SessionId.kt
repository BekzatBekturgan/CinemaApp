package com.example.cinema.api.model

import com.google.gson.annotations.SerializedName

data class SessionId (
    @SerializedName("success") val success: Boolean,
    @SerializedName("session_id") val session_id: String
)