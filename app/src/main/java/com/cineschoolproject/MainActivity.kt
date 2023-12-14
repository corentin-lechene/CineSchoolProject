package com.cineschoolproject

import MovieAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cineschoolproject.di.injectModuleDependencies
import com.cineschoolproject.di.parseAndInjectConfiguration
import com.cineschoolproject.viewModel.MovieViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModel()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        parseAndInjectConfiguration()
        injectModuleDependencies(this)

//        val imageView: ImageView = findViewById(R.id.movie_image)
//        Picasso.get()
//            .load("https://image.tmdb.org/t/p/w300/f1AQhx6ZfGhPZFTVKgxG91PhEYc.jpg")
//            .fit()
//            .into(imageView)

        val titleListSearchTextView: TextView = findViewById(R.id.title_section)

        titleListSearchTextView.text = "Films populaires"

        val recyclerView: RecyclerView = findViewById(R.id.movie_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        movieAdapter = MovieAdapter(emptyList())
        recyclerView.adapter = movieAdapter;

        this.movieViewModel.getPopularMovies(1)
        this.movieViewModel.movieListLiveData.observe(this@MainActivity) {
            if (it.isNotEmpty()) {
                Log.d("Size popular movies", it.size.toString())
                movieAdapter = MovieAdapter(it)
                recyclerView.adapter = movieAdapter;
            }
        }
    }
}