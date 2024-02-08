package com.android.movie.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.movie.database.daos.MovieDao
import com.android.movie.database.entities.MovieEntity

@Database(
    entities = [
        MovieEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao
}