package com.example.maktabplus.data.local.db

import androidx.room.*
import com.example.maktabplus.data.model.MovieDetailWithGenres
import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.data.model.movie.MovieDetail
import com.example.maktabplus.data.model.movie.MovieDetailGenreCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao : IDao<Movie, Int> {

    @Query("select * from movie_table")
    override fun getAll(): Flow<List<Movie>>

    @Query("select * from movie_table where movie_id = :primaryKey")
    override fun find(primaryKey: Int): Flow<Movie?>

    @Query(
        "select * from movie_table as m " +
                "where m.movie_id in (select r.movie_detail_id from movie_detail_genre_cross_ref as r " +
                "where r.genre_id = :genreId)"
    )
    fun getMovieListByGenre(genreId: Int): Flow<List<Movie>>

    fun getPopularMovieList() = getMovieListByGenre(Genre.POPULAR.id)

    fun getComingSoonMovieList() = getMovieListByGenre(Genre.COMING_SOON.id)

    @Query("select * from genre_table")
    fun getGenreList(): Flow<List<Genre>>

    @Query(
        "delete from movie_table " +
                "where movie_id in (select r.movie_detail_id from movie_detail_genre_cross_ref as r " +
                "where r.genre_id = :genreId)"
    )
    suspend fun deleteMoviesByGenre(genreId: Int)

    @Transaction
    suspend fun addMovieByGenre(genre: Genre, vararg movies: Movie) {
        insertItems(*movies)
        insertGenres(genre)
        insertMovieGenreCrossRef(*movies.map { MovieDetailGenreCrossRef(it.id, genre.id) }
            .toTypedArray())
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(vararg genres: Genre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenreCrossRef(vararg crossRef: MovieDetailGenreCrossRef)

    @Query(
        "select * from movie_table where movie_id in (" +
                "select r.movie_detail_id from movie_detail_genre_cross_ref r " +
                "where r.genre_id in (select r.genre_id from movie_detail_genre_cross_ref r " +
                "where r.movie_detail_id = :movieId) " +
                "group by r.movie_detail_id " +
                "order by count(*))"
    )
    fun getRecommendedByMovie(movieId: Int): Flow<List<Movie>>

    @Query("select * from movie_detail_table where movie_detail_id = :movieId limit 1")
    fun getMovieDetail(movieId: Int): Flow<MovieDetailWithGenres>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetails(vararg data: MovieDetail)

    @Transaction
    suspend fun insertDetailsWithGenres(vararg data: MovieDetailWithGenres) {
        insertGenres(*data.flatMap {
            it.genres
        }.toSet().toTypedArray())
        insertDetails(*data.map {
            it.detail
        }.toTypedArray())
        insertMovieGenreCrossRef(
            *data.flatMap {
                val detailId = it.detail.movieId
                it.genres.map { genre ->
                    MovieDetailGenreCrossRef(
                        detailId = detailId,
                        genreId = genre.id
                    )
                }
            }.toTypedArray()
        )
    }
}

@Dao
interface GenreDao : IDao<Genre, Int> {

    @Query("select * from genre_table")
    override fun getAll(): Flow<List<Genre>>

    @Query("select * from genre_table where genre_id = :primaryKey")
    override fun find(primaryKey: Int): Flow<Genre?>
}
