package com.example.banquemisrchallenge05.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.banquemisrchallenge05.model.apistates.MovieApiState
import com.example.banquemisrchallenge05.model.repository.IRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 *      create the viewModel to fetch the movies Data ( now playing ), popular ,upcomming
 *      working with State Flow
 */
class MoviesViewModel(private val repository: IRepository) : ViewModel() {

    private val _nowPlayingMovies = MutableStateFlow<MovieApiState>(MovieApiState.Loading)
    val nowPlayingMovies: StateFlow<MovieApiState> = _nowPlayingMovies // Change var to val
    ////
    fun getNowPlayingMovies(page: Int) {
        viewModelScope.launch {
            repository.getNowPlayingMovies(page)
                .catch { error -> _nowPlayingMovies.value = MovieApiState.Failure(error) } // Update _nowPlayingMovies here
                .collect { movies -> _nowPlayingMovies.value = MovieApiState.Success(movies) } // Same here
        }
    }

}