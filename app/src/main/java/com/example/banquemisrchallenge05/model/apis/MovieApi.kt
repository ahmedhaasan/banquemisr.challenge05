package com.example.banquemisrchallenge05.model.apis

import com.example.banquemisrchallenge05.API_KEY
import com.example.banquemisrchallenge05.model.pojos.MovieDetailsResponse
import com.example.banquemisrchallenge05.model.pojos.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): MovieResponse

    @GET("popular")
    suspend fun getPopularMovies(
        @Query("page") page: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): MovieResponse

    @GET("upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US"
    ): MovieResponse

    @GET("{movie_id}")
    suspend fun getMovieDetailsById(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieDetailsResponse
}