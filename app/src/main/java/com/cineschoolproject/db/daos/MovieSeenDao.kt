package com.cineschoolproject.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cineschoolproject.db.entities.MovieSeenEntity

@Dao
interface MovieSeenDao {
    @Query("SELECT * FROM movie_seen")
    fun fetchAll(): List<MovieSeenEntity>

    @Insert
    fun addNewMovieSeen(movieSeenEntity: MovieSeenEntity)

    @Delete
    fun deleteMovieSeen(movieSeenEntity: MovieSeenEntity)

}