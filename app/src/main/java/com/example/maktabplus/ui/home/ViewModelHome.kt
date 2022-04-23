package com.example.maktabplus.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maktabplus.data.MovieRepository
import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelHome @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _genresFlow = MutableStateFlow<Result<List<Genre>>>(Result.success(listOf()))
    val genresFlow get() = _genresFlow.asStateFlow()

    init {
        getGenres()
    }

    private fun getGenres() {
        viewModelScope.launch {
            repository.getGenres().collect {
                _genresFlow.emit(it)
            }
        }
    }

    fun getMovieListByGenre(genreId: Int): Flow<Result<List<Movie>>> {
        return repository.getMovieListByGenre(genreId = genreId, page = 1)
    }


}
