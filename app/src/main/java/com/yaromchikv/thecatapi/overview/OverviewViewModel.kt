package com.yaromchikv.thecatapi.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.yaromchikv.thecatapi.data.Repository
import com.yaromchikv.thecatapi.model.Cat

class OverviewViewModel : ViewModel() {

    private val repository = Repository()
    val cats by lazy {
        repository.getCatImagesLiveData().cachedIn(viewModelScope)
    }

    private val _navigateToSelectedCat = MutableLiveData<Cat>()
    val navigateToSelectedCat: LiveData<Cat>
        get() = _navigateToSelectedCat

    fun displayCatDetails(cat: Cat) {
        _navigateToSelectedCat.value = cat
    }

    fun displayCatDetailsComplete() {
        _navigateToSelectedCat.value = null
    }
}
