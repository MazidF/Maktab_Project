package com.example.maktabplus.data.remote

import com.example.maktabplus.data.model.MovieDetailWithGenres
import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.data.model.movie.MovieDetail
import com.example.maktabplus.data.model.movie.MovieResponse
import com.example.maktabplus.data.remote.network.MovieApi
import com.example.maktabplus.utils.Result
import com.example.maktabplus.utils.Result.Companion.map
import com.example.maktabplus.utils.toResult

class RemoteDataSource constructor(
    private val api: MovieApi
) {
    private val mapper: (MovieResponse?) -> List<Movie>? = { it?.movies }

    suspend fun getMovieListByGenre(page: Int, genreId: Int): Result<List<Movie>> {
        return api.getMoviesByGenre(page, genreId).toResult().map(mapper)
    }

    suspend fun getPopularMovieList(page: Int): Result<List<Movie>> {
        return api.getPopularMovies(page).toResult().map(mapper)
    }

    suspend fun getComingSoonMovieList(page: Int): Result<List<Movie>> {
        return api.getUpComingMovies(page).toResult().map(mapper)
    }

    suspend fun getRecommendedByMovie(movieId: Int): Result<List<Movie>> {
        return api.getRecommendedBy(movieId).toResult().map(mapper)
    }

    suspend fun getMovieDetail(movieId: Int): Result<MovieDetailWithGenres> {
        return api.getMovieDetail(movieId).toResult()
    }

    suspend fun search(query: String, page: Int): Result<List<Movie>> {
        return api.search(query, page).toResult().map(mapper)
    }

    suspend fun getGenres(): Result<List<Genre>> {
        return api.getGenres().toResult().map {
            it?.genres
        }
    }
}