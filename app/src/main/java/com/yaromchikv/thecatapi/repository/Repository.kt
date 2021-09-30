package com.yaromchikv.thecatapi.repository

import com.yaromchikv.thecatapi.api.RetrofitInstance
import com.yaromchikv.thecatapi.model.Cat
import retrofit2.Response

class Repository {

    suspend fun getCats(page: Int): Response<List<Cat>> {
        return RetrofitInstance.api.getCats(page)
    }
}
