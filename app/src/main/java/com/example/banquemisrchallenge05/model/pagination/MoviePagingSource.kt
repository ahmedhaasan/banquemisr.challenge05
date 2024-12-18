package com.example.banquemisrchallenge05.model.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.banquemisrchallenge05.model.apis.MovieApi
import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.model.remote.IRemoteDataSource
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

/**
 *      trying to implement the pagingation concept
 */


class MoviePagingSource(
    private val remoteDataSource: IRemoteDataSource,
    private val movieType: MovieType
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = when (movieType) {
                MovieType.NOW_PLAYING -> remoteDataSource.getNowPlayingMovies(page)
                MovieType.POPULAR -> remoteDataSource.getPopularMovies(page)
                MovieType.UPCOMING -> remoteDataSource.getUpcomingMovies(page)
            }.first() // Collect the first value from the Flow

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.results.isEmpty()) null else page + 1
            )
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
