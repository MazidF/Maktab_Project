package com.example.maktabplus.ui.home

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.maktabplus.R
import com.example.maktabplus.data.model.movie.Movie
import com.example.maktabplus.databinding.MovieItemBinding
import javax.inject.Inject

class MovieAdapter @Inject constructor()
    : ListAdapter<Movie, MovieAdapter.MovieHolder>(DIFF_CALLBACK) {

    inner class MovieHolder(
        private val binding: MovieItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private val errorPlaceholder: Drawable? = null

        fun bind(item: Movie) = with(binding) {
            Glide.with(root)
                .load(root.context.getString(R.string.tmdb_poster_baseurl) + item.poster)
                .error(errorPlaceholder)
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .into(itemImage)
            itemTitle.text = item.title
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(getItem(position))
    }
}