package com.cineschoolproject.models.movie_model.dto

data class TheMovieDbResponseDto(
    val page: Int,
    val results: List<TheMovieDbDto>,
    val totalPages: Int,
    val totalResults: Int
)