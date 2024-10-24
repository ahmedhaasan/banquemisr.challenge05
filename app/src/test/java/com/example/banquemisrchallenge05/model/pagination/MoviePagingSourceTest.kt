package com.example.banquemisrchallenge05.model.pagination

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.example.banquemisrchallenge05.model.apis.MovieApi
import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.model.pojos.MovieResponse
import com.example.banquemisrchallenge05.model.remote.IRemoteDataSource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

/**
 *      working to try to test the pagination
 */
class MoviePagingSourceTest {

    private val mockApi = mockk<IRemoteDataSource>()
    private lateinit var pagingSource: MoviePagingSource

// befor

    @Before
    fun setUp() {
        pagingSource = MoviePagingSource(mockApi, MovieType.NOW_PLAYING)

    }

    @Test
   fun LoadPage_returnPageWhenApi_isSuccess() = runTest {

       // Given
       val mockMovie = Movie(
           id = 1,
           title = "Test Movie",
           release_date = "2023-09-01",
           overview = "This is a test movie",
           poster_path = "test_poster_path",
           genre_ids = listOf(1, 2, 3)
       )

        val mockResponse = MovieResponse(
            page = 1,
            results = listOf(mockMovie),
            total_pages = 1,
            total_results = 1
        )

        // need some attention  // i think may mean when getNowPlayingMovies iscalled it returns mockResponse
        coEvery {
            mockApi.getNowPlayingMovies(any())
        } returns flowOf(mockResponse)

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 20,
                placeholdersEnabled = false

            )
        )


        // then

        assertTrue(result is PagingSource.LoadResult.Page)
        assertEquals(mockMovie,(result as PagingSource.LoadResult.Page ).data.first())  // first movie in page
        assertEquals(null,result.prevKey)
        assertEquals(2,result.nextKey)

    }


    // test Another Method in the paging source when Throws Exception
    @Test
    fun loadPage_returnErrorWhenApi_throwsException() = runTest {
        // Given
        val errorResponse = Response.error<MovieResponse>(
            401,
            "{\"message\":\"Unauthorized\"}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )

        coEvery {
            mockApi.getNowPlayingMovies(any())
        } throws HttpException(errorResponse)

        // When
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        // Then
        assertTrue(result is PagingSource.LoadResult.Error)
        assertTrue((result as PagingSource.LoadResult.Error).throwable is HttpException)
        assertEquals(401, (result.throwable as HttpException).code())
    }

    @Test
    fun LoadPage_returnErrorWhenApi_throwsIOException() = runTest {

        // Given
        coEvery{
            mockApi.getNowPlayingMovies(any())
        }throws java.io.IOException("Network error")   // throw the exception

        // when
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        // then
        assertTrue(result is PagingSource.LoadResult.Error)
        assertTrue((result as PagingSource.LoadResult.Error).throwable is java.io.IOException)
        assertEquals("Network error", (result.throwable as java.io.IOException).message)

    }

}