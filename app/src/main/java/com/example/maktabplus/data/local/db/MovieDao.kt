package com.example.maktabplus.data.local.db

import androidx.room.Dao
import androidx.room.Query
import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.model.movie.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao : IDao<Movie, Int> {

    @Query("select * from movie_table")
    override fun getAll(): Flow<List<Movie>>

    @Query("select * from movie_table where movie_id = :primaryKey")
    override fun find(primaryKey: Int): Flow<Movie?>

    @Query("select * from movie_table as m " +
            "where m.movie_id in (select r.movie_detail_id from movie_detail_genre_cross_ref as r " +
            "where r.genre_id = :genreId)")
    fun getMovieListByGenre(genreId: Int): Flow<List<Movie>>

    fun getPopularMovieList() = getMovieListByGenre(Genre.POPULAR.id)

    fun getComingSoonMovieList() = getMovieListByGenre(Genre.COMING_SOON.id)

    @Query("select * from genre_table")
    fun getGenreList(): Flow<List<Genre>>
}

@Dao
interface GenreDao: IDao<Genre, Int> {

    @Query("select * from genre_table")
    override fun getAll(): Flow<List<Genre>>

    @Query("select * from genre_table where genre_id = :primaryKey")
    override fun find(primaryKey: Int): Flow<Genre?>
}
