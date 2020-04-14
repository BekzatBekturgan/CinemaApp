package com.example.cinema.api.model


import com.google.gson.annotations.SerializedName


class AccountDetailsResponse (
    hash: String,
    id: Int?,
    iso_639_1: String,
    iso_3166_1: String,
    name: String,
    include_adult: Boolean,
    username: String
) {
    @SerializedName("hash")
    var hash: String = ""
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("iso_639_1")
    var iso_639_1: String = ""
    @SerializedName("iso_3166_1")
    var iso_3166_1: String = ""
    @SerializedName("name")
    var name: String = ""
    @SerializedName("include_adult")
    var include_adult: Boolean = false
    @SerializedName("username")
    var username: String = ""

    init {
        this.hash = hash
        if (id != null) {
            this.id = id
        }
        this.iso_3166_1 = iso_3166_1
        this.iso_639_1 = iso_639_1
        this.include_adult = include_adult
        this.name = name
        this.username = username

    }
}