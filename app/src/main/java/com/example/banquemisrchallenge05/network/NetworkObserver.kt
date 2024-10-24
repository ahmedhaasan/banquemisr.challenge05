package com.example.banquemisrchallenge05.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 *      working on NetworkObserver when to observe Connection to Network
 */
class NetworkObserver(context : Context) {

    private val connnectivityManager  = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _isConnected = MutableStateFlow<Boolean>(false)
    val isConnected : StateFlow<Boolean> = _isConnected

    private val networkCallBack = object :ConnectivityManager.NetworkCallback(){

        override fun onAvailable(network: android.net.Network) {
            super.onAvailable(network)
            _isConnected.value = true
        }
        override fun onLost(network: android.net.Network) {
            super.onLost(network)
            _isConnected.value = false
        }
    }

    fun Register(){
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
            connnectivityManager.registerNetworkCallback(networkRequest,networkCallBack)
    }

    fun Unregister(){
        connnectivityManager.unregisterNetworkCallback(networkCallBack)
    }
}