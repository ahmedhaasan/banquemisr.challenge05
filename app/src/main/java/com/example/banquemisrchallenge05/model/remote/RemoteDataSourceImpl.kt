package com.example.banquemisrchallenge05.model.remote

import com.example.banquemisrchallenge05.model.apis.MovieRetrofitHelper
import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.model.pojos.MovieDetailsResponse
import com.example.banquemisrchallenge05.model.pojos.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RemoteDataSourceImpl : IRemoteDataSource {
    override suspend fun getNowPlayingMovies(page: Int): Flow<MovieResponse> {

        val response = MovieRetrofitHelper.service.getNowPlayingMovies(page = page)
        return flowOf(response)
    }

    override suspend fun getPopularMovies(page: Int): Flow<MovieResponse> {
        val response = MovieRetrofitHelper.service.getPopularMovies(page = page)
        return flowOf(response)
    }

    override suspend fun getUpcomingMovies(page: Int): Flow<MovieResponse> {
        val response = MovieRetrofitHelper.service.getUpcomingMovies(page = page)
        return flowOf(response)
    }

    override suspend fun getMovieDetailsById(movieId: Int): Flow<MovieDetailsResponse> {
        val response = MovieRetrofitHelper.service.getMovieDetailsById(movieId = movieId)
        return flowOf(response)
    }
}