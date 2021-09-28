package com.yaromchikv.thecatapi.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.thecatapi.model.Cat
import com.yaromchikv.thecatapi.repository.Repository
import kotlinx.coroutines.launch

class OverviewViewModel(private val repository: Repository) : ViewModel() {

    val listOfCats = mutableListOf<Cat>()

    private var page = 1

    private val _isLoaded = MutableLiveData<Boolean>()
    val isUploaded: LiveData<Boolean>
        get() = _isLoaded

    init {
        getCats(page)
    }

    private fun getCats(page: Int) {
        viewModelScope.launch {
            _isLoaded.value = true

            val response = repository.getCats(page)
            listOfCats += (response.body() as MutableList<Cat>)

            _isLoaded.value = false
        }
    }

    fun loadMoreCats() {
        getCats(++page)
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