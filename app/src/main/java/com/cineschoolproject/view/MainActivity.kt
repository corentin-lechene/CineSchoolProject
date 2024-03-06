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
import com.cineschoolproject.models.movie_model.MovieSeen
import com.cineschoolproject.models.movie_model.dto.ViewMovieSeenRequest
import com.cineschoolproject.view.adapter.MovieSeenAdapter
import com.cineschoolproject.view.bottomSheet.FormViewMovieSeenBottomSheet
import com.cineschoolproject.viewModel.MovieSeenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), OnMovieSeenClickListener, ActionBottomSheet {
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
        val movieSeenAdapter = MovieSeenAdapter(moviesSeen.toMutableList(), this)
        this.movieSeenRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        this.movieSeenRecyclerView.adapter = movieSeenAdapter
    }


    private fun displaySearchPage() {
        Intent (
            this,
            MovieSearchActivity::class.java
        ).also { startActivity(it) }
    }

    override fun onMovieSeenClick(id: Int) {
        val movieSeen = this.movieSeenViewModel.fetchById(id)
        val bottomSheet = FormViewMovieSeenBottomSheet(movieSeen, this)
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    override fun updateMovie(movieSeen: MovieSeen) {
        this.movieSeenViewModel.update(movieSeen)
    }
}

interface OnMovieSeenClickListener {
    fun onMovieSeenClick(id: Int)
}

interface ActionBottomSheet {
    fun updateMovie(movieSeen: MovieSeen)
}