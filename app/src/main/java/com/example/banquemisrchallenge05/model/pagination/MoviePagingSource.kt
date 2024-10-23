package com.example.banquemisrchallenge05.model.pagination

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.banquemisrchallenge05.model.apis.MovieApi
import com.example.banquemisrchallenge05.model.apis.MovieRetrofitHelper
import com.example.banquemisrchallenge05.model.pojos.Movie
import retrofit2.HttpException

/**
 *      trying to implement the pagingation concept
 */


class MoviePagingSource(
    private val movieApi: MovieApi,
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
                MovieType.NOW_PLAYING -> movieApi.getNowPlayingMovies(page.toString())
                MovieType.POPULAR -> movieApi.getPopularMovies(page.toString())
                MovieType.UPCOMING -> movieApi.getUpcomingMovies(page.toString())
            }

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.results.isEmpty()) null else page + 1
            )
        } catch (e: HttpException) {
            Log.e("MoviePagingSource", "HTTP Error: ${e.code()}", e)
            when (e.code()) {
                401 -> Log.e("MoviePagingSource", "Unauthorized - Check your API key", e)
                429 -> Log.e("MoviePagingSource", "Rate limit exceeded", e)
                else -> Log.e("MoviePagingSource", "HTTP Error: ${e.code()}", e)
            }
            LoadResult.Error(e)
        } catch (e: Exception) {
            Log.e("MoviePagingSource", "Error loading data", e)
            LoadResult.Error(e)
        }
    }
}
