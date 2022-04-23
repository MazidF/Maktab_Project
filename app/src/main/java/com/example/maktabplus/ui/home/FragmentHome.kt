package com.example.maktabplus.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.maktabplus.R
import com.example.maktabplus.databinding.FragmentHomeBinding
import com.example.maktabplus.utils.Result
import com.example.maktabplus.utils.logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FragmentHome : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private val viewModel: ViewModelHome by viewModels()

    @Inject lateinit var movieAdapter: MovieAdapter // public

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        init()
        observer()
    }

    private fun init() = with(binding) {
        homeList.apply {
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
        binding.homeList.adapter = null
        _binding = null
    }

}