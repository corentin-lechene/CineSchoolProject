package com.cineschoolproject.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cineschoolproject.db.entities.MovieSeenEntity

@Dao
interface MovieSeenDao {
    @Query("SELECT * FROM movie_seen")
    fun fetchAll(): List<MovieSeenEntity>

    @Insert
    fun addNewMovieSeen(movieSeenEntity: MovieSeenEntity)

    @Update
    fun update(movieSeenEntity: MovieSeenEntity)

    @Query("DELETE FROM movie_seen WHERE uid = :movieId")
    fun deleteMovieSeenById(movieId: Int)

    @Query("SELECT COUNT(*) > 0 FROM movie_seen WHERE uid = :movieId")
    fun isMovieSeen(movieId: Int): Boolean

    @Query("SELECT * FROM movie_seen WHERE uid = :movieId")
    fun fetchById(movieId: Int): MovieSeenEntity
}