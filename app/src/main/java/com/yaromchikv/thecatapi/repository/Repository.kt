package com.yaromchikv.thecatapi.repository

import com.yaromchikv.thecatapi.api.MyInterceptor
import com.yaromchikv.thecatapi.api.RetrofitInstance
import com.yaromchikv.thecatapi.model.Cat
import retrofit2.Response

class Repository {

    suspend fun getCat(): Response<List<Cat>> {
        return RetrofitInstance.api.getCat()
    }
}