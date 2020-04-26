package com.example.cinema.api.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cinema.api.model.AccountDetails
import com.example.cinema.api.model.FavouriteMovies
import com.example.cinema.ui.profile.ProfileFragment

@Database(entities = [AccountDetails::class, FavouriteMovies::class], version = 1)
abstract class AccountDetailsDatabase : RoomDatabase(){

    abstract fun accountDetailsDao(): AccountDetailsDao


    companion object {
        var INSTANCE: AccountDetailsDatabase? = null

        fun getDatabase(context: Context): AccountDetailsDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AccountDetailsDatabase::class.java,
                    "app_database.db"
                ).build()
            }
            return INSTANCE!!
        }
    }

}