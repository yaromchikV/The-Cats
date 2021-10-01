package com.yaromchikv.thecatapi.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yaromchikv.thecatapi.databinding.ItemProgressBinding

class CatsLoadStateAdapter : LoadStateAdapter<CatsLoadStateAdapter.ProgressViewHolder>() {

    override fun onBindViewHolder(holder: ProgressViewHolder, loadState: LoadState) {
        // Do nothing
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ProgressViewHolder {
        return ProgressViewHolder(ItemProgressBinding.inflate(LayoutInflater.from(parent.context)))
    }

    class ProgressViewHolder(binding: ItemProgressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // Do nothing
    }
}
