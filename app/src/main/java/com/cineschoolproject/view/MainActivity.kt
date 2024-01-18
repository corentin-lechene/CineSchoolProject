package com.cineschoolproject.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cineschoolproject.R
import com.cineschoolproject.di.injectModuleDependencies
import com.cineschoolproject.di.parseAndInjectConfiguration
import com.cineschoolproject.models.movie_model.MovieData
import com.cineschoolproject.models.movie_model.dto.ViewMovieSeenRequest
import com.cineschoolproject.view.adapter.MovieSeenAdapter
import com.cineschoolproject.view.bottomSheet.FormRegisterMovieSeenBottomSheet
import com.cineschoolproject.viewModel.MovieSeenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), BottomSheetListener, OnMovieClickListener {
    private val movieSeenViewModel : MovieSeenViewModel by viewModel()
    private lateinit var  movieSeenRecyclerView: RecyclerView
    private lateinit var  searchButton: ImageView

//    // TODO: move in movie's detail page
//    private lateinit var  addMovieSeenButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        parseAndInjectConfiguration()
        injectModuleDependencies(this)

        this.movieSeenRecyclerView = findViewById(R.id.movie_seen_rv)
        this.searchButton = findViewById(R.id.search_button_iw)

//        // TODO: move in movie's detail page
//        this.addMovieSeenButton = findViewById(R.id.add_movie_seen_bt)


        this.searchButton.setOnClickListener {
            this.displaySearchPage()
        }

        this.movieSeenViewModel.moviesSeen.observe(this@MainActivity) {
            this.setImageSliderMoviesSeen(it)
        }
        this.movieSeenViewModel.getMoviesSeen()

//        // TODO: move in movie's detail page
//        this.addMovieSeenButton.setOnClickListener {
//            this.showModalFormMovieSeen()
//        }

    }

    private fun setImageSliderMoviesSeen(moviesSeen: List<ViewMovieSeenRequest>) {
        val movieSeenAdapter = MovieSeenAdapter(moviesSeen)
        this.movieSeenRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        this.movieSeenRecyclerView.adapter = movieSeenAdapter
    }

    // TODO: move in movie's detail page
    /*
    * This function opens show Form Register Movie Seen BottomSheet
    * */
    private fun showModalFormMovieSeen() {
        val bottomSheet = FormRegisterMovieSeenBottomSheet(this)
        bottomSheet.show(supportFragmentManager, "BottomSheetDialog")
    }


    private fun displaySearchPage() {
        Intent (
            this,
            MovieSearchActivity::class.java
        ).also { startActivity(it) }
    }

    override fun onBottomSheetDismissed() {
        this.movieSeenViewModel.getMoviesSeen()
    }

    override fun onMovieClick(movieData: MovieData) {
        Intent(
            this,
            MovieDetailsActivity::class.java
        ).also {
            it.putExtra("movieId", movieData.id)
            it.putExtra("movieImageUrl", movieData.imageUrl)
            it.putExtra("movieTitle", movieData.title)
            it.putExtra("movieOverview", movieData.overview)
            it.putExtra("movieReleasedAt", movieData.releasedAt)
            startActivity(it)
        }
    }
}

interface BottomSheetListener {
    fun onBottomSheetDismissed()
}