package com.cineschoolproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.cineschoolproject.di.injectModuleDependencies
import com.cineschoolproject.di.parseAndInjectConfiguration
import com.cineschoolproject.models.movie_model.MovieData
import com.cineschoolproject.models.movie_model.TheMovieDbDto
import com.cineschoolproject.viewModel.MovieViewModel
import com.cineschoolproject.viewModel.adapter.SliderAdapter
import com.cineschoolproject.viewModel.adapter.MovieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs
import android.os.Handler


class HomeScreen : AppCompatActivity() {

    private var currentView: SearchPageView = SearchPageView.POPULAR
    private val movieViewModel: MovieViewModel by viewModel()

    private lateinit var adapter: SliderAdapter

    private lateinit var viewPager2: ViewPager2

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        parseAndInjectConfiguration()
        injectModuleDependencies(this)

        init()
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
        adapter.notifyDataSetChanged()
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

    private fun init() {

        handler = Handler(Looper.myLooper()!!)
        this.viewPager2 = findViewById(R.id.view_pager2)
        this.adapter = SliderAdapter(listOf(), this.viewPager2)
        this.viewPager2.adapter = adapter

        this.movieViewModel.upcomingMovies.observe(this@HomeScreen) {
            this.setUpPopularMovies(it, viewPager2)
        }
        this.movieViewModel.getUpcomingMovies(1)

        // Configure other properties as needed
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setUpPopularMovies(movies: List<MovieData>, viewPager: ViewPager2) {
        adapter = SliderAdapter(movies, this.viewPager2)
        viewPager.adapter = adapter
    }

    fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }
}


