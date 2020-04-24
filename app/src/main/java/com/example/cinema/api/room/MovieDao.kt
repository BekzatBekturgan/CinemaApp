package com.example.cinema.api.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinema.api.model.MoviesData

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<MoviesData>)

    @Query("SELECT * FROM movie_table")
    fun getAll(): List<MoviesData>
}