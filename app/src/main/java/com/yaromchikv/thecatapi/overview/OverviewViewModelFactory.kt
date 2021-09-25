package com.yaromchikv.thecatapi.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yaromchikv.thecatapi.repository.Repository

class OverviewViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OverviewViewModel(repository) as T
    }
}