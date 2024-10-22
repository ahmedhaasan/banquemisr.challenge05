package com.example.banquemisrchallenge05.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.banquemisrchallenge05.ui.theme.screens.DetailsScreen
import com.example.banquemisrchallenge05.ui.theme.screens.HomeScreen


sealed class Screens (val route:String){
    object HomeScreen : Screens("home_screen")
    object DetailScreen : Screens("detail_screen")
}


@Composable
fun navigation(){

    val navController = rememberNavController()

    NavHost(navController = navController , startDestination = Screens.HomeScreen){
        composable(route = Screens.HomeScreen.route){
            HomeScreen(navController)
        }

        composable(route = Screens.DetailScreen.route){
            DetailsScreen()
        }


    }
}