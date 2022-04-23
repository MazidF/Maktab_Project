package com.example.maktabplus.data

import com.example.maktabplus.data.local.LocalDataSource
import com.example.maktabplus.data.remote.RemoteDataSource
import com.example.maktabplus.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getPopular(page: Int) = loadAndSave(
        localLoad = LocalDataSource::getPopularMovieList,
        remoteLoad = {
            this.getPopularMovieList(page)
        },
        localSave = {

        }
    )

    fun getMovieInfo(movieId: Int) {

    }

    fun getGenres() = loadAndSave(
        localLoad = LocalDataSource::getGenreList,
        remoteLoad = RemoteDataSource::getGenres,
        localSave = {

        }
    )

    private fun <T> loadAndSave(
        localLoad: LocalDataSource.() -> Flow<Result<T>>,
        remoteLoad: suspend RemoteDataSource.() -> Result<T>,
        localSave: suspend LocalDataSource.(T?) -> Unit,
    ): Flow<Result<T>> = flow {
        local.localLoad().collect {
            emit(it)
        }
        emit(Result.loading())
        remote.remoteLoad().also {
            emit(it)
            if (it.isSuccessful) {
                local.localSave(it.data)
            }
        }
    }.flowOn(defaultDispatcher)
}
