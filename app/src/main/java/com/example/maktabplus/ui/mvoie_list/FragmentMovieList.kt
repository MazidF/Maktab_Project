package com.example.maktabplus.ui.mvoie_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.maktabplus.databinding.FragmentMovieListBinding
import com.example.maktabplus.ui.MovieAdapter
import com.example.maktabplus.utils.Result
import com.example.maktabplus.utils.logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FragmentMovieList : Fragment() {
    private var _binding: FragmentMovieListBinding? = null
    private val binding: FragmentMovieListBinding
        get() = _binding!!

    private val args: FragmentMovieListArgs by navArgs()
    private val viewModel: ViewModelMovieList by viewModels()

    val movieAdapter = MovieAdapter {

    } // public

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
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieListFlow.collect {
                    logger(it.toString())
                    when(it) {
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

        lifecycleScope.launch {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.movieListItems.adapter = null
        _binding = null
    }

}