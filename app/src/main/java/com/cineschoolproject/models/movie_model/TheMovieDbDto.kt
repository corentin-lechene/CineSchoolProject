package com.cineschoolproject.models.movie_model

import com.google.gson.annotations.SerializedName

data class TheMovieDbDto(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
) {
    fun toMovieData(): MovieData {
        return MovieData(
            id = this.id,
            title = this.title,
            overview = this.overview,
            imageUrl = this.backdropPath ?: this.posterPath ?: "", // Fallback to posterPath if backdropPath is null, else empty string
            hasBackdrop = !this.backdropPath.isNullOrEmpty(), // True if backdropPath is not null/empty, false otherwise
            releasedAt = this.releaseDate,
            rating = this.voteAverage.toInt() // Convert Double voteAverage to Int
        )
    }

}