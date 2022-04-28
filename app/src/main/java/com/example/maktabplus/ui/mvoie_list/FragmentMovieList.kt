package com.example.maktabplus.ui.mvoie_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.maktabplus.R
import com.example.maktabplus.databinding.FragmentMovieListBinding
import com.example.maktabplus.ui.MovieAdapter
import com.example.maktabplus.utils.Result
import com.example.maktabplus.utils.repeatingLaunch
import dagger.hilt.android.AndroidEntryPoint

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
//            LinearSnapHelper().attachToRecyclerView(this)
        }
    }

    private fun observer() {
        repeatingLaunch(Lifecycle.State.STARTED) {
            viewModel.movieListFlow.collect {
                when (it) {
                    is Result.Error -> {

                    }
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        movieAdapter.submitList(it.data)
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