package com.example.cinema

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cinema.api.model.MoviesData
import kotlinx.android.synthetic.main.activity_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsJohnWick : AppCompatActivity() {
     val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
    private lateinit var imageView: ImageView
    private lateinit var movie_title: TextView
    private lateinit var movie_overview: TextView
    private lateinit var movie_release_date: TextView
    private lateinit var movie_rating: TextView
    private lateinit var movie_runtime: TextView
    private lateinit var movie_budget: TextView
    private lateinit var movie_revenue: TextView
    private lateinit var btnBack: Button
        /////////////////////////////////////////////////////
        //
        //      Хотел запустить его когда нажимаешь на постер , но чет ошибка выходит , думаю лишним не будет пусть пока что стоит тут
        //
        ////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_johnwick)

        btnBack = findViewById(R.id.buttonBack)


        btnBack.setOnClickListener{
            finish()
        }

    }


}