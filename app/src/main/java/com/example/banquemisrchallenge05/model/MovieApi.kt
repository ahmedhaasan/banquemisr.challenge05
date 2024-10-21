package com.example.banquemisrchallenge05.model

import com.example.banquemisrchallenge05.model.pojos.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    interface MovieApiService {
        @GET("now_playing")
        suspend fun getNowPlayingMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String = "en-US",
            @Query("page") page: Int
        ): MovieResponse
    }


}