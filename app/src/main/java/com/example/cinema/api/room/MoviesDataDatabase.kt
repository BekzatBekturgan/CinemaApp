package com.example.cinema.api.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cinema.api.model.MoviesData
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [MoviesData::class], version = 1)
abstract class MoviesDataDatabase : RoomDatabase(){
    abstract fun moviesDataDao(): MoviesDataDao

    companion object {
     //   @JvmField
     //   val MIGRATION_1_2 = Migration1To2()

        var INSTANCE: MoviesDataDatabase? = null

        fun getDatabase(context: Context): MoviesDataDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDataDatabase::class.java,
                    "app_database.db"
                )//.addMigrations(MoviesDataDatabase.MIGRATION_1_2)
                 .build()
            }
            return INSTANCE!!
        }
    }
   /* class Migration1To2 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE movie_data_table "
                        + "ADD COLUMN ListData TEXT"
            )
        }
    }*/
}