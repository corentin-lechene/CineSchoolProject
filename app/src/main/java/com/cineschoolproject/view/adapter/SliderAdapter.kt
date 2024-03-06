package com.cineschoolproject.viewModel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cineschoolproject.BuildConfig
import com.cineschoolproject.R
import com.cineschoolproject.models.movie_model.MovieData
import com.squareup.picasso.Picasso

import kotlin.collections.ArrayList

class SliderAdapter(
    private val movies: List<MovieData>,
    private val viewPager2: ViewPager2,
    private val showSeanceData: Boolean = false
) :
    RecyclerView.Adapter<SliderAdapter.MovieViewHolder>() {
    // Create a mutable copy of the movies list to modify
    private val mutableMovies: MutableList<MovieData> = ArrayList(movies)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.slider_movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = this.mutableMovies[position]
        holder.itemMovieTitle.text = movie.title

        /*if (showSeanceData) {
            holder.itemMovieReleaseDate.visibility = View.VISIBLE
            holder.itemMovieReleaseDate.text = movie.releasedAt
        } else {
            holder.itemMovieReleaseDate.visibility = View.GONE
        }*/

        Picasso.get()
            .load(BuildConfig.TMDB_MEDIA_URL + "w780" + movie.imageUrl)
            .error(R.drawable.no_image)
            .into(holder.itemImage)

        if (position == mutableMovies.size - 2) { // Use -2 if you want to preload
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return mutableMovies.size
    }


    private val runnable = Runnable {
        val currentSize = mutableMovies.size
        // Add all movies again to the end of the list (duplicate the list)
        mutableMovies.addAll(movies)
        // Notify the adapter for the range inserted
        notifyItemRangeInserted(currentSize, movies.size)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.image_slider)
        val itemMovieTitle: TextView = itemView.findViewById(R.id.text_title_slider)
        //WIP : add date upcoming
        //al itemMovieReleaseDate: TextView = itemView.findViewById(R.id.date_info)
    }
}