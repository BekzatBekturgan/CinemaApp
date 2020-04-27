package com.example.cinema.ui.favourites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cinema.*
import com.example.cinema.api.model.FavouriteMovies
import com.example.cinema.api.room.FavouriteDao
import com.example.cinema.api.room.FavouriteDatabase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


open class FavouritesFragment: Fragment(), CoroutineScope {

    lateinit var recyclerView: RecyclerView
    private var moviesAdapter: FavouritesAdapter? = null
    private lateinit var rootView: View
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var sessionId: String? = null
    var favMovieDao: FavouriteDao? = null

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

        Log.d("oncreateviewsessinid", sessionId)

        favMovieDao = FavouriteDatabase.getDatabase(requireContext()).favMoviesDao()
        getFavouriteMoviesCoroutine()

        swipeRefreshLayout.setOnRefreshListener {
            recyclerView.layoutManager = GridLayoutManager(
                activity,
                1
            )
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = moviesAdapter
            moviesAdapter?.notifyDataSetChanged()

            getFavouriteMoviesCoroutine()
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

    private fun getFavouriteMoviesCoroutine() {
        launch {
            swipeRefreshLayout.isRefreshing = true
            val movies = withContext(Dispatchers.IO) {
                try {
                    val response = RetrofitService.getMovieApi().getFavouriteMoviesCoroutine(sessionId)
                    if (response?.isSuccessful!!) {
                        val result = response.body()
                        if (!result?.results.isNullOrEmpty()) {
                            favMovieDao?.insertAll(result!!.results)
                        }
                        result?.results
                    } else {
                        favMovieDao?.getAll() ?: emptyList<FavouriteMovies>()
                    }
                } catch (e: Exception) {
                    Log.e("favourtite database", e.toString())
                    favMovieDao?.getAll() ?: emptyList<FavouriteMovies>()
                }
            }
            moviesAdapter?.clear()
            moviesAdapter?.addItems(movies as List<FavouriteMovies>)
            moviesAdapter?.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }
    }
}
