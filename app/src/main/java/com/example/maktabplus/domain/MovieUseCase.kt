package com.example.maktabplus.domain

import com.example.maktabplus.data.MovieRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MovieUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val defaultDispatcher: CoroutineContext = Dispatchers.IO
) {


}