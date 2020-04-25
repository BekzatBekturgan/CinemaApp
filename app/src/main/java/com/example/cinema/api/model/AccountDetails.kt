package com.example.cinema.api.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class AccountDetails (
    val hash: String,
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    val iso6391: String,
    val iso31661: String,
    @SerializedName("name")
    val name: String,
    val includeAdult: Boolean,
    @SerializedName("username")
    val username: String
)
