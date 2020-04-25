package com.example.cinema.api.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cinema.api.model.AccountDetails

@Database(entities = [AccountDetails::class], version = 1)
abstract class AccountDetailsDatabase : RoomDatabase(){

}