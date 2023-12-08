package com.cineschoolproject.models.movie_model

data class TheMovieDbResponseDto(
    val page: Int,
    val results: List<TheMovieDbDto>,
    val totalPages: Int,
    val totalResults: Int
)