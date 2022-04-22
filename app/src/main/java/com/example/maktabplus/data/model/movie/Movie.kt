package com.example.maktabplus.data.model.movie

import androidx.room.*

@Entity(tableName = Movie.TABLE_NAME)
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "movie_id") val id: Int,
    @ColumnInfo(name = "movie_title") val title: String,
    @ColumnInfo(name = "movie_poster") val poster: String,
) {
    companion object {
        const val TABLE_NAME = "movie_table"
    }
}
