package com.example.banquemisrchallenge05.model.pojos

data class MovieDetailsResponse(
    val id: Int,
    val original_title: String,
    val release_date: String,
    val poster_path: String,
    val overview: String,
    val logo_path :String,
    val vote_average :Double,
    val genres: List<Genre>,
    val runtime: Int
)

data class Genre(
    val id: Int,
    val name: String
)
