package com.example.boredombusterapp.repo

import com.example.boredombusterapp.api.ApiClient
import com.example.boredombusterapp.database.SharedPreferencesHelper
import com.example.boredombusterapp.models.Activity

/**
 * Single repository which helps in fetching activity data from API/database and saving activity
 * data in database.
 */
class Repository(private val sharedPreferencesHelper: SharedPreferencesHelper) {

    /** This method will help in saving data in database via [sharedPreferencesHelper]. */
    suspend fun saveData(key: String, value: String) {
        sharedPreferencesHelper.saveData(key, value)
    }

    /** This method will help in retrieving data from database via [sharedPreferencesHelper]. */
    suspend fun getData(key: String): String? {
        return sharedPreferencesHelper.getData(key)
    }

    /** This method will help in removing all values from the [sharedPreferencesHelper]. */
    fun clearCache() {
        sharedPreferencesHelper.clearCache()
    }

    /** This method will help in fetching activity data via [ApiClient.api]. */
    suspend fun getActivity(): Activity {
        return ApiClient.api.getActivity()
    }
}