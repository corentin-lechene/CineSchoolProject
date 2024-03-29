package com.cineschoolproject.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cineschoolproject.BuildConfig
import com.cineschoolproject.R
import com.cineschoolproject.models.movie_model.MovieData
import com.cineschoolproject.models.movie_model.dto.ViewMovieSeenRequest
import com.cineschoolproject.view.OnMovieClickListener
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MovieAdapter(private val movies: List<MovieData>,
    private val onMovieClickHandler: OnMovieClickListener
) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemMovieImage: ImageView = itemView.findViewById(R.id.item_movie_image)
        val itemMovieTitle: TextView = itemView.findViewById(R.id.item_movie_title)
        val itemMovieReleaseDate: TextView = itemView.findViewById(R.id.item_movie_release_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_movie_search, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.itemMovieTitle.text = movie.title
        holder.itemMovieReleaseDate.text = this.formatDate(movie.releasedAt)
        Picasso.get()
            .load(BuildConfig.TMDB_MEDIA_URL + "w300" + movie.imageUrl)
            .error(R.drawable.no_image)
            .into(holder.itemMovieImage)

        holder.itemView.setOnClickListener{
            onMovieClickHandler.onMovieClick(movie)
        }
    }

    private fun formatDate(date: String): String {
        if (date.isEmpty()) {
            return "Aucune date"
        }

        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val parsedDate: Date = inputFormat.parse(date) ?: return "Aucune date"

        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val currentDate = Date()

        return if (parsedDate.after(currentDate)) {
            "Sortira le ${outputFormat.format(parsedDate)}"
        } else {
            "Sorti le ${outputFormat.format(parsedDate)}"
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}
