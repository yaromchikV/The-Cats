package com.yaromchikv.thecatapi.ui.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yaromchikv.thecatapi.databinding.ItemProgressBinding

class ProgressStateAdapter : LoadStateAdapter<ProgressStateAdapter.ProgressViewHolder>() {

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
