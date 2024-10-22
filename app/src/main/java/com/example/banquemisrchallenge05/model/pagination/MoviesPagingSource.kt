package com.example.banquemisrchallenge05.model.pagination

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.banquemisrchallenge05.model.pojos.Movie
import java.time.LocalDateTime

/**
 *      trying to implement the pagingation concept
 */
/*
@RequiresApi(Build.VERSION_CODES.O)
private val firstMovieCreationTime = LocalDateTime.now()
class MoviesPagingSource :PagingSource<Int, Movie>(){
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val start = params.key ?: STARTING_KEY
        val range = start.until(start + params.loadSize)
    }

    private fun ensureValidKey(key: Int) = Integer.max(STARTING_KEY, key)

}*/
