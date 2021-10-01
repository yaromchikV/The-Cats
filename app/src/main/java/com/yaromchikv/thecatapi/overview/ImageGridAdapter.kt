package com.yaromchikv.thecatapi.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.yaromchikv.thecatapi.R
import com.yaromchikv.thecatapi.databinding.ItemCatBinding
import com.yaromchikv.thecatapi.model.Cat

private const val CROSSFADE_VALUE = 100

class ImageGridAdapter(val onClickListener: OnClickListener) :
    PagingDataAdapter<Cat, ImageGridAdapter.CatViewHolder>(DiffCallback) {

    inner class CatViewHolder(private var binding: ItemCatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cat: Cat?) {
            if (cat != null) {
                binding.imageView.heightRatio = cat.height.toFloat() / cat.width.toFloat()

                binding.imageView.load(cat.imageUrl) {
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_broken_image)
                    crossfade(true)
                    crossfade(CROSSFADE_VALUE)
                }

                itemView.setOnClickListener {
                    onClickListener.onClick(cat)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(ItemCatBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Cat>() {
        override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem.id == newItem.id && oldItem.imageUrl == newItem.imageUrl
        }
    }

    class OnClickListener(val clickListener: (cat: Cat) -> Unit) {
        fun onClick(cat: Cat) = clickListener(cat)
    }
}
