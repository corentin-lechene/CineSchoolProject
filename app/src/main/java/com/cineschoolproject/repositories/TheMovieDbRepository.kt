package com.cineschoolproject.repositories

import TheMovieDbExtra
import android.util.Log
import com.cineschoolproject.BuildConfig
import com.cineschoolproject.models.movie_model.MovieData
import com.cineschoolproject.models.movie_model.dto.TheMovieDbDto
import com.cineschoolproject.models.movie_model.dto.TheMovieDbResponseDto
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

    fun getMoviesByQuery(query: String, page: Int): Flowable<List<TheMovieDbDto>> {
        return theMovieDbApiService.getMoviesByQuery(
            query,
            "fr",
            page
        ).map {
            it.results
        }
    }

    fun getMovieDetails(movieId: Int): Flowable<TheMovieDbExtra> {
        Log.d("getMovieDetails repo", movieId.toString())
        return theMovieDbApiService.getMovieDetails(
            movieId,
        )
    }

}