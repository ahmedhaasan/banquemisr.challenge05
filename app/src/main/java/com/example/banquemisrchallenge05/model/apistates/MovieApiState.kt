package com.example.banquemisrchallenge05.model.apistates

import com.example.banquemisrchallenge05.model.pojos.Movie

sealed class MovieApiState {

    object Loading : MovieApiState()
    class Failure (val msg :Throwable) : MovieApiState()
    class Success(val movies: List<Movie>) : MovieApiState()
}