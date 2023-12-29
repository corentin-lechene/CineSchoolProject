package com.cineschoolproject.models.movie_model

import com.cineschoolproject.models.movie_model.dto.RegisterMovieSeenRequest

class MovieSeen (
    val id: Int,
    val title: String,
    val imageUrl: String,
    val registerMovieSeenRequest: RegisterMovieSeenRequest
)

