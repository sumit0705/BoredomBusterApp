package com.example.boredombusterapp.database

import android.content.SharedPreferences

/**
 * A helper class responsible for managing/updating [sharedPreferences] within the MainActivity.
 */
class SharedPreferencesHelper(private val sharedPreferences: SharedPreferences) {

    /**
     * This method will save data in database.
     *  @param key the name of the preference.
     *  @param value the new value for the preference.
     */
    suspend fun saveData(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    /**
     * This method will retrieve data from database.
     *  @param key the name of the preference to retrieve or null if data doesn't exist.
     */
    suspend fun getData(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    /** This method will remove all values from the preference. */
    fun clearCache() {
        sharedPreferences.edit().clear().apply()
    }
}