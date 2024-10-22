package com.example.banquemisrchallenge05.model.remote

import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.model.pojos.MovieResponse
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {

    suspend fun getNowPlayingMovies(page: Int): Flow<MovieResponse>
}