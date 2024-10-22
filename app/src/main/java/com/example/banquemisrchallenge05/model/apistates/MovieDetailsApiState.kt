package com.example.banquemisrchallenge05.model.apistates

import com.example.banquemisrchallenge05.model.pojos.MovieDetailsResponse

sealed class MovieDetailsApiState {
    class Success(val data: MovieDetailsResponse) : MovieDetailsApiState()
    class Failure(val msg: Throwable) : MovieDetailsApiState()
    object Loading : MovieDetailsApiState()

}