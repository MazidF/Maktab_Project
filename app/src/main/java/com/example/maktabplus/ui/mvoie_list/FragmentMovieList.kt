package com.example.maktabplus.ui.mvoie_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.maktabplus.R
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.databinding.FragmentMovieListBinding
import com.example.maktabplus.ui.MovieAdapter
import com.example.maktabplus.utils.Result
import com.example.maktabplus.utils.repeatingLaunch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
class FragmentMovieList : Fragment(R.layout.fragment_movie_list) {
    private val navController by lazy {
        findNavController()
    }
    private var _binding: FragmentMovieListBinding? = null
    private val binding: FragmentMovieListBinding
        get() = _binding!!

    private val args: FragmentMovieListArgs by navArgs()
    private val viewModel: ViewModelMovieList by viewModels()

    private var page = 1
    private var job: Job? = null

    private val movieAdapter = MovieAdapter {
        navController.navigate(
            FragmentMovieListDirections.actionFragmentMovieListToFragmentMovieInfo(
                it
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieListBinding.bind(view)
        init()
        observer()
    }

    private fun init() = with(binding) {
        movieListItems.apply {
            this.adapter = movieAdapter
            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        job?.cancel()
                        job = viewModel.getMovieListByGenre(args.genre, ++page)
                    }
                }
            })
        }
        viewModel.getMovieListByGenre(args.genre, page)
    }

    private fun observer() {
        repeatingLaunch(Lifecycle.State.STARTED) {
            viewModel.movieListFlow.collect { it ->
                when (it) {
                    is Result.Error -> {

                    }
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        it.data?.let { list ->
                            movieAdapter.submitList(movieAdapter.currentList + list)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.movieListItems.adapter = null
        _binding = null
    }

}