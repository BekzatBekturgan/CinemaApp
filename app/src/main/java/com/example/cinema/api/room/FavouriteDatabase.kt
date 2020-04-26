package com.example.cinema.api.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cinema.api.model.FavouriteMovies

@Database(entities = [FavouriteMovies::class], version = 2)
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