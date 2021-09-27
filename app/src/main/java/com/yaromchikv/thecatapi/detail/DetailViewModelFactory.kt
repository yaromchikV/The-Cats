package com.yaromchikv.thecatapi.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yaromchikv.thecatapi.model.Cat

class DetailViewModelFactory(private val cat: Cat): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(cat) as T
    }
}