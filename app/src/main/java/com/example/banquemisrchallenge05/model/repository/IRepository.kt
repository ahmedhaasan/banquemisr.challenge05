package com.example.banquemisrchallenge05.model.repository

import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.model.pojos.MovieResponse
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun getNowPlayingMovies(page: Int): Flow<MovieResponse>

}