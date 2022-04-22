package com.example.maktabplus.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.data.model.movie.MovieDetail

data class MovieWithDetail(
    @Embedded val movie: Movie,
    @Relation(
        entityColumn = "movie_id",
        parentColumn = "movie_detail_id"
    )
    val detail: MovieDetail
)
