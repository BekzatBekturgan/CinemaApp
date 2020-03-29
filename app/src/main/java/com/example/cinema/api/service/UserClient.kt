package com.example.cinema.api.service

import retrofit2.Call
import com.example.cinema.api.model.Login
import com.example.cinema.api.model.Post
import com.example.cinema.api.model.Token
import com.example.cinema.api.model.User
import retrofit2.http.*
const val api_key = "753b84576c954d96997803298a188f83"
interface UserClient {
    @POST("authentication/token/validate_with_login?api_key=753b84576c954d96997803298a188f83")
    fun login(@Body login:Login): Call<Token>

    @GET("authentication/token/new?api_key=753b84576c954d96997803298a188f83")
    fun getToken():Call<Token>

    @GET("posts")
    fun getPostList(): Call<List<Post>>
    @GET("posts/{id}")
    fun getPostById(@Path("id") id: Int): Call<Post>
}