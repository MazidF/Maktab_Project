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
    @ColumnInfo(name = "genre_owner_id") val ownerId: Int,
) {
    companion object {
        const val TABLE_NAME = "genre_table"
        const val POPULAR = -111
        const val COMING_SOON = -112
    }
}

@Entity(
    primaryKeys = ["movie_genre_cross_detail_id", "movie_genre_cross_genre_id"]
)
data class MovieDetailGenreCrossRef(
    @ColumnInfo(name = "movie_genre_cross_detail_id") val detailId: Int,
    @ColumnInfo(name = "movie_genre_cross_genre_id") val genreId: Int,
)

data class MovieDetailWithGenres(
    @Embedded val detail: MovieDetail,
    @Relation(
        parentColumn = "movie_detail_id",
        entityColumn = "genre_owner_id",
        associateBy  = Junction(MovieDetailGenreCrossRef::class)
    ) val genres: List<Genre>
)
