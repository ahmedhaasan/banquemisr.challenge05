package com.example.banquemisrchallenge05.model.remote

import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.model.pojos.MovieDetailsResponse
import com.example.banquemisrchallenge05.model.pojos.MovieResponse
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {

    suspend fun getNowPlayingMovies(page: Int): Flow<MovieResponse>
    suspend fun getPopularMovies(page: Int): Flow<MovieResponse>
    suspend fun getUpcomingMovies(page: Int): Flow<MovieResponse>
    suspend fun getMovieDetailsById(movieId: Int): Flow<MovieDetailsResponse>
}