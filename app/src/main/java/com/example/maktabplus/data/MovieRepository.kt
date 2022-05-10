package com.example.maktabplus.data

import com.example.maktabplus.data.local.LocalDataSource
import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.remote.RemoteDataSource
import com.example.maktabplus.utils.Result
import com.example.maktabplus.utils.logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun search(query: String, page: Int) = loadAndSave(
        localLoad = {
            flow { }
        }, remoteLoad = {
            this.search(query, page)
        },
        localSave = {

        }
    )

    fun getMovieDetail(movieId: Int) = loadAndSave(
        localLoad = {
            getMovieDetail(movieId)
        }, remoteLoad = {
            getMovieDetail(movieId)
        }, localSave = { data ->
            if (data != null) {
                this.saveMovieDetailWithGenres(data)
            }
        }
    )

    fun getPopular(page: Int) = loadAndSave(
        localLoad = LocalDataSource::getPopularMovieList,
        remoteLoad = {
            this.getPopularMovieList(page)
        },
        localSave = { data ->
            if (data == null) return@loadAndSave
            saveMovieList(Genre.POPULAR, *data.toTypedArray())
        }
    )

    fun getRecommendedByMovie(movieId: Int) = loadAndSave(
        localLoad = {
            this.getRecommendedByMovie(movieId)
        },
        remoteLoad = {
            getRecommendedByMovie(movieId)
        },
        localSave = {
            // ignore data
        }
    )

    fun getGenres() = loadAndSave(
        localLoad = LocalDataSource::getGenreList,
        remoteLoad = RemoteDataSource::getGenres,
        localSave = { data ->
            if (data == null)
                return@loadAndSave
            this.saveGenres(*data.toTypedArray())
        }
    )

    fun getMovieListByGenre(page: Int, genre: Genre) = loadAndSave(
        localLoad = {
            this.getMovieListByGenre(genre.id)
        },
        remoteLoad = {
            this.getMovieListByGenre(page, genre.id)
        },
        localSave = { data ->
            if (data == null) return@loadAndSave
            saveMovieList(genre, *data.toTypedArray())
        }
    )

    private fun <T> loadAndSave(
        localLoad: LocalDataSource.() -> Flow<Result<T>>,
        remoteLoad: suspend RemoteDataSource.() -> Result<T>,
        localSave: suspend LocalDataSource.(T?) -> Unit,
    ): Flow<Result<T>> = flow {
        try {
            emit(local.localLoad().first())
        } catch (e: Exception) { }
        emit(Result.loading())
        try {
            remote.remoteLoad().also {
                emit(it)
                if (it.isSuccessful) {
                    local.localSave(it.data)
                }
            }
        } catch (e: Exception) {
            emit(Result.error(e.message.toString()))
            logger(e.message.toString())
        }
        Unit
    }.flowOn(dispatcher)
}
