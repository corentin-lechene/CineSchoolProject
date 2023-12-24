package com.cineschoolproject.repositories

import com.cineschoolproject.models.movie_model.MovieSeen
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable

//TODO : implement stockage
class MovieSeenRepository {
    private val moviesSeen: MutableList<MovieSeen> = mutableListOf()

    init {
        val movieSeen = MovieSeen(1, "Avengers 1", "https://image.tmdb.org/t/p/w300/9BBTo63ANSmhC4e6r62OJFuK2GL.jpg", 5)
        val movieSeen2 = MovieSeen(2, "Avengers Infinity War", "https://image.tmdb.org/t/p/w300/mDfJG3LC3Dqb67AZ52x3Z0jU0uB.jpg", 5)
        this.moviesSeen.add(movieSeen)
        this.moviesSeen.add(movieSeen2)
    }

    fun getMoviesSeen(): Flowable<List<MovieSeen>> {
        return Flowable.create({ emitter ->
            // Simuler une opération asynchrone (par exemple, récupérer les films de la base de données)
            // Émettre la liste des films
            emitter.onNext(this.moviesSeen)

            // Indiquer que le flux est terminé
            emitter.onComplete()
        }, BackpressureStrategy.BUFFER)
    }
}