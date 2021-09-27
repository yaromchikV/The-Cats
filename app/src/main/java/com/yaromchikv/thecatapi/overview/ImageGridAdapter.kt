package com.yaromchikv.thecatapi.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.OriginalSize
import coil.size.Precision
import com.yaromchikv.thecatapi.R
import com.yaromchikv.thecatapi.databinding.GridItemBinding
import com.yaromchikv.thecatapi.model.Cat

class ImageGridAdapter() :
    ListAdapter<Cat, ImageGridAdapter.CatViewHolder>(DiffCallback) {

    class CatViewHolder(private var binding: GridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cat: Cat) {
            binding.imageView.load(cat.imageUrl) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
                crossfade(true)
                crossfade(300)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(GridItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val currentCat = getItem(position)
        holder.bind(currentCat)
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Cat>() {
        override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
