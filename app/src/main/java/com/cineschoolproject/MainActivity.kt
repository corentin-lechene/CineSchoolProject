package com.cineschoolproject

import MovieAdapter
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cineschoolproject.di.injectModuleDependencies
import com.cineschoolproject.di.parseAndInjectConfiguration
import com.cineschoolproject.viewModel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModel()
    private lateinit var popularMovieAdapter: MovieAdapter
    private lateinit var searchMovieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        parseAndInjectConfiguration()
        injectModuleDependencies(this)

        setInputEvents()

        val popularMovieRecyclerView: RecyclerView = findViewById(R.id.popular_movie_recycler_view)
        popularMovieRecyclerView.layoutManager = LinearLayoutManager(this)

        popularMovieAdapter = MovieAdapter(emptyList())
        popularMovieRecyclerView.adapter = popularMovieAdapter;

        // get popular movies
        this.movieViewModel.getPopularMovies(1)
        this.movieViewModel.popularMovieListLiveData.observe(this@MainActivity) {
            if (it.isNotEmpty()) {
                Log.d("Size popular movies", it.size.toString())
                popularMovieAdapter = MovieAdapter(it)
                popularMovieRecyclerView.adapter = popularMovieAdapter;
            }
        }
    }

    private fun setInputEvents() {
        val titleSearchPage: TextView = findViewById(R.id.title_search_page)
        val searchBar: EditText = findViewById(R.id.search_movie_input)
        val cancelButton: TextView = findViewById(R.id.search_movie_cancel)
        val sectionTitle: TextView = findViewById(R.id.title_section)
        val popularMovieRecyclerView: RecyclerView = findViewById(R.id.popular_movie_recycler_view)
        val resultMovieRecyclerView: RecyclerView = findViewById(R.id.result_movie_recycler_view)
        popularMovieRecyclerView.layoutManager = LinearLayoutManager(this)
        resultMovieRecyclerView.layoutManager = LinearLayoutManager(this)

        sectionTitle.text = "Films populaires"
        searchBar.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus) {
                cancelButton.visibility = View.VISIBLE
                titleSearchPage.visibility = View.GONE
                sectionTitle.text = "Résultats de la recherche"
                popularMovieRecyclerView.visibility = View.GONE
                resultMovieRecyclerView.visibility = View.VISIBLE
            } else {
                cancelButton.visibility = View.GONE
                titleSearchPage.visibility = View.VISIBLE
                sectionTitle.text = "Films populaires"
                popularMovieRecyclerView.visibility = View.VISIBLE
                resultMovieRecyclerView.visibility = View.GONE
            }
        }

        cancelButton.setOnClickListener {
            cancelButton.visibility = View.GONE
            titleSearchPage.visibility = View.VISIBLE
            popularMovieRecyclerView.visibility = View.VISIBLE
            resultMovieRecyclerView.visibility = View.GONE

            searchMovieAdapter = MovieAdapter(emptyList())
            resultMovieRecyclerView.adapter = searchMovieAdapter

            searchBar.clearFocus()
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(searchBar.windowToken, 0)
        }

        searchBar.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val newText = s.toString()
                onSearch(newText)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    fun onSearch(input: String) {
        val resultMovieRecyclerView: RecyclerView = findViewById(R.id.result_movie_recycler_view)
        resultMovieRecyclerView.layoutManager = LinearLayoutManager(this)

        if(input.isEmpty()) {
            searchMovieAdapter = MovieAdapter(emptyList())
            resultMovieRecyclerView.adapter = searchMovieAdapter
            return
        }

        searchMovieAdapter = MovieAdapter(emptyList())
        resultMovieRecyclerView.adapter = searchMovieAdapter;

        // search movies
        this.movieViewModel.getMoviesByInput(input, 1)
        this.movieViewModel.searchMovieListLiveData.observe(this@MainActivity) {
            if (it.isNotEmpty()) {
                Log.d("Size popular movies", it.size.toString())
                searchMovieAdapter = MovieAdapter(it)
                resultMovieRecyclerView.adapter = searchMovieAdapter;
            }
        }
    }
}