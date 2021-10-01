package com.yaromchikv.thecatapi.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yaromchikv.thecatapi.api.TheCatApiService
import com.yaromchikv.thecatapi.model.Cat
import retrofit2.HttpException

const val DEFAULT_PAGE_INDEX = 10
const val DEFAULT_PAGE_SIZE = 10

class TheCatsPagingSource(
    private val apiService: TheCatApiService
) : PagingSource<Int, Cat>() {

    override fun getRefreshKey(state: PagingState<Int, Cat>) = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        val response = apiService.getCats(page)
        return if (response.isSuccessful) {
            val listOfCats = checkNotNull(response.body())
            val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
            val nextKey = if (listOfCats.size < DEFAULT_PAGE_SIZE) null else page + 1
            LoadResult.Page(listOfCats, prevKey, nextKey)
        } else LoadResult.Error(HttpException(response))
    }
}
