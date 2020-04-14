package com.example.cinema

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.example.cinema.ui.movies.MoviesFragment
import com.example.cinema.R
import com.example.cinema.ui.favourites.FavouritesFragment
import com.example.cinema.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

val APP_PREFERENCES = "mysettings" // имя файла для shared preferences
val TOKEN_KEY = "token"
lateinit var pref: SharedPreferences

class MainActivity : AppCompatActivity() {

private lateinit var toolbar: Toolbar
private lateinit var textView: TextView
private val  mOnNavigationItemSelected = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    when (item.itemId) {
        R.id.navigationMovieView ->  {
            textView.text = "Кино ТВ - Онлайн Фильмы"
            val moviesFragment: Fragment = MoviesFragment()
            openFragment(moviesFragment)
            return@OnNavigationItemSelectedListener true
        }
        R.id.navigationFavouriteView -> {
            textView.text = "Избранное"
            val favouritesFragment: Fragment = FavouritesFragment()
            openFragment(favouritesFragment)
            return@OnNavigationItemSelectedListener true
        }

        R.id.navigationProfileView -> {
            textView.text = "Профиль"
            val profileFragment: Fragment = ProfileFragment()
            openFragment(profileFragment)
            return@OnNavigationItemSelectedListener true
        }
    }
    false
}
    override fun onBackPressed() {
        super.onBackPressed()
        return
    }


    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView=findViewById(R.id.textView)
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navView)
        bottomNavigation.setOnNavigationItemSelectedListener (mOnNavigationItemSelected)

        if (savedInstanceState == null) {
            val fragment = MoviesFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
    }


}
