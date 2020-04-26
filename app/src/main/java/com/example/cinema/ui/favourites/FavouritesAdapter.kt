package com.example.cinema.ui.favourites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.api.model.FavouriteMovies
import com.example.cinema.api.model.MoviesData
import kotlinx.android.synthetic.main.item_movie.view.*

class FavouritesAdapter: BaseRecyclerViewAdapter<FavouriteMovies>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var myHolder = holder as? MoviesViewHolder
        getItem(position)?.let { myHolder?.bind(movie = it) }
    }

    inner class MoviesViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val movieImageView = itemView.findViewById<ImageView>(R.id.item_movie_image)

        init {
            itemView.setOnClickListener(this)
        }
        fun bind (movie: FavouriteMovies) {
            itemView.itemMovieTitle.text = movie?.originalTitle
            itemView.overview.text = movie?.overview
            itemView.textViewData.text = movie?.releaseDate

            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .into(movieImageView)
        }

        override fun onClick(v: View?) {
            if (v != null) {
                itemClickListener?.onItemClick(adapterPosition, v)
            }
        }

    }

}