package com.cineschoolproject.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.cineschoolproject.R
import com.cineschoolproject.di.injectModuleDependencies
import com.cineschoolproject.di.parseAndInjectConfiguration
import com.cineschoolproject.models.movie_model.MovieData
import com.cineschoolproject.models.movie_model.MovieSeen
import com.cineschoolproject.models.movie_model.dto.ViewMovieSeenRequest
import com.cineschoolproject.view.adapter.MovieSeenAdapter
import com.cineschoolproject.view.bottomSheet.FormViewMovieSeenBottomSheet
import com.cineschoolproject.viewModel.MovieSeenViewModel
import com.cineschoolproject.viewModel.MovieViewModel
import com.cineschoolproject.viewModel.adapter.SliderAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class MainActivity : AppCompatActivity(), OnMovieSeenClickListener, ActionBottomSheet {
    private val movieSeenViewModel: MovieSeenViewModel by viewModel()
    private lateinit var movieSeenRecyclerView: RecyclerView
    private lateinit var searchButton: ImageView
    private val movieViewModel: MovieViewModel by viewModel()
    private lateinit var adapter: SliderAdapter
    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler

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


        initUpcomingMoviesViewPager()
        setUpTransformer()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 2000)
    }


    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager2.setPageTransformer(transformer)
    }


    private fun initUpcomingMoviesViewPager() {

        handler = Handler(Looper.myLooper()!!)
        this.viewPager2 = findViewById(R.id.view_pager2)
        this.adapter = SliderAdapter(listOf(), this.viewPager2)
        this.viewPager2.adapter = adapter

        this.movieViewModel.upcomingMovies.observe(this@MainActivity) {
            this.setupUpcomingMovies(it, viewPager2)
        }
        this.movieViewModel.getUpcomingMovies(1)

        // Configure other properties as needed
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setImageSliderMoviesSeen(moviesSeen: List<ViewMovieSeenRequest>) {
        val movieSeenAdapter = MovieSeenAdapter(moviesSeen.toMutableList(), this)
        this.movieSeenRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        this.movieSeenRecyclerView.adapter = movieSeenAdapter
    }

    private fun setupUpcomingMovies(movies: List<MovieData>, viewPager: ViewPager2) {
        adapter = SliderAdapter(movies, this.viewPager2)
        viewPager.adapter = adapter
    }

    private fun displaySearchPage() {
        Intent(
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