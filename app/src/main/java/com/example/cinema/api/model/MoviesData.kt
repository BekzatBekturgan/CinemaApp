package com.example.cinema.api.model

import com.google.gson.annotations.SerializedName

data class MoviesData (
    val budget: Int,
    val id: Int,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val rating: Double,
    val categories : String,
    val favorite : Boolean=false,
    @SerializedName("genre_ids")
    val genre_ids: List<Int>?=null
)