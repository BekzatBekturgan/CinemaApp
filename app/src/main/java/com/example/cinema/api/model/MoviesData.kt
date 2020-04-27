package com.example.cinema.api.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
data class MoviesData (
    val budget: Int? = null,
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("revenue")
    val revenue: Long,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("tag_line")
    val tagLine: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("video")
    val video: Boolean? = null,
    @SerializedName("vote_average")
    val rating: Double,
    @SerializedName("categories")
    val categories : String? = null,
    val favorite : Boolean? = null
)