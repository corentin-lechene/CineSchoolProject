package com.example.cineschoolproject.model.movie_model

data class FakeTheMovieDbResponseDto(
    val page: Int,
    val results: List<FakeTheMovieDbDto>,
    val totalPages: Int,
    val totalResults: Int
)