package com.example.cinema.ui.favourites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.api.model.MovieResponse
import com.example.cinema.api.model.MoviesData
import com.example.cinema.ui.movies.MoviesAdapter
import com.example.cinema.ui.movies.OnItemClickListner
import com.example.cinema.DetailsActivity
import com.example.cinema.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FavouritesFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    private var moviesAdapter: MoviesAdapter? = null
    private lateinit var rootView: View


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
        rootView = inflater.inflate(R.layout.fragment_favourites, container, false)
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

        page: Int = 1,
        onSuccess: (movies: List<MoviesData>) -> Unit,
        onError: () -> Unit

    ) {
        RetrofitService.getMovieApi().getFavouriteMovies()
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