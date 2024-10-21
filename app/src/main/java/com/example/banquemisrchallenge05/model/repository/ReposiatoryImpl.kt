package com.example.banquemisrchallenge05.model.repository

import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.model.remote.IRemoteDataSource
import kotlinx.coroutines.flow.Flow


class ReposiatoryImpl(val remote: IRemoteDataSource) : IRepository {
    override suspend fun getNowPlayingMovies(page: Int): Flow<List<Movie>> {
        return remote.getNowPlayingMovies(page)
    }
}