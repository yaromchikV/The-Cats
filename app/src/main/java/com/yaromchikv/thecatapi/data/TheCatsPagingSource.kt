package com.yaromchikv.thecatapi.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yaromchikv.thecatapi.api.TheCatApiService
import com.yaromchikv.thecatapi.data.Repository.Companion.DEFAULT_PAGE_INDEX
import com.yaromchikv.thecatapi.data.Repository.Companion.DEFAULT_PAGE_SIZE
import com.yaromchikv.thecatapi.data.Repository.Companion.FIRST_PAGE
import com.yaromchikv.thecatapi.model.Cat
import okio.IOException
import retrofit2.HttpException

class TheCatsPagingSource(private val apiService: TheCatApiService) : PagingSource<Int, Cat>() {

    override fun getRefreshKey(state: PagingState<Int, Cat>) = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
        return try {
            val page = params.key ?: DEFAULT_PAGE_INDEX
            val response = apiService.getCats(page)
            if (response.isSuccessful && response.body()?.isNotEmpty() == true) {
                val listOfCats = checkNotNull(response.body())
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                val nextKey = if (listOfCats.size < DEFAULT_PAGE_SIZE) FIRST_PAGE else page + 1
                LoadResult.Page(listOfCats, prevKey, nextKey)
            } else LoadResult.Error(HttpException(response))
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}
