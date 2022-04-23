package com.example.maktabplus.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.data.model.movie.MovieDetail
import com.example.maktabplus.data.model.movie.MovieDetailGenreCrossRef

@Database(
    entities = [Movie::class, MovieDetail::class, Genre::class, MovieDetailGenreCrossRef::class],
    version = 1,
    exportSchema = true
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao
}