package com.example.maktabplus.data.local

import com.example.maktabplus.data.local.db.MovieDao
import com.example.maktabplus.data.model.MovieDetailWithGenres
import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.data.model.movie.MovieDetail
import com.example.maktabplus.utils.Result
import com.example.maktabplus.utils.toResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalDataSource constructor(
    private val dao: MovieDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getMovieDetail(movieId: Int): Flow<Result<MovieDetailWithGenres>> {
        return dao.getMovieDetail(movieId).toResult(dispatcher)
    }

    fun getPopularMovieList(): Flow<Result<List<Movie>>> {
        return dao.getPopularMovieList().toResult(dispatcher)
    }

    fun getMovieListByGenre(genreId: Int): Flow<Result<List<Movie>>> {
        return dao.getMovieListByGenre(genreId).toResult(dispatcher)
    }

    fun getRecommendedByMovie(movieId: Int): Flow<Result<List<Movie>>> {
        return dao.getRecommendedByMovie(movieId).toResult(dispatcher)
    }

    fun getGenreList(): Flow<Result<List<Genre>>> {
        return dao.getGenreList().toResult(dispatcher)
    }

    suspend fun saveMovieList(genre: Genre, vararg movies: Movie) {
        dao.addMovieByGenre(genre, *movies)
    }

    suspend fun saveGenres(vararg genre: Genre) {
        dao.insertGenres(*genre)
    }

    suspend fun saveMovieDetailWithGenres(vararg data: MovieDetailWithGenres) {
        dao.insertDetailsWithGenres(*data)
    }
}