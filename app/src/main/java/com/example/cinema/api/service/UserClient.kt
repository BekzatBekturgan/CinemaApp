package com.example.cinema.api.service

import com.example.cinema.api.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

const val apiKey = "753b84576c954d96997803298a188f83"
interface UserClient {
    @POST("authentication/token/validate_with_login?api_key=753b84576c954d96997803298a188f83")
    suspend fun login(@Body login:Login): Response<Token>
    // create a new session_id
    @POST("authentication/session/new?api_key=753b84576c954d96997803298a188f83")
   suspend fun getSessionId(@Body requestSessionId : RequestSessionId): Response<SessionId>

    @GET("authentication/token/new?api_key=753b84576c954d96997803298a188f83")
    suspend fun getToken(): Response<Token>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(@Path("movie_id") movieId: Int = 1,
                     @Query("api_key") apiKey: String = com.example.cinema.api.service.apiKey
    ): Response<MoviesData>
    //get favourite movies by coroutine
    @GET("account/{account_id}/favorite/movies?api_key=753b84576c954d96997803298a188f83")
    suspend fun getFavouriteMoviesCoroutine(
        @Query("session_id") session: String?
    ): Response<FavouriteMovieResponse>

    //get account details by coroutine
    @GET("account?api_key=753b84576c954d96997803298a188f83")
    suspend fun getAccountDetailsCoroutine(
        @Query("session_id") session: String?
    ): Response<AccountDetails>

    // get popular movies by coroutine
    @GET("movie/popular?api_key=753b84576c954d96997803298a188f83")
    suspend fun getPopularMoviesCoroutine(
        @Query("page") page: Int = 10
    ): Response<MovieResponse>

    @Headers("Content-Type:application/json; charset=UTF-8")
    @POST("account/{account_id}/favorite?api_key=753b84576c954d96997803298a188f83")
    fun addFavList(
        @Body movie: FavMovieInfo,
        @Query("session_id") session: String?
    ): Call<FavResponse>

    @GET("movie/{movie_id}/account_states")
    fun getMovieState(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String?,
        @Query("session_id") session: String?
    ): Call<MoviesData?>?
}