package com.example.banquemisrchallenge05.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.banquemisrchallenge05.model.apistates.MovieApiState
import com.example.banquemisrchallenge05.model.apistates.MovieDetailsApiState
import com.example.banquemisrchallenge05.model.pagination.MovieType
import com.example.banquemisrchallenge05.model.repository.IRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

/**
 *      create the viewModel to fetch the movies Data ( now playing ), popular ,upcomming
 *      working with State Flow
 */


class MoviesViewModel(private val repository: IRepository) : ViewModel() {


    // start change to use the pagging
     var currentMovieType = MutableStateFlow(MovieType.NOW_PLAYING)

    val movies = currentMovieType.flatMapLatest { movieType ->
        when (movieType) {
            MovieType.NOW_PLAYING -> repository.getNowPlayingMovies(1)
            MovieType.POPULAR -> repository.getPopularMovies(1)
            MovieType.UPCOMING -> repository.getUpcomingMovies(1)
        }

    }.cachedIn(viewModelScope)

    fun updateMovieType(type: MovieType) {   // to update the movie type and catch new Pages
        currentMovieType.value = type
    }
// get Movie Details
private val _movieDetails = MutableStateFlow<MovieDetailsApiState>(MovieDetailsApiState.Loading)
    val movieDetails: StateFlow<MovieDetailsApiState> = _movieDetails.asStateFlow()

fun getMovieDetailsById(movieId: Int) {
    viewModelScope.launch {

        repository.getMovieDetailsById(movieId)
            .catch { error -> _movieDetails.value = MovieDetailsApiState.Failure(error) }
            .collect { movieDetails ->
                _movieDetails.value = MovieDetailsApiState.Success(movieDetails)
            }
    }
}


// create the viewModel Factory
}
class MoviesViewModelFactory(private val repo: IRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }


}