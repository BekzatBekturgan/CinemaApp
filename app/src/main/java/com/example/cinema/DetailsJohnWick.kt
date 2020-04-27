package com.example.cinema

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DetailsJohnWick : AppCompatActivity() {
    private lateinit var btnBack: Button
    private lateinit var likeView: Button
    private var liked:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_johnwick)
        btnBack = findViewById(R.id.buttonBack)
        btnBack.setOnClickListener{
            finish()
        }
            likeView = findViewById(R.id.buttonLike)
            likeView.setOnClickListener { getLike() }
    }
    private fun getLike(){
        if (liked == false) {
            likeView.setBackgroundResource(R.drawable.heart_red)
            liked=true
        } else {
            likeView.setBackgroundResource(R.drawable.heart_white)
            liked=false
        }

    }

}