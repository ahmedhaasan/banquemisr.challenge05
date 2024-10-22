package com.example.banquemisrchallenge05.model.repository

import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.model.pojos.MovieResponse
import com.example.banquemisrchallenge05.model.remote.IRemoteDataSource
import kotlinx.coroutines.flow.Flow


class ReposiatoryImpl(val remote: IRemoteDataSource) : IRepository {
    override suspend fun getNowPlayingMovies(page: Int): Flow<MovieResponse> {
        return remote.getNowPlayingMovies(page)
    }

    override suspend fun getPopularMovies(page: Int): Flow<MovieResponse> {
        return remote.getPopularMovies(page)
    }

    override suspend fun getUpcomingMovies(page: Int): Flow<MovieResponse> {
        return remote.getUpcomingMovies(page)
    }
}