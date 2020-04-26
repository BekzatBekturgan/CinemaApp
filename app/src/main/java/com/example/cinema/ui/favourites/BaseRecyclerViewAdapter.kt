package com.example.cinema.ui.favourites

import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.api.model.FavouriteMovies
import com.example.cinema.api.model.MoviesData

abstract class BaseRecyclerViewAdapter<T>: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: ArrayList<T>? = ArrayList<T>()
    protected var itemClickListener: OnItemClickListner? = null


    fun addItems(items: List<T>) {
        this.list?.addAll(items)
        reload()
    }

    fun clear() {
        this.list?.clear()
        reload()
    }

    fun getItem(position: Int): T? {
        return this.list?.get(position)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListner) {
        this.itemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int = list!!.size

    private fun reload() {
        Handler(Looper.getMainLooper()).post { notifyDataSetChanged() }
    }

}