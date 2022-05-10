package com.example.maktabplus.data.model.movie

import androidx.lifecycle.MutableLiveData

data class GenreWithMovies(
    val genre: Genre,
    var movies: List<Movie> = List(10) {
        Movie.empty
    }
) {
    val hasLoaded by lazy {
        MutableLiveData<Boolean>()
    }

    fun replace(newList: List<Movie>) {
//        if (hasLoaded.value == true) return
        movies = newList
        hasLoaded.postValue(true)
    }
}