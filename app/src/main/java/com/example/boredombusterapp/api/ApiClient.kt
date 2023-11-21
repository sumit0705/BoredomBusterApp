package com.example.boredombusterapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Singleton class responsible for providing a configured Retrofit instance.
 * The base URL and converter factory are set during initialization.
 */
object ApiClient {

    // Base URL of the API.
    private const val BASE_URL = "https://www.boredapi.com/api/"

    // Retrofit instance with Gson converter factory.
    private val retrofit by lazy {
        val httpClient = OkHttpClient.Builder()
            .readTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(2, TimeUnit.MINUTES)

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    // ApiService instance created by Retrofit.
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}