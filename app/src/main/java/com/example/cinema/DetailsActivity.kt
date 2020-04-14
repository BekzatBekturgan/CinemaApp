package com.example.cinema

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cinema.api.model.FavMovieInfo
import com.example.cinema.api.model.FavResponse
import com.example.cinema.api.model.MoviesData
import com.example.cinema.api.service.api_key
import kotlinx.android.synthetic.main.activity_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.awt.font.NumericShaper
import java.util.*

class DetailsActivity : AppCompatActivity() {
     val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
    private lateinit var imageView: ImageView
    private lateinit var movie_title: TextView
    private lateinit var movie_overview: TextView
    private lateinit var movie_release_date: TextView
    private lateinit var movie_rating: TextView
    private lateinit var movie_runtime: TextView
    private lateinit var movie_budget: TextView
    private lateinit var movie_revenue: TextView
    private lateinit var movie_genre: TextView

    private lateinit var btnBack: Button
    private var liked:Boolean=false

    private lateinit var LikeView: Button

    var sessionId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        Log.e("qwe","123")

        val movieId = intent.getIntExtra("movieId", 1)

        movie_title = findViewById(R.id.movie_title)
        imageView = findViewById(R.id.movie_poster)
        movie_overview= findViewById(R.id.movie_overview)
        movie_release_date = findViewById(R.id.movie_release_date)
        movie_budget = findViewById(R.id.movie_budget)
        movie_rating = findViewById(R.id.movie_rating)
        movie_revenue = findViewById(R.id.movie_revenue)
        movie_runtime = findViewById(R.id.movie_runtime)
        movie_genre = findViewById(R.id.movie_genre)


        val genreArray: Array<String> = arrayOf("  Fantasy ", "  Fantastic  ", "  Adventures  " , "  Comedy  " , "  Thriller  ")


        val index = (0..3).random()
        movie_genre.text=genreArray[index]
        btnBack = findViewById(R.id.buttonBack)

        btnBack.setOnClickListener{
            finish()
        }

        LikeView = findViewById(R.id.buttonLike)
        //LikeView.setOnClickListener { getLike() }
        val pref = getSharedPreferences(TOKEN_KEY, Context.MODE_PRIVATE)
        sessionId = pref.getString("sessionID", "empty")

        getMovieById(movieId)
    }

    /*
    private fun getLike(){
        if (liked==false ) {
                LikeView.setBackgroundResource(R.drawable.heart_red)
            liked=true

        } else {
                LikeView.setBackgroundResource(R.drawable.heart_white)
            liked=false
        }

    }

     */

    fun markAsFav(info: FavMovieInfo, sessionId: String?) {
        try {
                RetrofitService.getMovieApi().addFavList(info, sessionId)
                ?.enqueue(object : Callback<FavResponse> {
                    override fun onFailure(call: Call<FavResponse>, t: Throwable) {
                        Log.d("fav", "lol")
                    }

                    override fun onResponse(
                        call: Call<FavResponse>,
                        response: Response<FavResponse>
                    ) {
                        Log.d("pusk", response.toString())

                    }

                })
        } catch (e: Exception) {
            //Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show()
            Log.d("mark", e.toString())
        }
    }

    fun getState(movieId: Int?): Boolean {
        try {
            if (movieId != null) {

                    RetrofitService.getMovieApi().getMovieState(movieId, api_key, sessionId)
                    ?.enqueue(object : Callback<MoviesData?> {
                        override fun onFailure(call: Call<MoviesData?>, t: Throwable) {
                            Log.d("fav", "lol")
                        }

                        override fun onResponse(call: Call<MoviesData?>, response: Response<MoviesData?>) {
                            Log.d("pusk", response.toString())
                            if (response.body()?.id == movieId)
                                liked = response.body()?.favourite!!
                            if (liked == true) {
                                LikeView?.setBackgroundResource(R.drawable.ic_favorite_black_24dp)

                            } else
                                LikeView?.setBackgroundResource(R.drawable.heart_white)

                        }

                    })
            }
            return liked
        } catch (e: Exception) {
            //Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show()
            Log.d("mark", e.toString())
        }
        return liked
    }


    private fun getMovieById(movieId: Int) {
        RetrofitService.getMovieApi().getMovieById(movieId)
            .enqueue(object : Callback<MoviesData> {
                override fun onFailure(call: Call<MoviesData>, t: Throwable) {
                    Log.e("Error", "Error")
                }
                override fun onResponse(
                    call: Call<MoviesData>,
                    response: Response<MoviesData>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d("Check", responseBody?.title ?: "google")
                        if (responseBody != null) {
                            movie_title.text = responseBody.title
                            movie_overview.text = responseBody.overview
                            movie_release_date.text = responseBody.releaseDate
                            movie_runtime.text = responseBody.runtime.toString() + " min"
                            movie_revenue.text = responseBody.revenue.toString() + " $"



                            if(responseBody.rating < 4){
                                movie_rating.text=responseBody.rating.toString() + " ★"
                            }
                            else if(responseBody.rating >= 4 && responseBody.rating < 6){
                                movie_rating.text=responseBody.rating.toString() + " ★★"

                            }
                            else if(responseBody.rating >=6 && responseBody.rating < 7){
                                movie_rating.text=responseBody.rating.toString() + " ★★★"

                            }
                            else if(responseBody.rating >=7 && responseBody.rating <=8 ){
                                movie_rating.text=responseBody.rating.toString() + " ★★★★"

                            }
                            else if(responseBody.rating > 8 ){
                                movie_rating.text=responseBody.rating.toString() + " ★★★★★"
                            }
                            liked = getState(movieId)
                            LikeView.setOnClickListener(View.OnClickListener {
                                if(!liked){
                                    liked = true
                                    LikeView?.setBackgroundResource(R.drawable.ic_favorite_black_24dp)
                                    /*Toast.makeText(
                                        activity,
                                        "Film added to favList",
                                        Toast.LENGTH_LONG
                                    ).show()
                                     */
                                    markAsFav(FavMovieInfo(true, movieId, "movie"), sessionId)
                                    LikeView.refreshDrawableState()
                                }
                                else{
                                    liked = false
                                    LikeView.setBackgroundResource(R.drawable.heart_white)
                                    markAsFav(FavMovieInfo(false, movieId, "movie"), sessionId)
                                    LikeView.refreshDrawableState()
                                }
                            })
                            //  movie_genre.text = " " + responseBody.categories + " "
                            movie_budget.text = responseBody.budget.toString() + " $"




                            val moviePosterURL = POSTER_BASE_URL + responseBody.posterPath
                            Glide.with(this@DetailsActivity)
                                .load(moviePosterURL)
                                .into(movie_poster);


                        }
                    }
                }
            })

    }



}