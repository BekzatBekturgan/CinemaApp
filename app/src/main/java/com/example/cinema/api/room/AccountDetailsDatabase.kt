package com.example.cinema.api.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cinema.api.model.AccountDetails

@Database(entities = [AccountDetails::class], version = 1)
abstract class AccountDetailsDatabase : RoomDatabase(){


    companion object {

        var INSTANCE: AccountDetailsDatabase? = null

        fun getDatabase(context: Context): AccountDetailsDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AccountDetailsDatabase::class.java,
                    "account_details_database.db"
                ).build()
            }
            return INSTANCE!!
        }
    }

}