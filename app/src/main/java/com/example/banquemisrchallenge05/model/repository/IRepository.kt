package com.example.banquemisrchallenge05.model.repository

import com.example.banquemisrchallenge05.model.pojos.Movie
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun getNowPlayingMovies(page: Int): Flow<List<Movie>>

}