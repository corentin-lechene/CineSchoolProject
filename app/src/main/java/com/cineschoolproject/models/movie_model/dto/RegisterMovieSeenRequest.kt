package com.cineschoolproject.models.movie_model.dto

data class RegisterMovieSeenRequest (
    val dateSeen: String,
    val note: Int,
    val comment: String
)