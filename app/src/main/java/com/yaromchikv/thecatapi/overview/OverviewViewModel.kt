package com.yaromchikv.thecatapi.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yaromchikv.thecatapi.model.Cat
import com.yaromchikv.thecatapi.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class OverviewViewModel(private val repository: Repository) : ViewModel() {

    val myResponse : MutableLiveData<Response<List<Cat>>> = MutableLiveData()

    fun getCat() {
        viewModelScope.launch {
            val response = repository.getCat()
            myResponse.value = response
        }
    }

}