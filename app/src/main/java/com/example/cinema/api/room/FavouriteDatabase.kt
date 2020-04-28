package com.example.cinema.api.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cinema.api.model.AccountDetails
import com.example.cinema.api.model.FavouriteMovies
import com.example.cinema.api.model.MoviesData

@Database(entities = [FavouriteMovies::class, AccountDetails::class, MoviesData::class], version = 1)
abstract class FavouriteDatabase : RoomDatabase() {

    abstract fun favMoviesDao(): FavouriteDao

    companion object {

        var INSTANCE: FavouriteDatabase? = null

        fun getDatabase(context: Context): FavouriteDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    FavouriteDatabase::class.java,
                    "app_database.db"
                ).build()
            }
            return INSTANCE!!
        }
    }
}