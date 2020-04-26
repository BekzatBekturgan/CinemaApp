package com.example.cinema.api.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cinema.api.model.AccountDetails
import com.example.cinema.api.model.FavouriteMovies

@Database(entities = [FavouriteMovies::class, AccountDetails::class], version = 1)
abstract class FavouriteDatabase : RoomDatabase() {

    abstract fun favMoviesDao(): FavouriteDao

    companion object {
        @JvmField
        //val MIGRATION_1_2 = Migration1To2()
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
    /*
    class Migration1To2 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE favourite_movie_table "
                        + "DROP COLUMN ListData"
            )
        }
    }
    */

}