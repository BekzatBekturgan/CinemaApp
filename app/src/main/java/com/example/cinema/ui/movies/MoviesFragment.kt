package com.example.cinema.ui.movies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.cinema.*
import com.example.cinema.api.model.FavouriteMovies
import com.example.cinema.api.model.MovieResponse
import com.example.cinema.api.model.MoviesData
import com.example.cinema.api.room.FavouriteDao
import com.example.cinema.api.room.MovieDao
import com.example.cinema.api.room.MovieDatabase
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext


open class MoviesFragment: Fragment(), CoroutineScope {

    lateinit var recyclerView: RecyclerView
    private var moviesAdapter: MoviesAdapter? = null
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var rootView: View
    private lateinit var picture:ImageView
    val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
    private lateinit var movie_post:ImageView
    var movieDao: MovieDao? = null

    private lateinit var mov: List<MoviesData>

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         onCreateComponent()
    }

    private fun onCreateComponent() {
        moviesAdapter = MoviesAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_movies, container, false)
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayoutMovie)
        movieDao = MovieDatabase.getDatabase(requireContext()).movieDao()

        getMoviesCoroutine()
        swipeRefreshLayout.setOnRefreshListener {
            recyclerView.layoutManager = GridLayoutManager(
                activity,
                1
            )
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = moviesAdapter
            moviesAdapter?.notifyDataSetChanged()

            getMoviesCoroutine()

        }
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
                intent.putExtra("movieId", moviesAdapter?.getItem(position)?.id)
                startActivity(intent)
            }
        })
        main_layout_pic.setOnClickListener {
                    val intent = Intent(activity, DetailsJohnWick::class.java)
                    startActivity(intent)
        }
    }

    private fun inititializeRecyclerView() {
        recyclerView = rootView.findViewById(R.id.moviesRecyclerView1)
        recyclerView.layoutManager = GridLayoutManager(
            activity,
            1
        )
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = moviesAdapter
        moviesAdapter?.notifyDataSetChanged()
    }
    private fun getMoviesCoroutine() {
        launch {
            swipeRefreshLayout.isRefreshing = true
            val list = withContext(Dispatchers.IO) {
                try {
                    val response = RetrofitService.getMovieApi().getPopularMoviesCoroutine()
                    if (response?.isSuccessful!!) {
                        val result = response.body()
                        //Log.d("fav result size", result!!.results.size.toString())
                        if (!result?.results.isNullOrEmpty()) {
                            Log.d("fav movies", "115")
                            movieDao?.insertAll(result!!.results)
                        }
                        result?.results
                    } else {
                        movieDao?.getAll() ?: emptyList<MoviesData>()
                    }
                } catch (e: Exception) {
                    Log.e("favourtite database", e.toString())
                    movieDao?.getAll() ?: emptyList<MoviesData>()
                }
            }

            moviesAdapter?.addItems(list as List<MoviesData>)
            moviesAdapter?.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }
    }
}
