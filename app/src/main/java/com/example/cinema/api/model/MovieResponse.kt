package com.example.cinema.api.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<MoviesData>,
    @SerializedName("total_pages") val pages: Int
) {

}
