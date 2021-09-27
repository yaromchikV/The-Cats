package com.yaromchikv.thecatapi.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yaromchikv.thecatapi.model.Cat
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.thecatapi.com"

interface TheCatApiService {

    @GET("v1/images/search?limit=30&page=1&order=rand")
    suspend fun getCat(): Response<List<Cat>>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
    }

    val api: TheCatApiService by lazy {
        retrofit.create(TheCatApiService::class.java)
    }
}