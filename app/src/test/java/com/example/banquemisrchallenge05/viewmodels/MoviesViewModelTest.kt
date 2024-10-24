package com.example.banquemisrchallenge05.viewmodels

import androidx.compose.runtime.collectAsState
import androidx.paging.PagingData
import com.example.banquemisrchallenge05.model.apistates.MovieDetailsApiState
import com.example.banquemisrchallenge05.model.pagination.MovieType
import com.example.banquemisrchallenge05.model.pojos.Genre
import com.example.banquemisrchallenge05.model.pojos.Movie
import com.example.banquemisrchallenge05.model.pojos.MovieDetailsResponse
import com.example.banquemisrchallenge05.model.repository.IRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MoviesViewModelTest {

    private val mockkRepository = mockk<IRepository>()
    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setUP() {
        viewModel = MoviesViewModel(mockkRepository)

    }

    @Test
    fun updateMovieType_updatesCurrentMovieType() {
        // Given

        val movie = Movie(
            id = 1,
            title = "Test Movie",
            release_date = "2023-09-01",
            overview = "This is a test movie",
            poster_path = "test_poster_path",
            genre_ids = listOf(1, 2, 3)
        )

        val mockkPaginationData = PagingData.from(listOf(movie))

        coEvery {
            mockkRepository.getPopularMovies(any())

        } returns flowOf(mockkPaginationData)
        // when
        viewModel.updateMovieType(MovieType.POPULAR)
        // then
        assert(viewModel.currentMovieType.value == MovieType.POPULAR)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getMovieDetailsById_updatesMovieDetailsState() = runTest {
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
            mockkRepository.getMovieDetailsById(1)
        } returns flowOf(mockMovieDetails)

        // When
        viewModel.getMovieDetailsById(1)

        // Then

        // Collect the state
        val state = viewModel.movieDetails.first()
        assert(state is MovieDetailsApiState.Success)
        if (state is MovieDetailsApiState.Success) {
            assertEquals(mockMovieDetails, state.data)
        }
    }



}