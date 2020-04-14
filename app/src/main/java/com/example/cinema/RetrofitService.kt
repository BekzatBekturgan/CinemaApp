package com.example.cinema

import android.util.Log
import com.example.cinema.api.model.*
import com.example.cinema.api.service.UserClient
import com.example.cinema.api.service.api_key
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

object RetrofitService {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    private lateinit var movieApi: MovieApi
    fun getPostApi(): UserClient {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(UserClient::class.java)
    }
    fun getMovieApi(): MovieApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttp())
            .build()
        movieApi =  retrofit.create(MovieApi::class.java)
        return movieApi
    }
    private fun getOkHttp(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(getLoggingInterceptor())
        return okHttpClient.build()
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(logger = object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("OkHttp", message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    interface MovieApi {

        @GET("authentication/token/new?api_key=753b84576c954d96997803298a188f83")
        fun getToken():Call<Token>

        @GET("movie/popular")
        fun getPopularMovies(
            @Query("api_key") apiKey: String = "753b84576c954d96997803298a188f83",
            @Query("page") page: Int
        ): Call<MovieResponse>

        @GET("movie/{movie_id}")
        fun getMovieById(@Path("movie_id") movieId: Int=1,
                         @Query("api_key") apiKey: String = "753b84576c954d96997803298a188f83")
                :Call<MoviesData>

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
        )
    }

}

