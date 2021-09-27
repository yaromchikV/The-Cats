package com.yaromchikv.thecatapi.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.thecatapi.model.Cat
import com.yaromchikv.thecatapi.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class OverviewViewModel(private val repository: Repository) : ViewModel() {

    val myResponse: MutableLiveData<Response<List<Cat>>> = MutableLiveData()

    init {
        getCat()
    }

    private fun getCat() {
        viewModelScope.launch {
            val response = repository.getCat()
            myResponse.value = response
        }
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