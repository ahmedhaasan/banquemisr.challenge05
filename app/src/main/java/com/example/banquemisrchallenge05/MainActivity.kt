package com.example.banquemisrchallenge05

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.banquemisrchallenge05.network.NetworkObserver
import com.example.banquemisrchallenge05.ui.theme.navigation.navigation

class MainActivity : ComponentActivity() {

    private lateinit var networkObserver: NetworkObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initialize network observer
        networkObserver = NetworkObserver(applicationContext)
        networkObserver.Register()  // register it

        enableEdgeToEdge()
        setContent {
            navigation(networkObserver)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkObserver.unRegister()  // Unregister the network callback
    }
}

