package com.cineschoolproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cineschoolproject.di.injectModuleDependencies
import com.cineschoolproject.di.parseAndInjectConfiguration
import com.cineschoolproject.models.movie_model.dto.ViewMovieSeenRequest
import com.cineschoolproject.viewModel.MovieSeenViewModel
import com.cineschoolproject.viewModel.adapter.MovieSeenAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

//TODO : connect search page
class MainActivity : AppCompatActivity() {
    private val movieSeenViewModel : MovieSeenViewModel by viewModel()
    private lateinit var  movieSeenRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        parseAndInjectConfiguration()
        injectModuleDependencies(this)

        this.movieSeenRecyclerView = findViewById(R.id.movie_seen_rv)

        this.movieSeenViewModel.moviesSeen.observe(this@MainActivity) {
            this.setImageSliderMoviesSeen(it)
        }
        this.movieSeenViewModel.getMoviesSeen()

    }

    private fun setImageSliderMoviesSeen(moviesSeen: List<ViewMovieSeenRequest>) {
        val movieSeenAdapter = MovieSeenAdapter(moviesSeen)
        this.movieSeenRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        this.movieSeenRecyclerView.adapter = movieSeenAdapter

//        val imageList = ArrayList<SlideModel>()
//        for(movieSeen in moviesSeen) {
//            imageList.add(SlideModel(movieSeen.imageUrl, movieSeen.title))
//        }
//        this.imageSlider.setImageList(imageList)
    }

}