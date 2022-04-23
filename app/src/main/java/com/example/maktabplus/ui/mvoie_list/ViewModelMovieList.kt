package com.example.maktabplus.ui.mvoie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maktabplus.data.MovieRepository
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMovieList @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieListFlow: MutableStateFlow<Result<List<Movie>>> =
        MutableStateFlow(Result.success(listOf()))
    val movieListFlow get() = _movieListFlow.asStateFlow()


    fun getPopularMovieList() {
        viewModelScope.launch {
            repository.getPopular(1).collect {
                _movieListFlow.emit(it)
            }
        }
    }

    fun getMovieInfo(
        movieId: Int
    ) {
        viewModelScope.launch {

        }
    }
}