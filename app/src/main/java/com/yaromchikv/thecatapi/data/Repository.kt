package com.yaromchikv.thecatapi.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.yaromchikv.thecatapi.api.RetrofitInstance
import com.yaromchikv.thecatapi.api.TheCatApiService
import com.yaromchikv.thecatapi.model.Cat

class Repository {

    private val apiService: TheCatApiService = RetrofitInstance.api

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }

    fun getCatImagesLiveData(pagingConfig: PagingConfig = getDefaultPageConfig()): LiveData<PagingData<Cat>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { TheCatsPagingSource(apiService) }
        ).liveData
    }

    companion object {
        val DEFAULT_PAGE_INDEX = (1..500).random()
        const val FIRST_PAGE = 1
        const val DEFAULT_PAGE_SIZE = 10
    }
}
