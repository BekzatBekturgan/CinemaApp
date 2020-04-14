package com.example.cinema.api.model

import com.google.gson.annotations.SerializedName

class RequestSessionId(token: String?) {
    @SerializedName("request_token")
    var requestToken: String = ""

    init {
        if (token != null) {
            requestToken = token
        }
    }
}