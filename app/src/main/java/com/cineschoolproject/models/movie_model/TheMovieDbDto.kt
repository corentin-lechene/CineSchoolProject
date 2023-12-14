package com.cineschoolproject.models.movie_model

data class TheMovieDbDto(
    val adult: Boolean,
    val backdrop_path: String,
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)