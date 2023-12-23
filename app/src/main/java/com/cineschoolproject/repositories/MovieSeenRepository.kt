package com.cineschoolproject.repositories

import com.cineschoolproject.models.movie_model.MovieSeen
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable

//TODO : implement stockage
class MovieSeenRepository {
    fun getMoviesSeen(): Flowable<List<MovieSeen>> {
        return Flowable.create({ emitter ->
            // Simuler une opération asynchrone (par exemple, récupérer les films de la base de données)
            val movieSeen = MovieSeen(1, "Avengers 1", "https://image.tmdb.org/t/p/w300/9BBTo63ANSmhC4e6r62OJFuK2GL.jpg", 5)
            val movieSeen2 = MovieSeen(2, "Avengers Infinity War", "https://image.tmdb.org/t/p/w300/mDfJG3LC3Dqb67AZ52x3Z0jU0uB.jpg", 5)
            val moviesSeen: List<MovieSeen> = listOf(movieSeen, movieSeen2)

            // Émettre la liste des films
            emitter.onNext(moviesSeen)

            // Indiquer que le flux est terminé
            emitter.onComplete()
        }, BackpressureStrategy.BUFFER)
    }
}