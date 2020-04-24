package com.example.cinema.ui.favourites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.cinema.*
import com.example.cinema.api.model.MovieResponse
import com.example.cinema.api.model.MoviesData
import com.example.cinema.ui.favourites.FavouritesAdapter
import com.example.cinema.ui.favourites.OnItemClickListner
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext


open class FavouritesFragment: Fragment(), CoroutineScope {

    lateinit var recyclerView: RecyclerView
    private var moviesAdapter: FavouritesAdapter? = null
    private lateinit var rootView: View
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var sessionId: String? = null

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

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
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout)
        sessionId = pref.getString("sessionID", "empty")
        Log.d(
            "oncreateviewsessinid",
            sessionId
        )

        getFavouriteMoviesCoroutine()
        /*
        getFavouriteMovies(
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onError
        )
        */

        swipeRefreshLayout.setOnRefreshListener {
            recyclerView.layoutManager = GridLayoutManager(
                activity,
                1
            )
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = moviesAdapter
            moviesAdapter?.notifyDataSetChanged()

            getFavouriteMoviesCoroutine()
            /*
            getFavouriteMovies(
                onSuccess = ::onPopularMoviesFetched,
                onError = ::onError
            )
             */
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

    private fun setUpAdapter() {
        moviesAdapter?.setOnItemClickListener(onItemClickListener = object : OnItemClickListner {
            override fun onItemClick(position: Int, view: View) {
                val intent = Intent(activity, DetailsActivity::class.java)
                intent.putExtra("movieId", moviesAdapter!!.getItem(position)?.id)
                startActivity(intent)
            }
        })
    }

    private fun inititializeRecyclerView() {
        recyclerView = rootView.findViewById(R.id.favouriteMoviesRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(
            activity,
            1
        )
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = moviesAdapter
        moviesAdapter?.notifyDataSetChanged()

    }
/*
    private fun getFavouriteMovies(
        onSuccess: (movies: List<MoviesData>) -> Unit,
        onError: () -> Unit
    ) {
        swipeRefreshLayout.isRefreshing = true
        RetrofitService.getMovieApi().getFavouriteMovies(sessionId)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d("get favourite movies", sessionId)
                        if (responseBody != null) {
                            moviesAdapter?.clear()
                            onSuccess.invoke(responseBody.movies)
                            moviesAdapter?.notifyDataSetChanged()

                        } else {
                            onError.invoke()
                        }

                    } else {
                        onError.invoke()
                    }
                    swipeRefreshLayout.isRefreshing = false
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
*/

    private fun onPopularMoviesFetched(movies: List<MoviesData>) {
        moviesAdapter?.addItems(movies as ArrayList<MoviesData>)
    }

    private fun onError() {
        Log.e("Error", sessionId)
    }

    private fun getFavouriteMoviesCoroutine() {
        launch {
            swipeRefreshLayout.isRefreshing = true
            val list = withContext(Dispatchers.IO) {
            try{
                val response = RetrofitService.getMovieApi().getFavouriteMoviesCoroutine(sessionId)

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    //Log.d("get favourite movies", sessionId)
                    if (responseBody != null) {
                        moviesAdapter?.clear()
                        moviesAdapter?.addItems(responseBody.movies as ArrayList<MoviesData>)
                        moviesAdapter?.notifyDataSetChanged()

                    } else {
                        Log.e("Error", sessionId)
                    }
                } else {
                    Log.e("Error", sessionId)
                }
            }
            catch{

            }

            swipeRefreshLayout.isRefreshing = false
        }
    }
}
