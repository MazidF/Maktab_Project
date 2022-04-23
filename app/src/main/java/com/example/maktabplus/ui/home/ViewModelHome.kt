package com.example.maktabplus.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maktabplus.data.MovieRepository
import com.example.maktabplus.data.model.image.Image
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelHome @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    init {
        getPopularMovieList()
    }

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
}