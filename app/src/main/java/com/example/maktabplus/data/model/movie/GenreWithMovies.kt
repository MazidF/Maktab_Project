package com.example.maktabplus.data.model.movie

import java.util.*

data class GenreWithMovies(
    val genre: Genre,
    val movies: MutableList<Movie> = MutableList(10) {
        Movie.empty
    }
) {
    var hasLoaded = false

    fun replace(newList: List<Movie>) {
        if (hasLoaded) return
        Collections.copy(movies, newList)
        hasLoaded = true
    }
}