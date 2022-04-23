package com.example.maktabplus.data

import com.example.maktabplus.data.local.LocalDataSource
import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.data.remote.RemoteDataSource
import com.example.maktabplus.utils.Result
import com.example.maktabplus.utils.toResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

/*    fun getGenre(): Flow<Result<List<Genre>>> = flow {
        local.g
    }*/

    fun getPopular(page: Int): Flow<Result<List<Movie>>> = flow {
        local.getPopularMovieList().collect {
            emit(it)
        }
        emit(Result.loading())
        remote.getPopularMovieList(page).also {
            emit(it)
            if (it.isSuccessful) {
                // update local
            }
        }
    }.flowOn(defaultDispatcher)
}
