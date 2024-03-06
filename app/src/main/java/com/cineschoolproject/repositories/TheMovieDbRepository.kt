package com.cineschoolproject.repositories

import com.cineschoolproject.models.movie_model.MovieData
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

    fun getUpcomingMovies(page: Int): Flowable<List<TheMovieDbDto>> {
        return theMovieDbApiService.getUpcomingMovies(
            "fr",
            "fr",
            page
        ).map {
            it.results
        }
    }

    fun getMoviesByQuery(query: String, page: Int): Flowable<List<TheMovieDbDto>> {
        return theMovieDbApiService.getMoviesByQuery(
            query,
            "fr",
            page
        ).map {
            it.results
        }
    }
}