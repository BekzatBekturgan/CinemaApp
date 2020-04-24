package com.example.cinema.api.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cinema.api.model.Post


@Database(entities = [Post::class], version = 1)
abstract class PostDatabase : RoomDatabase() {

}