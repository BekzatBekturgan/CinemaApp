package com.example.cinema.api.service

import com.example.cinema.TOKEN_KEY
import com.example.cinema.api.model.*
import retrofit2.Call
import retrofit2.http.*
const val api_key = "753b84576c954d96997803298a188f83"
interface UserClient {
    @POST("authentication/token/validate_with_login?api_key=753b84576c954d96997803298a188f83")
    fun login(@Body login:Login): Call<Token>
    // create a new session_id
    @POST("authentication/session/new?api_key=753b84576c954d96997803298a188f83")
    fun getSessionId(@Body requestSessionId : RequestSessionId): Call<SessionId>

    @GET("authentication/token/new?api_key=753b84576c954d96997803298a188f83")
    fun getToken():Call<Token>
    @GET("posts")
    fun getPostList(): Call<List<Post>>
    @GET("posts/{id}")
    fun getPostById(@Path("id") id: Int): Call<Post>
}