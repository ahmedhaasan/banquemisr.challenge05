package com.example.banquemisrchallenge05.model.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.banquemisrchallenge05.model.apis.MovieApi
import com.example.banquemisrchallenge05.model.pagination.MoviePagingSource
import com.example.banquemisrchallenge05.model.pagination.MovieType
import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.model.pojos.MovieDetailsResponse
import com.example.banquemisrchallenge05.model.remote.IRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RepositoryImpl(
    private val remote: IRemoteDataSource
) : IRepository {
    override suspend fun getNowPlayingMovies(page: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                MoviePagingSource(remote, MovieType.NOW_PLAYING)
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
                MoviePagingSource(remote, MovieType.POPULAR)
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
                MoviePagingSource(remote, MovieType.UPCOMING)
            }
        ).flow
    }


    // details below
    override suspend fun getMovieDetailsById(movieId: Int): Flow<MovieDetailsResponse> {
        return remote.getMovieDetailsById(movieId)
    }

}