package com.cineschoolproject.viewModel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cineschoolproject.R
import com.cineschoolproject.models.movie_model.dto.ViewMovieSeenRequest
import com.squareup.picasso.Picasso

class MovieSeenAdapter(private val moviesSeen: List<ViewMovieSeenRequest>) :
    RecyclerView.Adapter<MovieSeenAdapter.MovieSeenHolder>() {

    class MovieSeenHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieSeenImageView: ImageView = itemView.findViewById(R.id.movie_seen_image_iw)
        val movieSeenTitleTextView: TextView = itemView.findViewById(R.id.movie_seen_title_tv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSeenHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_seen_card, parent, false)
        return MovieSeenHolder(view)
    }

    override fun onBindViewHolder(holder: MovieSeenHolder, position: Int) {
        val movieSeen = moviesSeen[position]
        holder.movieSeenTitleTextView.text = movieSeen.title

        Picasso.get().load(movieSeen.imageUrl)
            .error(R.drawable.no_image)
            .into(holder.movieSeenImageView)

    }


    override fun getItemCount(): Int {
        return moviesSeen.size
    }
}