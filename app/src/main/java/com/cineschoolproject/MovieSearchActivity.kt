package com.cineschoolproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cineschoolproject.di.injectModuleDependencies
import com.cineschoolproject.di.parseAndInjectConfiguration
import com.cineschoolproject.models.movie_model.dto.TheMovieDbDto
import com.cineschoolproject.viewModel.MovieViewModel
import com.cineschoolproject.viewModel.adapter.MovieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

enum class SearchPageView {
    POPULAR,
    SEARCH
}
class MovieSearchActivity : AppCompatActivity() {
    private var currentView: SearchPageView = SearchPageView.POPULAR

    private val movieViewModel: MovieViewModel by viewModel()
    private lateinit var popularMoviesRecyclerView: RecyclerView
    private lateinit var resultMoviesRecyclerView: RecyclerView
    private lateinit var searchBar: EditText
    private lateinit var searchBarCancelButton: TextView
    private lateinit var listHeaderSection: TextView
    private lateinit var loadingProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        parseAndInjectConfiguration()
        injectModuleDependencies(this)

        this.popularMoviesRecyclerView = findViewById(R.id.popular_movies_recycler_view)
        this.resultMoviesRecyclerView = findViewById(R.id.result_movies_recycler_view)
        this.searchBar = findViewById(R.id.search_movie_input)
        this.searchBarCancelButton = findViewById(R.id.search_movie_input_cancel)
        this.listHeaderSection = findViewById(R.id.list_header_title)
        this.loadingProgressBar = findViewById(R.id.loading_search_page)

        this.movieViewModel.popularMovies.observe(this@MovieSearchActivity) {
            this.setUpPopularMovies(it)
        }
        this.movieViewModel.resultMovies.observe(this@MovieSearchActivity) {
            this.setUpResultMovies(it)
            loadingProgressBar.visibility = View.GONE
        }

        this.movieViewModel.getPopularMovies(1)

        this.setSearchBar()
        this.setView(SearchPageView.POPULAR)
    }

    private fun setSearchBar() {
        this.searchBar.doBeforeTextChanged { text, _, _, _ ->
            if(text.isNullOrBlank()) {
                loadingProgressBar.visibility = View.GONE
            }
        }
        this.searchBar.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrBlank()) {
                loadingProgressBar.visibility = View.VISIBLE
                this.movieViewModel.getMoviesByQuery(text.toString(), 1)
            }
        }
        this.searchBar.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus) {
                this.setView(SearchPageView.SEARCH)
            } else {
                this.setView(SearchPageView.POPULAR)
            }
        }
        this.searchBarCancelButton.setOnClickListener {
            this.setView(SearchPageView.POPULAR)
        }
    }

    private fun setView(newView: SearchPageView) {
        this.currentView = newView

        if(this.currentView == SearchPageView.POPULAR) {
            this.listHeaderSection.text = "Films Populaires"
            this.popularMoviesRecyclerView.visibility = View.VISIBLE
            this.resultMoviesRecyclerView.visibility = View.GONE
            this.searchBarCancelButton.visibility = View.GONE
            this.searchBar.clearFocus()
            this.searchBar.text = null
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(searchBar.windowToken, 0)
            loadingProgressBar.visibility = View.GONE
        } else if(this.currentView == SearchPageView.SEARCH) {
            this.listHeaderSection.text = "Résultats de la recherche"
            this.resultMoviesRecyclerView.visibility = View.VISIBLE
            this.popularMoviesRecyclerView.visibility = View.GONE
            this.searchBarCancelButton.visibility = View.VISIBLE
            this.resultMoviesRecyclerView.adapter = MovieAdapter(listOf())
        }
    }

    private fun setUpPopularMovies(movies: List<TheMovieDbDto>) {
        val popularMovieAdapter = MovieAdapter(movies)
        this.popularMoviesRecyclerView.layoutManager = LinearLayoutManager(this)
        this.popularMoviesRecyclerView.adapter = popularMovieAdapter
    }

    private fun setUpResultMovies(movies: List<TheMovieDbDto>) {
        val resultMoviesAdapter = MovieAdapter(movies)
        this.resultMoviesRecyclerView.layoutManager = LinearLayoutManager(this)
        this.resultMoviesRecyclerView.adapter = resultMoviesAdapter
    }
}