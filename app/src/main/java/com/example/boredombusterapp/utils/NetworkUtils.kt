package com.example.boredombusterapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/** Singleton class to handle network-related operations like checking internet connectivity. */
object NetworkUtils {
    // To check if the internet connection is active or not.
    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }
}