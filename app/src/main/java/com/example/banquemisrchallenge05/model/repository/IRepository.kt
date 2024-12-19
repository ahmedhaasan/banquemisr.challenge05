package com.example.banquemisrchallenge05.model.repository

import androidx.paging.PagingData
import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.model.pojos.MovieDetailsResponse
import com.example.banquemisrchallenge05.model.pojos.MovieResponse
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun getNowPlayingMovies(page: Int): Flow<PagingData<Movie>>
    suspend fun getPopularMovies(page: Int): Flow<PagingData<Movie>>
    suspend fun getUpcomingMovies(page: Int): Flow<PagingData<Movie>>
    // details below
    suspend fun getMovieDetailsById(movieId: Int): Flow<MovieDetailsResponse>

    // search on movies
    suspend fun searchMovies(query: String): Flow<List<Movie>>



}