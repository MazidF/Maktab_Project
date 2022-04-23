package com.example.maktabplus.data.remote.network

import com.example.maktabplus.data.model.movie.*
import retrofit2.Response
import retrofit2.http.*

interface MovieApi {

    companion object {
        const val TOKEN = "f5a5d7af59cba941f2d09648869ea4e3"
        const val LANGUAGE = "en-US"
    }

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
    ): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("page") page: Int,
    ): Response<MovieResponse>

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("page") page: Int,
        @Query("with_genres") genreId: Int,
    ): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieInfo(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") append: String = "videos"
    ): Response<MovieDetail>

    @GET("search/movie")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Response<MovieResponse>

    @GET("/movie/{movie_id}/recommendations")
    suspend fun getRecommendedBy(
        @Path("movie_id") id: Int
    ): Response<MovieResponse>

    @POST("movie/{movie_id}/rating")
    suspend fun rateMovie(
        @Path("movie_id") id: Int,
        @Body rate: Rate
    )

    @DELETE("movie/{movie_id}/rating")
    suspend fun deleteRating(
        @Path("movie_id") id: Int,
    )

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<List<Genre>>

/*    @GET("p/w500/{poster_path}")
    suspend fun getPoster(
        @Path("poster_path") posterPath: String
    )*/
}