package com.example.maktabplus.ui.search

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.maktabplus.R
import com.example.maktabplus.databinding.FragmentSearchBinding
import com.example.maktabplus.ui.MovieAdapter
import com.example.maktabplus.utils.Result
import com.example.maktabplus.utils.repeatingLaunch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
class FragmentSearch : Fragment(R.layout.fragment_search) {
    private val navController by lazy {
        findNavController()
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    private val viewModel: ViewModelSearch by viewModels()
    private var page = 1
    private var job: Job? = null
    private var lastQuery: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        init()
        observer()
    }

    private val movieAdapter = MovieAdapter {
        navController.navigate(
            FragmentSearchDirections.actionFragmentSearchToFragmentMovieInfo(
                it
            )
        )
    }

    private fun init() = with(binding) {
        searchList.apply {
            adapter = movieAdapter
            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        lastQuery?.let {
                            job?.cancel()
                            job = viewModel.searchByQuery(it, ++page)
                        }
                    }
                }
            })
        }
        searchSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                page = 1
                lastQuery = query.trim()
                job?.cancel()
                job = viewModel.searchByQuery(lastQuery!!, page)
                return true
            }
        })
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
                        it.data?.let { list ->
                            if (page == 1) {
                                movieAdapter.submitList(list)
                            } else {
                                movieAdapter.submitList(movieAdapter.currentList + list)
                            }
                        }
                    }
                }
            }
        }
    }
}