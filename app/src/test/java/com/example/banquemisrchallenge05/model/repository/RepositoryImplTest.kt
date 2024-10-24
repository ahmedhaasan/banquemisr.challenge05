package com.example.banquemisrchallenge05.model.repository

import androidx.paging.PagingData
import com.example.banquemisrchallenge05.model.pojos.Genre
import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.model.pojos.MovieDetailsResponse
import com.example.banquemisrchallenge05.model.pojos.MovieResponse
import com.example.banquemisrchallenge05.model.remote.IRemoteDataSource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class RepositoryImplTest{

    private val remote = mockk<IRemoteDataSource>()
    private lateinit var repository: RepositoryImpl

    @Before
    fun setUp(){
        repository = RepositoryImpl(remote)
    }

    // first function to Test
    @Test
    fun getNowPlayingMovies_returnsFlowOfPagingData() = runTest {

        // Givien
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
            total_pages = 10,
            total_results = 100
        )

        coEvery {
            remote.getNowPlayingMovies(any())
            } returns flowOf(mockResponse)

        // when
        val result = repository.getNowPlayingMovies(1)

        // then
        assertNotNull(result)
        assertTrue(result is Flow<PagingData<Movie>>)
    }

    // test get Details function
    @Test
    fun getMovieDetailsById_returnsMovieDetails() = runTest {
        // Given
        val genre1 = Genre(1, "Drama")
        val genre2 = Genre(2, "Action")
        val mockMovieDetails = MovieDetailsResponse(
            id = 1,
            original_title = "Test Movie",
            release_date = "2023-09-01",
            poster_path = "test_poster_path",
            overview = "This is a test movie overview.",
            logo_path = "test_logo_path",
            vote_average = 7.5,
            genres = listOf(genre1, genre2),
            runtime = 120
        )


        coEvery {
            remote.getMovieDetailsById(1)
        } returns flowOf(mockMovieDetails)

        // when
        val result = repository.getMovieDetailsById(1)
        // then
        assertNotNull(result)
        assertEquals(mockMovieDetails, result.first())

    }
}