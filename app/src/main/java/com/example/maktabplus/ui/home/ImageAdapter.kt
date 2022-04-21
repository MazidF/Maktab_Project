package com.example.maktabplus.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.maktabplus.data.model.Image
import com.example.maktabplus.databinding.ImageItemBinding
import javax.inject.Inject

class ImageAdapter @Inject constructor()
    : ListAdapter<Image, ImageAdapter.ImageHolder>(DIFF_CALLCACK) {

    inner class ImageHolder(
        private val binding: ImageItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Image) = with(binding) {
/*            Glide.with(root)
                .load(item.url)
                .transition(DrawableTransitionOptions.withCrossFade(800))
                .into(itemImage)*/
            itemImage.load(item.url) {
                crossfade(true)
                crossfade(800)
            }
            itemTitle.text = item.author
        }
    }

    companion object {
        val DIFF_CALLCACK = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(getItem(position))
    }
}