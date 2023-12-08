package com.cineschoolproject.repositories

import android.util.Log
import com.cineschoolproject.models.movie_model.TheMovieDbDto
import com.cineschoolproject.services.TheMovieDbApiService
import io.reactivex.rxjava3.core.Flowable

class TheMovieDbRepository (
    private val theMovieDbApiService: TheMovieDbApiService
) {
    fun getPopularMovies(page: Int): Flowable<List<TheMovieDbDto>> {
        return theMovieDbApiService.getPopularMovies(
            "fr",
            "fr",
            page
        ).map {
            it.results
        }
    }
}