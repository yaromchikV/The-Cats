package com.yaromchikv.thecatapi.api

import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("content-type", "application/json")
            .addHeader("x-api-key", API_KEY)
            .build()
        return chain.proceed(request)
    }

    companion object {
        private const val API_KEY = "3a98ca19-d7ae-4ce7-8a5e-c6803fa9364a"
    }
}