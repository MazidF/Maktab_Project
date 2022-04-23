package com.example.maktabplus.ui.home

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.maktabplus.R
import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.model.movie.GenreWithMovies
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.databinding.FragmentHomeBinding
import com.example.maktabplus.utils.Result
import com.example.maktabplus.utils.repeatingLaunch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.streams.toList

@AndroidEntryPoint
class FragmentHome : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private val viewModel: ViewModelHome by viewModels()
    private val holderAdapter = GeneHolderAdapter(
        onItemClick = this::onMovieItemClick,
        onMoreClick = this::onGenreHolderMoreClick
    )

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        init()
        observer()
    }

    private fun init() = with(binding) {
        movieListItems.apply {
            this.adapter = holderAdapter
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun observer() {
        repeatingLaunch(Lifecycle.State.STARTED) {
            viewModel.genresFlow.collect {
                when(it) {
                    is Result.Error -> {  }
                    is Result.Loading -> {  }
                    is Result.Success -> {
                        val list = it.data?.map { genre ->
                            GenreWithMovies(genre).apply {
                                launch {
                                    viewModel.getMovieListByGenre(genre.id).collect { response ->
                                        when(response) {
                                            is Result.Error -> {

                                            }
                                            is Result.Loading -> {

                                            }
                                            is Result.Success -> {
                                                response.data?.let { movies ->
                                                    this@apply.replace(movies.stream().limit(10).toList())
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        holderAdapter.submitList(list)
                    }
                }
            }
        }
    }

    private fun onMovieItemClick(movie: Movie) {

    }

    private fun onGenreHolderMoreClick(genre: Genre) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}