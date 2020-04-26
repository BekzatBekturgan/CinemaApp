package com.example.cinema.api.model

import com.google.gson.annotations.SerializedName

data class FavouriteMovieResponse (
    @SerializedName("results")
    val results: List<FavMovies>
)