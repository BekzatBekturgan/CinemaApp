package com.example.cinema.api.model

import com.google.gson.annotations.SerializedName

class Login(user:String, password:String, token: String?){ // класс нужен для логирования
    @SerializedName("username")  var username: String =""
    @SerializedName("password")  var password: String =""
    @SerializedName("request_token")  var request_token:String=""
    init {
        this.username=user
        this.password=password
        this.request_token= token!!
    }
}