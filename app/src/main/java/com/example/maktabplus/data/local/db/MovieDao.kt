package com.example.maktabplus.data.local.db

import androidx.room.Dao
import androidx.room.Query
import com.example.maktabplus.data.model.movie.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao : IDao<Movie, Int> {

    @Query("select * from movie_table")
    override fun getAll(): Flow<List<Movie>>

    @Query("select * from movie_table where movie_id = :primaryKey")
    override fun find(primaryKey: Int): Flow<Movie?>
}
