package com.example.maktabplus.ui.home

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.maktabplus.R
import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.model.movie.GenreWithMovies
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.databinding.FragmentHomeBinding
import com.example.maktabplus.utils.Constants.UNABLE_TO_CONNECT
import com.example.maktabplus.utils.Result
import com.example.maktabplus.utils.repeatingLaunch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.streams.toList

@AndroidEntryPoint
class FragmentHome : Fragment(R.layout.fragment_home) {
    private val navController by lazy {
        findNavController()
    }
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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun init() = with(binding) {
        homeItems.apply {
            this.adapter = holderAdapter
        }
        this.homeRefresher.setOnRefreshListener {
            viewModel.getGenres()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun observer() {
        repeatingLaunch(Lifecycle.State.STARTED) {
            loadGenres()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private suspend fun loadGenres() {
        viewModel.genresFlow.collect {
            when (it) {
                is Result.Error -> {
                    it.snackBar(binding.root, UNABLE_TO_CONNECT) {
                        viewModel.getGenres()
                    }
                }
                is Result.Success -> {
                    setGenresHolder(it.data)
                }
            }
            if (it is Result.Loading) {
                startLoading()
            } else {
                stopLoading()
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private suspend fun setGenresHolder(genres: List<Genre>?) = coroutineScope {
        val list = genres?.map { genre ->
            GenreWithMovies(genre).apply {
                launch(Dispatchers.IO) {
                    viewModel.getMovieListByGenre(genre).collect { response ->
                        when (response) {
                            // ignore in error or loading
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

    private fun startLoading() = with(binding) {
        homeRefresher.isRefreshing = true
    }

    private fun stopLoading() = with(binding) {
        homeRefresher.isRefreshing = false
    }

    private fun onMovieItemClick(movie: Movie) {
        navController.navigate(
            FragmentHomeDirections.actionFragmentHomeToFragmentMovieInfo(movie)
        )
    }

    private fun onGenreHolderMoreClick(genre: Genre) {
        navController.navigate(
            FragmentHomeDirections.actionFragmentHomeToFragmentMovieList(genre)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}