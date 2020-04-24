package com.example.cinema.api.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cinema.api.model.MoviesData


@Database(entities = [MoviesData::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun postDao(): MovieDao

    companion object {

        var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movies_database.db"
                ).build()
            }
            return INSTANCE!!
        }
    }
}