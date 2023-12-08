package com.cineschoolproject.models.movie_model

class MovieData(
    val id: Int,
    val title: String,
    val overview: String,
    val imageUrl: String?,
    val hasBackdrop: Boolean,
    val releasedAt: Int,
    val rating: Int,
) {

}
