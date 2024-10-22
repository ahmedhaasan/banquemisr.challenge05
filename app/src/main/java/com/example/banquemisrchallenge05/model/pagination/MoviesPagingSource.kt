package com.example.banquemisrchallenge05.model.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.banquemisrchallenge05.model.pojos.Movie

/**
 *      trying to implement the pagingation concept
 */
class MoviesPagingSource :PagingSource<Int, Movie>(){
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        TODO("Not yet implemented")
    }

}