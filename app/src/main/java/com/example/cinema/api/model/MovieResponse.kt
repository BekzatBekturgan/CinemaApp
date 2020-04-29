package com.example.cinema.api.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MoviesData>
    //@SerializedName("total_pages") val pages: Int
)
