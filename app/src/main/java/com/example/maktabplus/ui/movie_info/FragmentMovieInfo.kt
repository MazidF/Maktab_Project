package com.example.maktabplus.ui.movie_info

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.maktabplus.R
import com.example.maktabplus.data.model.MovieDetailWithGenres
import com.example.maktabplus.data.model.MovieDetailWithGenres.Companion.empty
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.databinding.FragmentMovieInfoBinding
import com.example.maktabplus.ui.MovieAdapter
import com.example.maktabplus.utils.Constants
import com.example.maktabplus.utils.Result
import com.example.maktabplus.utils.onMain
import com.example.maktabplus.utils.repeatingLaunch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FragmentMovieInfo : Fragment(R.layout.fragment_movie_info) {
    private val args: FragmentMovieInfoArgs by navArgs()
    private val navController by lazy {
        findNavController()
    }

    private var _binding: FragmentMovieInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViewModelMovieInfo by viewModels()
    private val movieAdapter = MovieAdapter(this::onItemClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieInfoBinding.bind(view)
        init()
        observer()
    }

    private fun init() = with(binding) {
        viewModel.getMovieInfo(args.movie.id)
        movieInfoSimilarMovies.apply {
            adapter = movieAdapter
        }
        movieInfoRefresher.setOnRefreshListener {
            viewModel.getMovieInfo(args.movie.id)
        }
    }

    private fun setViews(movieDetailWithGenres: MovieDetailWithGenres = empty) = with(binding) {
        val movie = args.movie
        val detail = movieDetailWithGenres.detail
        val genres = movieDetailWithGenres.genres
        similarMoviesInit()
        movieInfoBackdrop.apply {
            val backdrop =
                if (detail.backdrop.isNotBlank()) detail.getBackdropPath() else movie.getPosterPath()
            loadImage(backdrop, this)
        }
        movieInfoPoster.apply {
            loadImage(movie.getPosterPath(), this)
        }
        movieInfoTitle.apply {
            text = movie.title
        }
        movieInfoOverview.apply {
            text = detail.overview
        }
    }

    private fun similarMoviesInit() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getSimilarMovies(args.movie.id).collect {
                when (it) {
                    is Result.Error -> {
                        println()
                    }
                    is Result.Loading -> {
                        println()
                    }
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            movieAdapter.submitList(it.data)
                        }
                    }
                }
            }
        }
    }

    private fun loadImage(
        url: String,
        target: ImageView,
        placeholder: Int = R.drawable.movie_place_holder
    ) {
        Glide.with(requireContext())
            .load(url)
            .placeholder(placeholder)
            .transition(DrawableTransitionOptions.withCrossFade(200))
            .error(placeholder)
            .into(target)
    }

    private fun observer() {
        repeatingLaunch(Lifecycle.State.STARTED) {
            viewModel.movieDetailFlow.collect {
                when (it) {
                    is Result.Error -> {
                        it.snackBar(binding.root) {
                            viewModel.getMovieInfo(args.movie.id)
                        }
                        stopLoading()
                    }
                    is Result.Loading -> {
                        startLoading()
                    }
                    is Result.Success -> {
                        onMain {
                            if (it.data != null) {
                                setViews(it.data)
                            } else {
//                                Result.error<Unit>(Constants.UNABLE_TO_CONNECT)
//                                    .snackBar(binding.root) {
//                                        viewModel.getMovieInfo(args.movie.id)
//                                    }
                            }
                            stopLoading()
                        }
                    }
                }
            }
        }
    }

    private fun startLoading() {
        binding.movieInfoRefresher.isRefreshing = true
    }

    private fun stopLoading() {
        binding.movieInfoRefresher.isRefreshing = false
    }

    private fun onItemClick(movie: Movie) {
        navController.navigate(FragmentMovieInfoDirections.actionFragmentMovieInfoSelf(movie))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}