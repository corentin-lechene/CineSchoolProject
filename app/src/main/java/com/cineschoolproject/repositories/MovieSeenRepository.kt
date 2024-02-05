package com.cineschoolproject.repositories

import com.cineschoolproject.db.daos.MovieSeenDao
import com.cineschoolproject.db.entities.toMovieSeenEntity
import com.cineschoolproject.models.movie_model.MovieSeen
import com.cineschoolproject.models.movie_model.toMovieSeen
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable

class MovieSeenRepository (private val movieSeenDao: MovieSeenDao) {

    fun getMoviesSeen(): Flowable<List<MovieSeen>> {
        val movies = movieSeenDao.fetchAll()

        return Flowable.create({ emitter ->
            emitter.onNext(movies.map { it.toMovieSeen() })

            emitter.onComplete()
        }, BackpressureStrategy.BUFFER)
    }

    fun addMovieSeen(movieSeen: MovieSeen) {
        movieSeenDao.addNewMovieSeen(movieSeen.toMovieSeenEntity());
//        this.moviesSeen.add(movieSeen)
    }

//    fun isMovieSeen(movieId: Int): Boolean {
//        val movies = movieSeenDao.fetchAll()
//
//        for (movie in movies) {
//            if (movie.uid == movieId) {
//                return true
//            }
//        }
//        return false
//    }
//
//    fun deleteMovieSeen(movieId: Int) {
//        val movies = movieSeenDao.fetchAll()
//
//        movies.removeIf { it.id == movieId }
//    }
}