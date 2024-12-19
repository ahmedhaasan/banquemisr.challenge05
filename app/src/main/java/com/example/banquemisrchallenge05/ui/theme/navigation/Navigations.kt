package com.example.banquemisrchallenge05.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.banquemisrchallenge05.model.network.NetworkObserver
import com.example.banquemisrchallenge05.ui.theme.screens.DetailsScreen
import com.example.banquemisrchallenge05.ui.theme.screens.HomeScreen
import com.example.banquemisrchallenge05.viewmodels.MoviesViewModel

import androidx.hilt.navigation.compose.hiltViewModel
import com.example.banquemisrchallenge05.ui.theme.screens.SearchScreen

sealed class Screens(val route: String) {
    object  SearchScreen: Screens("search_screen")
    object HomeScreen : Screens("home_screen")
    object DetailScreen : Screens("detail_screen/{movie_id}") {
        fun createRoute(movie_id: Int): String {
            return "detail_screen/$movie_id"
        }
    }
}

// Initialize dependencies   // and a new way to pass the MovieApi instead of RemoteDataSourceImpl
/*val movieApi = AppModule.provideMovieApi()
val repository: IRepository = RepositoryImpl(RemoteDataSourceImpl(movieApi))
val moviesViewModelFactory = MoviesViewModelFactory(repository)*/
@Composable
fun navigation(networkObserver: NetworkObserver) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.HomeScreen.route) {
        composable(route = Screens.HomeScreen.route) {
            val moviesViewModel: MoviesViewModel = hiltViewModel() // Hilt to get ViewModel

            HomeScreen(navController, moviesViewModel, networkObserver)
        }

        composable(
            route = Screens.DetailScreen.route,
            arguments = listOf(navArgument("movie_id") { type = NavType.IntType })

        ) { backStackEntry ->
            val movie_id = backStackEntry.arguments?.getInt("movie_id")
            if (movie_id != null) {
                val moviesViewModel: MoviesViewModel = hiltViewModel() // Hilt for ViewModel new Way
                DetailsScreen(navController, movie_id, moviesViewModel,networkObserver)
            }
        }

        //  search composable
        composable(route = Screens.SearchScreen.route) {
            val moviesViewModel: MoviesViewModel = hiltViewModel()
            SearchScreen(navController, moviesViewModel, networkObserver)

        }

    }
}