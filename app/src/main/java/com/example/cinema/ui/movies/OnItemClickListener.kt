package com.example.cinema.ui.movies

import android.view.View

interface OnItemClickListner {
    abstract fun onItemClick(position: Int, view: View)
}