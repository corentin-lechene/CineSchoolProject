package com.cineschoolproject.services

import android.graphics.Movie
import com.cineschoolproject.models.movie_model.MovieData
import com.cineschoolproject.models.movie_model.dto.TheMovieDbDto
import com.cineschoolproject.models.movie_model.dto.TheMovieDbResponseDto
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbApiService {
    @GET("movie/now_playing")
    fun getPopularMovies(
        @Query("language") language: String,
        @Query("region") region: String,
        @Query("page") page: Int,
    ): Flowable<TheMovieDbResponseDto>

    @GET("search/movie")
    fun getMoviesByQuery(
        @Query("query") query: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): Flowable<TheMovieDbResponseDto>

    @GET("movie/{movieId}")
    fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("language") language: String
    ): Flowable<MovieData>

}