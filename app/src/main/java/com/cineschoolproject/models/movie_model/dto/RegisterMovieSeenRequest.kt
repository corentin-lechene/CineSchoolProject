package com.cineschoolproject.models.movie_model.dto

import com.cineschoolproject.models.movie_model.MovieSeen

data class RegisterMovieSeenRequest (
    val dateSeen: String,
    val note: Int,
    val comment: String
)

/*
    * To map MovieSeen to ViewMovieSeenRequest (dto)
    * */
fun MovieSeen.toViewMovieSeenRequest() = ViewMovieSeenRequest(
    title = title,
    imageUrl = imageUrl
)