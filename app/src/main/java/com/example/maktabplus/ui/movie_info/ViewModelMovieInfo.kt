package com.example.maktabplus.ui.movie_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maktabplus.data.MovieRepository
import com.example.maktabplus.data.model.MovieDetailWithGenres
import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMovieInfo @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieDetailStateFlow: MutableStateFlow<Result<MovieDetailWithGenres>> =
        MutableStateFlow(Result.success(MovieDetailWithGenres.empty))
    val movieDetailFlow get() = _movieDetailStateFlow.asStateFlow()

    fun getMovieInfo(movieId: Int) {
        viewModelScope.launch {
            repository.getMovieDetail(movieId).collect {
                _movieDetailStateFlow.emit(it)
            }
        }
    }

    fun getSimilarMovies(movieId: Int): Flow<Result<List<Movie>>> {
        return repository.getRecommendedByMovie(movieId)
    }
}