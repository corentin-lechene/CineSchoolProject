package com.cineschoolproject.models.movie_model

import com.cineschoolproject.db.entities.MovieSeenEntity
import com.cineschoolproject.models.movie_model.dto.RegisterMovieSeenRequest
import com.cineschoolproject.models.movie_model.dto.ViewMovieSeenRequest

class MovieSeen (
    val id: Int,
    val title: String,
    val imageUrl: String,
    val registerMovieSeenRequest: RegisterMovieSeenRequest
)

// Fonction de mapping
fun MovieSeenEntity.toMovieSeen(): MovieSeen {
    return MovieSeen(
        id = this.uid,
        title = this.movieTitle,
        imageUrl = this.movieImageUrl,
        registerMovieSeenRequest = RegisterMovieSeenRequest(this.dateSeen, this.note, this.comment)
    )
}