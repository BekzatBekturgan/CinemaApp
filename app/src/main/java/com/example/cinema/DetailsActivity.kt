package com.example.cinema

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cinema.api.model.FavMovieInfo
import com.example.cinema.api.model.FavResponse
import com.example.cinema.api.model.MoviesData
import com.example.cinema.api.service.apiKey
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class DetailsActivity : AppCompatActivity(), CoroutineScope {
     val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
    private lateinit var imageView: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieOverview: TextView
    private lateinit var movieReleaseDate: TextView
    private lateinit var movieRating: TextView
    private lateinit var movieRuntime: TextView
    private lateinit var movieBudget: TextView
    private lateinit var movieRevenue: TextView
    private lateinit var movieGenre: TextView

    private lateinit var btnBack: Button
    private  var liked: Boolean? = null

    private lateinit var likeView: Button

   private var sessionId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        Log.e("qwe","123")

        val movieId = intent.getIntExtra("movieId", 1)

        movieTitle = findViewById(R.id.movie_title)
        imageView = findViewById(R.id.movie_poster)
        movieOverview = findViewById(R.id.movie_overview)
        movieReleaseDate = findViewById(R.id.movie_release_date)
        movieBudget = findViewById(R.id.movie_budget)
        movieRating = findViewById(R.id.movie_rating)
        movieRevenue = findViewById(R.id.movie_revenue)
        movieRuntime = findViewById(R.id.movie_runtime)
        movieGenre = findViewById(R.id.movie_genre)


        val genreArray: Array<String> = arrayOf("  Fantasy ", "  Fantastic  ", "  Adventures  " , "  Comedy  " , "  Thriller  ")


        val index = (0..3).random()
        movieGenre.text=genreArray[index]
        btnBack = findViewById(R.id.buttonBack)

        btnBack.setOnClickListener{
            finish()
        }

        likeView = findViewById(R.id.buttonLike)
        sessionId = pref.getString("sessionID", "empty")


       // getMovieById(movieId)
        getMovieByIdCoroutine(movieId)
    }

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
            Log.d("mark", e.toString())
        }
    }

    fun getState(movieId: Int?): Boolean? {
        try {
            if (movieId != null) {

                    RetrofitService.getMovieApi().getMovieState(movieId, apiKey, sessionId)
                    ?.enqueue(object : Callback<MoviesData?> {
                        override fun onFailure(call: Call<MoviesData?>, t: Throwable) {
                            Log.d("fav", "lol")
                        }

                        override fun onResponse(call: Call<MoviesData?>, response: Response<MoviesData?>) {
                            Log.d("pusk", response.toString())
                            if (response.body()?.id == movieId)
                                liked = response.body()?.favorite

                            if (liked == true) {
                                likeView.setBackgroundResource(R.drawable.ic_favorite_black_24dp)
                                Log.d("liked", "correct")

                            }
                            else {
                                likeView.setBackgroundResource(R.drawable.heart_white)
                                Log.d("not liked", "wrong")
                            }
                        }

                    })
            }
            return liked
        } catch (e: Exception) {
            Log.d("mark", e.toString())
        }
        return liked
    }


/*    private fun getMovieById(movieId: Int) {
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
                            movieTitle.text = responseBody.title
                            movieOverview.text = responseBody.overview
                            movieReleaseDate.text = responseBody.releaseDate
                            movieRuntime.text = responseBody.runtime.toString() + " min"
                            movieRevenue.text = responseBody.revenue.toString() + " $"
                            liked = getState(movieId)

                            if (responseBody.rating < 4){
                                movieRating.text = responseBody.rating.toString() + " ★"
                            }
                            else if (responseBody.rating >= 4 && responseBody.rating < 6) {
                                movieRating.text = responseBody.rating.toString() + " ★★"

                            }
                            else if (responseBody.rating >= 6 && responseBody.rating < 7) {
                                movieRating.text = responseBody.rating.toString() + " ★★★"

                            }
                            else if (responseBody.rating >= 7 && responseBody.rating <=8) {
                                movieRating.text = responseBody.rating.toString() + " ★★★★"

                            }
                            else if (responseBody.rating > 8){
                                movieRating.text = responseBody.rating.toString() + " ★★★★★"
                            }
                            likeView?.setOnClickListener(View.OnClickListener {
                                if(liked == false){
                                    liked = true
                                    likeView?.setBackgroundResource(R.drawable.ic_favorite_black_24dp)
                                    markAsFav(FavMovieInfo(true, movieId, "movie"), sessionId)
                                    likeView?.refreshDrawableState()
                                }
                                else {
                                    liked = false
                                    likeView?.setBackgroundResource(R.drawable.heart_white)
                                    markAsFav(FavMovieInfo(false, movieId, "movie"), sessionId)
                                    likeView?.refreshDrawableState()
                                }
                            })
                            //  movie_genre.text = " " + responseBody.categories + " "
                            movieBudget.text = responseBody.budget.toString() + " $"
                            val moviePosterURL = POSTER_BASE_URL + responseBody.posterPath
                            Glide.with(this@DetailsActivity)
                                .load(moviePosterURL)
                                .into(movie_poster);
                        }
                    }
                }
            })
    }*/
private fun getMovieByIdCoroutine(movieId: Int){
    launch {


        val response = RetrofitService.getMovieApi().getMovieByIdCoroutine(movieId)
        if (response.isSuccessful) {
            val responseBody = response.body()
            Log.d("Check", responseBody?.title ?: "google")
            if (responseBody != null) {
                movieTitle.text = responseBody.title
                movieOverview.text = responseBody.overview
                movieReleaseDate.text = responseBody.releaseDate
                movieRuntime.text = responseBody.runtime.toString() + " min"
                movieRevenue.text = responseBody.revenue.toString() + " $"
                liked = getState(movieId)

                if (responseBody.rating < 4){
                    movieRating.text = responseBody.rating.toString() + " ★"
                }
                else if (responseBody.rating >= 4 && responseBody.rating < 6) {
                    movieRating.text = responseBody.rating.toString() + " ★★"

                }
                else if (responseBody.rating >= 6 && responseBody.rating < 7) {
                    movieRating.text = responseBody.rating.toString() + " ★★★"

                }
                else if (responseBody.rating >= 7 && responseBody.rating <=8) {
                    movieRating.text = responseBody.rating.toString() + " ★★★★"

                }
                else if (responseBody.rating > 8){
                    movieRating.text = responseBody.rating.toString() + " ★★★★★"
                }
                likeView?.setOnClickListener(View.OnClickListener {
                    if(liked == false){
                        liked = true
                        likeView?.setBackgroundResource(R.drawable.ic_favorite_black_24dp)
                        markAsFav(FavMovieInfo(true, movieId, "movie"), sessionId)
                        likeView?.refreshDrawableState()
                    }
                    else {
                        liked = false
                        likeView?.setBackgroundResource(R.drawable.heart_white)
                        markAsFav(FavMovieInfo(false, movieId, "movie"), sessionId)
                        likeView?.refreshDrawableState()
                    }
                })
                //  movie_genre.text = " " + responseBody.categories + " "
                movieBudget.text = responseBody.budget.toString() + " $"
                val moviePosterURL = POSTER_BASE_URL + responseBody.posterPath
                Glide.with(this@DetailsActivity)
                    .load(moviePosterURL)
                    .into(movie_poster);

            }
        }

        }
    }
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}