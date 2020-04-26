package com.example.cinema.api.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinema.api.model.FavouriteMovies

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFav(list: List<FavouriteMovies>)

    @Query("Select * from favourite_movie_table ")
    fun getFav(): List<FavouriteMovies>
}