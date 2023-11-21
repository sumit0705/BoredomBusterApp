package com.example.boredombusterapp.api

import com.example.boredombusterapp.models.Activity
import retrofit2.http.GET

/**
 * Retrofit service interface defining API endpoints.
 * Each function corresponds to a specific API endpoint, annotated with HTTP methods.
 */
interface ApiService {

    /**
     * Endpoint to fetch activity data.
     * Used suspend function for asynchronous execution.
     *
     * @return [Activity] containing the data.
     */
    @GET("activity")
    suspend fun getActivity(): Activity
}