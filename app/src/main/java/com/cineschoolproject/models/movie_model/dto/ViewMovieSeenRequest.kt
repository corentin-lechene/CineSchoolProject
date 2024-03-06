package com.cineschoolproject.models.movie_model.dto

import com.cineschoolproject.models.movie_model.MovieSeen

data class ViewMovieSeenRequest (
    val id: Int,
    val title: String,
    val imageUrl: String
)

/*
    * To map MovieSeen to ViewMovieSeenRequest (dto)
    * */
fun MovieSeen.toViewMovieSeenRequest() = ViewMovieSeenRequest(
    id = id,
    title = title,
    imageUrl = imageUrl,
)