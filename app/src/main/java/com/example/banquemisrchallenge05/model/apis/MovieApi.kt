package com.example.banquemisrchallenge05.model.apis

import com.example.banquemisrchallenge05.API_KEY
import com.example.banquemisrchallenge05.model.pojos.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
        @GET("now_playing")
        suspend fun getNowPlayingMovies(
            @Query("api_key") apiKey: String = API_KEY,
            @Query("language") language: String = "en-US",
            @Query("page") page: Int = 1
        ): MovieResponse
}