package com.example.banquemisrchallenge05.model.remote

import com.example.banquemisrchallenge05.model.apis.MovieRetrofitHelper
import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.model.pojos.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RemoteDataSource  :IRemoteDataSource{
    override suspend fun getNowPlayingMovies(page: Int): Flow<List<Movie>> {

        val response  = MovieRetrofitHelper.service.getNowPlayingMovies(page = page)
        return flowOf(response.results)
    }
}