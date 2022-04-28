package com.example.maktabplus.data.model

import androidx.room.*
import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.model.movie.MovieDetail
import com.example.maktabplus.data.model.movie.MovieDetailGenreCrossRef


data class MovieDetailWithGenres(
    @Embedded val detail: MovieDetail,
    @Relation(
        parentColumn = "movie_detail_id",
        entityColumn = "genre_id",
        associateBy  = Junction(MovieDetailGenreCrossRef::class)
    ) val genres: List<Genre>
) {
    companion object {
        val empty = MovieDetailWithGenres(
            detail = MovieDetail.empty,
            genres = emptyList()
        )
    }
}
