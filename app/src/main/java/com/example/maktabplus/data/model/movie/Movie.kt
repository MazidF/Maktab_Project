package com.example.maktabplus.data.model.movie

import androidx.room.*
import java.io.Serializable

@Entity(tableName = Movie.TABLE_NAME)
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "movie_id") val id: Int,
    @ColumnInfo(name = "movie_title") val title: String,
    @ColumnInfo(name = "movie_poster") val poster: String,
): Serializable {

    fun poster(): String {
        return "https://image.tmdb.org/t/p/original$poster"
    }

    companion object {
        const val TABLE_NAME = "movie_table"
        val empty = Movie(
            id = -1,
            title = "",
            poster = ""
        )
    }
}
