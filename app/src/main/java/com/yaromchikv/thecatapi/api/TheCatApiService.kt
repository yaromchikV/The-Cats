package com.yaromchikv.thecatapi.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yaromchikv.thecatapi.model.Cat
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

private const val BASE_URL = "https://api.thecatapi.com"

interface TheCatApiService {

    @GET("v1/images/search?limit=20&page=1&order=rand")
    suspend fun getCat(): Response<List<Cat>>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object RetrofitInstance {

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    }.build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(client)
            .build()
    }

    val api: TheCatApiService by lazy {
        retrofit.create(TheCatApiService::class.java)
    }
}