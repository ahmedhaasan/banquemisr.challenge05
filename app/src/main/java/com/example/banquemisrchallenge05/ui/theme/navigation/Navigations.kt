package com.example.banquemisrchallenge05.ui.theme.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.model.remote.RemoteDataSourceImpl
import com.example.banquemisrchallenge05.model.repository.IRepository
import com.example.banquemisrchallenge05.model.repository.ReposiatoryImpl
import com.example.banquemisrchallenge05.ui.theme.screens.DetailsScreen
import com.example.banquemisrchallenge05.ui.theme.screens.HomeScreen
import com.example.banquemisrchallenge05.viewmodels.MoviesViewModel
import com.example.banquemisrchallenge05.viewmodels.MoviesViewModelFactory
import com.google.gson.Gson
import java.net.URLEncoder


sealed class Screens(val route: String) {
    object HomeScreen : Screens("home_screen")
    object DetailScreen : Screens("detail_screen/{movie_id}") {
        fun createRoute(movie_id: Int): String {
            return "detail_screen/$movie_id"
        }
    }
}

// define the factories for the viewModels

val reposiatory: IRepository = ReposiatoryImpl(RemoteDataSourceImpl())
val moviesViewModelFactory = MoviesViewModelFactory(reposiatory)

@Composable
fun navigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.HomeScreen.route) {
        composable(route = Screens.HomeScreen.route) {
            val moviesViewModel: MoviesViewModel =
                viewModel(factory = moviesViewModelFactory) // create the viewModel
            HomeScreen(navController, moviesViewModel)
        }

        composable(
            route = Screens.DetailScreen.route,
            arguments = listOf(navArgument("movie_id") { type = NavType.IntType })

        ) { backStackEntry ->
            val movie_id = backStackEntry.arguments?.getInt("movie_id")
            if (movie_id != null) {
                val moviesViewModel: MoviesViewModel =
                    viewModel(factory = moviesViewModelFactory) // create the viewModel
                DetailsScreen(navController, movie_id, moviesViewModel)
            }
        }


    }
}