package com.cineschoolproject.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cineschoolproject.models.movie_model.MovieSeen

@Entity(tableName = "movie_seen")
data class MovieSeenEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name="movie_title") val movieTitle: String,
    @ColumnInfo(name="movie_image_url") val movieImageUrl: String,
    @ColumnInfo(name="date_seen") val dateSeen: String,
    @ColumnInfo(name="note") val note: Int,
    @ColumnInfo(name="comment") val comment: String
)

// Fonction de mapping
fun MovieSeen.toMovieSeenEntity(): MovieSeenEntity {
    return MovieSeenEntity(
        uid = this.id,
        movieTitle = this.title,
        movieImageUrl = this.imageUrl,
        dateSeen = this.registerMovieSeenRequest.dateSeen,
        note = this.registerMovieSeenRequest.note,
        comment = this.registerMovieSeenRequest.comment,
    )
}
