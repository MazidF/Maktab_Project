package com.example.maktabplus.data.model.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = MovieDetail.TABLE_NAME, foreignKeys = [
    ForeignKey(
        entity = Movie::class,
        parentColumns = ["movie_id"],
        childColumns = ["movie_detail_owner_id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class MovieDetail(
    @ColumnInfo(name = "movie_detail_owner_id") val ownerId: Int,
    @ColumnInfo(name = "movie_detail_overview") val overview: String,
    @ColumnInfo(name = "movie_detail_backdrop_path") val backdrop: String,
    @ColumnInfo(name = "movie_detail_vote_average") val voteAverage: Float,
    @ColumnInfo(name = "movie_detail_vote_count") val voteCount: Int,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "movie_detail_id") val id: Int = 0,
) {
    companion object {
        const val TABLE_NAME = "movie_detail_table"
    }
}