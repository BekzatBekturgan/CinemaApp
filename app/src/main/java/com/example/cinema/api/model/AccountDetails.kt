package com.example.cinema.api.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "account_details_table")
data class AccountDetails (
    @SerializedName("hash")
    val hash: String,
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("iso6391")
    val iso6391: String,
    @SerializedName("iso31661")
    val iso31661: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("include_adult")
    val includeAdult: Boolean,
    @SerializedName("username")
    val username: String
)
