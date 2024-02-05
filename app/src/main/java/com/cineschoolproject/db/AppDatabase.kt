package com.cineschoolproject.db

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import com.cineschoolproject.db.daos.MovieSeenDao
import com.cineschoolproject.db.entities.MovieSeenEntity

@Database(
    entities = [MovieSeenEntity::class],
    version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieSeenDao(): MovieSeenDao

    private fun isDatabasePopulated(): Boolean {
        return  movieSeenDao().fetchAll().isNotEmpty()
    }
    fun prepopulateDatabase() {
        if(!isDatabasePopulated()) {
            Log.d("Database", "Initialisation...")

            val movieSeen = MovieSeenEntity(1, "Avengers 1", "https://image.tmdb.org/t/p/w300/9BBTo63ANSmhC4e6r62OJFuK2GL.jpg",  "01/01/2020", 3, "Cool")
            val movieSeen2 = MovieSeenEntity(2, "Avengers Infinity War", "https://image.tmdb.org/t/p/w300/mDfJG3LC3Dqb67AZ52x3Z0jU0uB.jpg",  "01/01/2020", 3, "Cool")
            movieSeenDao().addNewMovieSeen(movieSeen)
            movieSeenDao().addNewMovieSeen(movieSeen2)
        }

    }

}