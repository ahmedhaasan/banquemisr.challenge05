package com.example.banquemisrchallenge05.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.banquemisrchallenge05.model.remote.RemoteDataSourceImpl
import com.example.banquemisrchallenge05.model.repository.IRepository
import com.example.banquemisrchallenge05.model.repository.ReposiatoryImpl
import com.example.banquemisrchallenge05.ui.theme.screens.DetailsScreen
import com.example.banquemisrchallenge05.ui.theme.screens.HomeScreen
import com.example.banquemisrchallenge05.viewmodels.MoviesViewModel
import com.example.banquemisrchallenge05.viewmodels.MoviesViewModelFactory


sealed class Screens (val route:String){
    object HomeScreen : Screens("home_screen")
    object DetailScreen : Screens("detail_screen")
}

// define the factories for the viewModels

val reposiatory : IRepository = ReposiatoryImpl(RemoteDataSourceImpl())
val moviesViewModelFactory = MoviesViewModelFactory(reposiatory)

@Composable
fun navigation(){

    val navController = rememberNavController()

    NavHost(navController = navController , startDestination = Screens.HomeScreen.route){
        composable(route = Screens.HomeScreen.route){
            val moviesViewModel: MoviesViewModel = viewModel(factory = moviesViewModelFactory) // create the viewModel
            HomeScreen(navController,moviesViewModel)
        }

        composable(route = Screens.DetailScreen.route){
            DetailsScreen()
        }


    }
}