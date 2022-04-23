package com.example.maktabplus.data.local

import com.example.maktabplus.data.local.db.MovieDao
import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.utils.Result
import com.example.maktabplus.utils.toResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalDataSource constructor(
    private val dao: MovieDao,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getPopularMovieList(): Flow<Result<List<Movie>>> {
        return dao.getPopularMovieList().toResult(defaultDispatcher)
    }

    fun getMovieListByGenre(genreId: Int): Flow<Result<List<Movie>>> {
        return dao.getMovieListByGenre(genreId).toResult(defaultDispatcher)
    }

    fun getGenreList(): Flow<Result<List<Genre>>> {
        return dao.getGenreList().toResult(defaultDispatcher)
    }

/*    fun getGenres(): Flow<Result<List<Genre>>> {
        return dao.getGenres()
    }*/
}