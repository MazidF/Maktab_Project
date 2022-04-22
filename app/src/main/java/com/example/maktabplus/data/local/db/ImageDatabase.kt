package com.example.maktabplus.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.data.model.movie.MovieDetail
import com.example.maktabplus.data.remote.network.MovieApi

@Database(
    entities = [Movie::class, MovieDetail::class],
    version = 1,
    exportSchema = true
)
abstract class ImageDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao
}