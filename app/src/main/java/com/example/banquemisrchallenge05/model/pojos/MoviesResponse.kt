package com.example.banquemisrchallenge05.model.pojos

import java.io.Serializable


/**
 *      creating a movieResponse to catch the movies
 */
data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

data class  Movie(
    val id :Int,
    val title:String,
    val release_date:String,
    val overview:String,
    val poster_path:String,
    val genre_ids:List<Int>
): Serializable