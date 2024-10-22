package com.example.banquemisrchallenge05.model.apis

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object MovieRetrofitHelper {

    private val BaseUrl = "https://api.themoviedb.org/3/movie/"
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BaseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    val service : MovieApi by lazy {
        retrofitInstance.create(MovieApi::class.java)
    }
}