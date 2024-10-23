package com.example.banquemisrchallenge05.model.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.banquemisrchallenge05.model.apis.MovieApi
import com.example.banquemisrchallenge05.model.pagination.MoviePagingSource
import com.example.banquemisrchallenge05.model.pagination.MovieType
import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.model.pojos.MovieDetailsResponse
import com.example.banquemisrchallenge05.model.pojos.MovieResponse
import com.example.banquemisrchallenge05.model.remote.IRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class ReposiatoryImpl(
    private val movieApi: MovieApi
) : IRepository {
    override suspend fun getNowPlayingMovies(page: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                MoviePagingSource(movieApi, MovieType.NOW_PLAYING)
            }
        ).flow
    }


    override suspend fun getPopularMovies(page: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                MoviePagingSource(movieApi, MovieType.POPULAR)
            }
        ).flow
    }

    override suspend fun getUpcomingMovies(page: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                MoviePagingSource(movieApi, MovieType.UPCOMING)
            }
        ).flow
    }


// details below
override suspend fun getMovieDetailsById(movieId: Int): Flow<MovieDetailsResponse> {
    return flowOf(movieApi.getMovieDetailsById(movieId))
}

}