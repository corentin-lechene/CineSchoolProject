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
    private lateinit var movieRv: RecyclerView

    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPager3: ViewPager2

    private lateinit var handler: Handler
    private lateinit var imageList: ArrayList<TheMovieDbDto>


    private lateinit var popularMoviesRecyclerView: RecyclerView
    private lateinit var popularMoviesAdapter: MovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        parseAndInjectConfiguration()
        injectModuleDependencies(this)

        init()
        //Uncomment
        setUpTransformer()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }
        })


        viewPager3.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }
        })
    }

    // Uncomment
    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 2000)
    }


    private val runnable = Runnable {
        //imageList.addAll(imageList)
        //adapter.notifyDataSetChanged()
        viewPager2.currentItem = viewPager2.currentItem + 1
        viewPager3.currentItem = viewPager3.currentItem + 1
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager2.setPageTransformer(transformer)
        viewPager3.setPageTransformer(transformer)
    }

    private fun init() {

        handler = Handler(Looper.myLooper()!!)

        // view pager 2 ; popular movies
        this.viewPager2 = findViewById(R.id.view_pager2)
        this.adapter = SliderAdapter(listOf(), this.viewPager2)
        this.viewPager2.adapter = adapter

        this.movieViewModel.popularMovies.observe(this@HomeScreen) {
            this.setUpPopularMovies(it, viewPager2)
        }
        this.movieViewModel.getPopularMovies(1)

        // view pager 2 ; seances
        viewPager3 = findViewById(R.id.view_pager2_2)
        this.viewPager3.adapter = adapter

        this.movieViewModel.popularMovies.observe(this@HomeScreen) {
            this.setUpPopularMovies(it, viewPager3)
        }
        this.movieViewModel.getPopularMovies(1)


        //! val adapterForViewPager3 = SliderAdapter(imageList, viewPager3) // Adjust with correct data
        //!  viewPager3.adapter = adapterForViewPager3

// Assuming you have a different dataset or adapter for viewPager3
// You can initialize and set it here similarly

// Add ViewPager2 instances to their respective containers
        //findViewById<FrameLayout>(R.id.viewPagerContainer1).addView(viewPager2)
        // findViewById<FrameLayout>(R.id.viewPagerContainer2).addView(viewPager3)

// Configure other properties as needed
        viewPager2.offscreenPageLimit = 3
         viewPager2.clipToPadding = false
         viewPager2.clipChildren = false
         viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        //viewPager3.offscreenPageLimit = 3
        //viewPager3.clipToPadding = false
        //viewPager3.clipChildren = false
        //viewPager3.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setUpPopularMovies(movies: List<MovieData>, viewPager: ViewPager2) {
        adapter = SliderAdapter(movies, this.viewPager2)
        viewPager.adapter = adapter
    }

    fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }
}


