package com.example.maktabplus.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maktabplus.data.MovieRepository
import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelSearch @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieListFlow: MutableStateFlow<Result<List<Movie>>> =
        MutableStateFlow(Result.success(listOf()))
    val movieListFlow get() = _movieListFlow.asStateFlow()

    fun searchByQuery(query: String, page: Int = 1): Job {
        return viewModelScope.launch {
            delay(1)
            repository.search(query, page).collect {
                _movieListFlow.emit(it)
            }
        }
    }
}
