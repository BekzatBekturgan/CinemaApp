package com.example.cinema.ui.favourites

import android.view.View

interface OnItemClickListner {
    abstract fun onItemClick(position: Int, view: View)
}