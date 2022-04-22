package com.example.maktabplus.data.remote.network

import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.data.model.movie.MovieDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    companion object {
        const val TOKEN = "f5a5d7af59cba941f2d09648869ea4e3"
        const val LANGUAGE = "en-US"
    }

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = TOKEN,
        @Query("language") lan: String = LANGUAGE
    ): Response<List<Movie>>

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = TOKEN,
        @Query("language") lan: String = LANGUAGE
    ): Response<List<Movie>>

    @GET("movie/{movie_id}")
    suspend fun getMovieInfo(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = TOKEN,
        @Query("language") lan: String = LANGUAGE,
        @Query("append_to_response") append: String = "videos"
    ): Response<MovieDetail>

    @GET("search/movie")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = TOKEN,
        @Query("language") lan: String = LANGUAGE
    ): Response<List<Movie>>

/*    @GET("p/w500/{poster_path}")
    suspend fun getPoster(
        @Path("poster_path") posterPath: String
    )*/

}