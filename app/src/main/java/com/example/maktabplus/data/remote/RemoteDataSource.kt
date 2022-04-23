package com.example.maktabplus.data.remote

import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.data.remote.network.MovieApi
import com.example.maktabplus.utils.Result
import com.example.maktabplus.utils.Result.Companion.map
import com.example.maktabplus.utils.toResult
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: MovieApi
) {
    suspend fun getMovieListByGenre(page: Int, genreId: Int): Result<List<Movie>> {
        return api.getMoviesByGenre(page, genreId).toResult()
    }

    suspend fun getPopularMovieList(page: Int): Result<List<Movie>> {
        return api.getPopularMovies(page).toResult().map {
            it?.movies
        }
    }

    suspend fun getComingSoonMovieList(page: Int): Result<List<Movie>> {
        return api.getUpComingMovies(page).toResult()
    }

    suspend fun getRecommendedByMovie(movieId: Int): Result<List<Movie>> {
        return api.getRecommendedBy(movieId).toResult()
    }

    suspend fun search(query: String, page: Int): Result<List<Movie>> {
        return api.search(query, page).toResult()
    }

    suspend fun getGenres(): Result<List<Genre>> {
        return api.getGenres().toResult()
    }
}