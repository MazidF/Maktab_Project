package com.example.maktabplus.ui.mvoie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.maktabplus.data.MovieRepository
import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMovieList @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private var currentPage: Int = 1
    private val _movieListFlow: MutableStateFlow<Result<List<Movie>>> =
        MutableStateFlow(Result.success(listOf()))
    val movieListFlow get() = _movieListFlow.asStateFlow()

    fun getMovieListByGenre(genre: Genre, page: Int? = null) {
        viewModelScope.launch {
            repository.getMovieListByGenre(genre = genre, page = page ?: currentPage).collect {
                _movieListFlow.emit(it)
            }
        }
    }
}