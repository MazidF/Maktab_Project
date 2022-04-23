package com.example.maktabplus.data.model.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable

@Entity(
    tableName = MovieDetailGenreCrossRef.TABLE_NAME,
    primaryKeys = ["movie_detail_id", "genre_id"]
)
data class MovieDetailGenreCrossRef(
    @ColumnInfo(name = "movie_detail_id") val detailId: Int,
    @ColumnInfo(name = "genre_id") val genreId: Int,
) {
    companion object {
        const val TABLE_NAME = "movie_detail_genre_cross_ref"
    }
}