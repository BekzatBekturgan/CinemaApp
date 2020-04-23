package com.example.cinema.api.model

data class AccountDetails (
    val hash: String,
    val id: Int,
    val iso6391: String,
    val iso31661: String,
    val name: String,
    val includeAdult: Boolean,
    val username: String
)
