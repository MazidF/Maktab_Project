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

    @Query("select m.movie_id, m.movie_title, m.movie_poster from movie_table as m " +
            "join movie_detail_table as d on m.movie_id = d.movie_detail_owner_id " +
            "join genre_table as g on d.movie_detail_id = g.genre_owner_id " +
            "where g.genre_id = :genreId")
    fun getMovieListByGenre(genreId: Int): Flow<List<Movie>>

    fun getPopularMovieList() = getMovieListByGenre(Genre.POPULAR)

    fun getComingSoonMovieList() = getMovieListByGenre(Genre.COMING_SOON)
}

@Dao
interface GenreDao: IDao<Genre, Int> {

    @Query("select * from genre_table")
    override fun getAll(): Flow<List<Genre>>

    @Query("select * from genre_table where genre_id = :primaryKey")
    override fun find(primaryKey: Int): Flow<Genre?>
}
