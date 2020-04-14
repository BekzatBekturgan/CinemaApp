package com.example.cinema.ui.favourites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.*
import com.example.cinema.api.model.MovieResponse
import com.example.cinema.api.model.MoviesData
import com.example.cinema.ui.favourites.FavouritesAdapter
import com.example.cinema.ui.favourites.OnItemClickListner
import kotlinx.android.synthetic.main.fragment_movies.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


open class FavouritesFragment: Fragment() {

    lateinit var recyclerView: RecyclerView
    private var moviesAdapter: FavouritesAdapter? = null
    private lateinit var rootView: View
    private lateinit var picture:ImageView
    val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
    private lateinit var movie_post:ImageView
    var sessionId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateComponent()

    }

    private fun onCreateComponent() {
        moviesAdapter = FavouritesAdapter()
    }


    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_favourites, container, false)
        val pref = requireActivity().getSharedPreferences(TOKEN_KEY, Context.MODE_PRIVATE)
        sessionId = pref.getString("sessionID", "empty")
        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

    }

    private fun initView() {
        setUpAdapter()
        inititializeRecyclerView()
    }

    private fun setUpAdapter(){
        moviesAdapter?.setOnItemClickListener(onItemClickListener = object : OnItemClickListner {
            override fun onItemClick(position: Int, view: View) {
                val intent = Intent(activity, DetailsActivity::class.java)
                intent.putExtra("movieId", moviesAdapter!!.getItem(position)?.id)
                startActivity(intent)
            }
        })
        main_layout_pic.setOnClickListener {
            val intent = Intent(activity, DetailsJohnWick::class.java)
            startActivity(intent)
        }
    }

    private fun inititializeRecyclerView() {
        recyclerView = rootView.findViewById(R.id.favouriteMoviesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.adapter = moviesAdapter
        getFavouriteMovies(
            onSuccess = :: onPopularMoviesFetched,
            onError =  :: onError
        )
    }

    private fun getFavouriteMovies(
        onSuccess: (movies: List<MoviesData>) -> Unit,
        onError: () -> Unit
    ) {
        RetrofitService.getMovieApi().getFavouriteMovies(sessionId)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)

                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }





    private fun onPopularMoviesFetched(movies: List<MoviesData>) {
        moviesAdapter?.addItems(movies as ArrayList<MoviesData>)
    }

    private fun onError() {
        Log.e("Error", "Error")
    }

}
