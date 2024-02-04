package com.cineschoolproject.viewModel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cineschoolproject.BuildConfig
import com.cineschoolproject.R
import com.cineschoolproject.models.movie_model.MovieData
import com.squareup.picasso.Picasso

class SliderAdapter(private val movies: List<MovieData>) :
    RecyclerView.Adapter<SliderAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.slider, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = this.movies[position]
        holder.itemMovieTitle.text = movie.title

        Picasso.get()
            .load(BuildConfig.TMDB_MEDIA_URL + "w300" + movie.imageUrl)
            .error(R.drawable.no_image)
            .into(holder.itemImage)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.image_slider)
        val itemMovieTitle: TextView = itemView.findViewById(R.id.text_title_slider)
    }
}