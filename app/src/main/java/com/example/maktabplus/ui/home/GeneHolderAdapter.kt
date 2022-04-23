package com.example.maktabplus.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.maktabplus.data.model.movie.Genre
import com.example.maktabplus.data.model.movie.GenreWithMovies
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.databinding.GenreListBinding
import com.example.maktabplus.ui.MovieAdapter
import com.example.maktabplus.utils.observeOnce

class GeneHolderAdapter(
    private val onItemClick: (Movie) -> Unit,
    private val onMoreClick: (Genre) -> Unit
) : ListAdapter<GenreWithMovies, GeneHolderAdapter.GenreHolder>(DIFF_CALLBACK) {
    private val viewHolderPool = RecyclerView.RecycledViewPool()

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GenreWithMovies>() {
            override fun areItemsTheSame(
                oldItem: GenreWithMovies,
                newItem: GenreWithMovies
            ): Boolean {
                return oldItem.genre.id == newItem.genre.id
            }

            override fun areContentsTheSame(
                oldItem: GenreWithMovies,
                newItem: GenreWithMovies
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class GenreHolder(
        private val binding: GenreListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val movieAdapter = MovieAdapter(onItemClick)
        private lateinit var genre: Genre

        init {
            with(binding) {
                genreHolderItems.apply {
                    adapter = movieAdapter
                    this.setRecycledViewPool(viewHolderPool)
                }
                genreHolderTop.setOnClickListener {
                    onMoreClick(genre)
                }
            }
        }

        fun bind(item: GenreWithMovies) = with(binding) {
            genre = item.genre
            item.hasLoaded.apply {
                if (value != true) {
                    observeOnce {
                        if (it)
                            movieAdapter.submitList(item.movies)
                    }
                }
            }
            movieAdapter.submitList(item.movies)
            genreHolderTitle.text = item.genre.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreHolder {
        val binding = GenreListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreHolder, position: Int) {
        holder.bind(getItem(position))
    }
}