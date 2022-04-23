package com.example.maktabplus.data.model.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = Genre.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = MovieDetail::class,
            parentColumns = ["movie_detail_id"],
            childColumns = ["genre_owner_id"]
        )
    ]
)
data class Genre(
    @PrimaryKey
    @ColumnInfo(name = "genre_id") val id: Int,
    @ColumnInfo(name = "genre_name") val name: String,
): Serializable {
    companion object {
        const val TABLE_NAME = "genre_table"
        val POPULAR = Genre(-111, "Popular")
        val COMING_SOON = Genre(-112, "Coming Soon")
    }
}