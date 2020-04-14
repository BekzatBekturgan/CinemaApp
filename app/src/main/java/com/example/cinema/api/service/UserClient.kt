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
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String = "753b84576c954d96997803298a188f83",
                         @Query("page") page: Int): Call<MovieResponse>
    @GET("movie/{movie_id}")
    fun getMovieById(@Path("movie_id") movieId: Int=1,
                     @Query("api_key") apiKey: String = "753b84576c954d96997803298a188f83"):Call<MoviesData>

    @GET("account/{account_id}/favorite/movies?api_key=753b84576c954d96997803298a188f83")
    fun getFavouriteMovies(
        //@Path("account_id") id: Int,
        @Query("session_id") sessionId: String?
    ): Call<MovieResponse>

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

    @GET("account?api_key=753b84576c954d96997803298a188f83")
    fun getAccountDetails(
        @Query("session_id") session: String?
    ) : Call<AccountDetails>?

}