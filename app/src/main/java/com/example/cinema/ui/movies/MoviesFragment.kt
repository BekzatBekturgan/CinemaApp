package com.example.cinema.ui.movies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cinema.*
import com.example.cinema.api.model.MoviesData
import com.example.cinema.api.room.MoviesDataDao
import com.example.cinema.api.room.MoviesDataDatabase
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


open class MoviesFragment: Fragment() , CoroutineScope {

    lateinit var recyclerView: RecyclerView
    private var moviesAdapter: MoviesAdapter? = null
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var rootView: View
    private lateinit var picture:ImageView
    val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
    private lateinit var movie_post:ImageView

    var movieDataDao: MoviesDataDao? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         onCreateComponent()

    }

    private fun onCreateComponent() {
        moviesAdapter = MoviesAdapter()
    }

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_movies, container, false)
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayoutMovie)
        movieDataDao = MoviesDataDatabase.getDatabase(requireContext()).moviesDataDao()
        getPopularMoviesCoroutine(
            onSuccess = :: onPopularMoviesFetched,
            onError =  :: onError
        )
        swipeRefreshLayout.setOnRefreshListener {
            recyclerView.layoutManager = GridLayoutManager(
                activity,
                1
            )
            recyclerView.itemAnimator = DefaultItemAnimator()
            recyclerView.adapter = moviesAdapter
            moviesAdapter?.notifyDataSetChanged()
            getPopularMoviesCoroutine(
                onSuccess = :: onPopularMoviesFetched,
                onError =  :: onError
            )
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
        recyclerView = rootView.findViewById(R.id.moviesRecyclerView1)
        recyclerView.layoutManager = GridLayoutManager(
            activity,
            1
        )
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = moviesAdapter
        moviesAdapter?.notifyDataSetChanged()
    }
    private fun getPopularMoviesCoroutine(
        page: Int = 10,
        onSuccess: (movies: List<MoviesData>) -> Unit,
        onError: () -> Unit
    )
    {
        launch {
            swipeRefreshLayout.isRefreshing = true
            val movies = withContext(Dispatchers.IO) {
                try {
                    val response = RetrofitService.getMovieApi().getPopularMoviesCoroutine(page = page)
                    if (response?.isSuccessful!!) {
                        val result = response.body()
                        if (!result?.movies.isNullOrEmpty()) {
                            movieDataDao?.insertAll(result!!.movies)
                        }
                        result?.movies
                    } else {
                        movieDataDao?.getAll() ?: emptyList<MoviesFragment>()
                    }
                } catch (e: Exception) {
                    Log.e("Popular movie database", e.toString())
                    movieDataDao?.getAll() ?: emptyList<MoviesFragment>()
                }
            }
            val response = RetrofitService.getMovieApi().getPopularMoviesCoroutine(page = page)
            if (response.isSuccessful) {
                val responseBody = response.body()

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
            swipeRefreshLayout.isRefreshing = false;
        }

    }

   /* private fun getPopularMovies(

        page: Int = 10,
        onSuccess: (movies: List<MoviesData>) -> Unit,
        onError: () -> Unit


    ) {
        swipeRefreshLayout.isRefreshing = true
        RetrofitService.getMovieApi().getPopularMovies(page = page)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

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
                    swipeRefreshLayout.isRefreshing = false;
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
      Log.e("Error", "Error")
    }

}
