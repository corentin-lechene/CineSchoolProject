package com.cineschoolproject.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cineschoolproject.R
import com.cineschoolproject.di.injectModuleDependencies
import com.cineschoolproject.di.parseAndInjectConfiguration
import com.cineschoolproject.models.movie_model.dto.ViewMovieSeenRequest
import com.cineschoolproject.viewModel.MovieSeenViewModel
import com.cineschoolproject.view.adapter.MovieSeenAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val movieSeenViewModel : MovieSeenViewModel by viewModel()
    private lateinit var  movieSeenRecyclerView: RecyclerView
    private lateinit var  searchButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        parseAndInjectConfiguration()
        injectModuleDependencies(this)

        this.movieSeenRecyclerView = findViewById(R.id.movie_seen_rv)
        this.searchButton = findViewById(R.id.search_button_iw)

        this.searchButton.setOnClickListener {
            this.displaySearchPage()
        }

        this.movieSeenViewModel.moviesSeen.observe(this@MainActivity) {
            this.setImageSliderMoviesSeen(it)
        }
        this.movieSeenViewModel.getMoviesSeen()

    }

    private fun setImageSliderMoviesSeen(moviesSeen: List<ViewMovieSeenRequest>) {
        val movieSeenAdapter = MovieSeenAdapter(moviesSeen)
        this.movieSeenRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        this.movieSeenRecyclerView.adapter = movieSeenAdapter
    }

    private fun displaySearchPage() {
        Intent (
            this,
            MovieSearchActivity::class.java
        ).also { startActivity(it) }
    }

}