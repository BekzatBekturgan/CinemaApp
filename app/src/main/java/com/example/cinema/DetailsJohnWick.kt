package com.example.cinema

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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
    private lateinit var LikeView: Button
    private var liked:Boolean=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_johnwick)

        btnBack = findViewById(R.id.buttonBack)


        btnBack.setOnClickListener{
            finish()
        }
            LikeView = findViewById(R.id.buttonLike)
            LikeView.setOnClickListener { getLike() }
    }
    private fun getLike(){
        if (liked==false ) {
            LikeView.setBackgroundResource(R.drawable.heart_red)
            liked=true

        } else {
            LikeView.setBackgroundResource(R.drawable.heart_white)
            liked=false
        }

    }

}