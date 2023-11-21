package com.example.boredombusterapp.utils

import android.net.ConnectivityManager
import android.net.Network

/** Listener class to show a Snackbar whenever the internet connectivity status changes. */
class NetworkCallbackListener(
    private val onInternetConnected: () -> Unit,
    private val onInternetDisconnected: () -> Unit
) {

    // This method will create this network callback.
    private fun createNetworkCallback(): ConnectivityManager.NetworkCallback {
        return object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                onInternetConnected.invoke()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                onInternetDisconnected.invoke()
            }
        }
    }

    // This method will register this network callback listener.
    fun registerNetworkCallback(connectivityManager: ConnectivityManager) {
        connectivityManager.registerDefaultNetworkCallback(createNetworkCallback())
    }

    // This method will unregister this network callback listener.
    fun unregisterNetworkCallback(connectivityManager: ConnectivityManager) {
        connectivityManager.unregisterNetworkCallback(createNetworkCallback())
    }
}