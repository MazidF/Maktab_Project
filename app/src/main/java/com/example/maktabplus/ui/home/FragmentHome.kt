package com.example.maktabplus.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.maktabplus.R
import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.model.movie.GenreWithMovies
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.databinding.FragmentHomeBinding
import com.example.maktabplus.utils.Result
import com.example.maktabplus.utils.Result.Companion.map
import com.example.maktabplus.utils.repeatingLaunch
import kotlinx.coroutines.launch

class FragmentHome : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private val viewModel: ViewModelHome by viewModels()
    private val holderAdapter = GeneHolderAdapter(
        onItemClick = this::onMovieItemClick,
        onMoreClick = this::onGenreHolderMoreClick
    )

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

    private fun observer() {
        repeatingLaunch(Lifecycle.State.STARTED) {
            viewModel.genresFlow.collect {
                when(it) {
                    is Result.Error -> {  }
                    is Result.Loading -> {  }
                    is Result.Success -> {
                        val list = it.data?.map { genre ->
                            GenreWithMovies(genre).apply {
                                // TODO: get a list of movies with size 10
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