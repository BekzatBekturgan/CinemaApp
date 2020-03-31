package com.example.cinema

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

val APP_PREFERENCES = "mysettings" // name of the file to shared preferences
val TOKEN_KEY = "token"
lateinit var pref: SharedPreferences
// check the branches
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            //Не забудь переключится на свой branch!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }


}
