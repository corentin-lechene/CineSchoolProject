package com.cineschoolproject.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.cineschoolproject.BuildConfig
import com.cineschoolproject.R
import com.cineschoolproject.view.bottomSheet.FormRegisterMovieSeenBottomSheet
import com.cineschoolproject.view.bottomSheet.OnMovieSeenRegisteredListener
import com.cineschoolproject.viewModel.MovieSeenViewModel
import com.cineschoolproject.viewModel.MovieViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsActivity : AppCompatActivity(),
    OnMovieSeenRegisteredListener {
    private val movieViewModel: MovieViewModel by viewModel()
    private val movieSeenViewModel : MovieSeenViewModel by viewModel()
    private lateinit var moviePosterImageView: ImageView
    private lateinit var movieTitleTextView: TextView
    private lateinit var movieDescriptionTextView: TextView
    private lateinit var movieReleasedAtDate: TextView
    private lateinit var addMovieSeenButton: Button
    private lateinit var deleteMovieSeenButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        this.moviePosterImageView = findViewById(R.id.movie_poster)
        this.movieTitleTextView = findViewById(R.id.movie_title_tv)
        this.movieDescriptionTextView = findViewById(R.id.movie_description_tv)
        this.movieReleasedAtDate = findViewById(R.id.movie_release_date_tv)
        this.addMovieSeenButton = findViewById(R.id.add_movie_seen_bt)
        this.deleteMovieSeenButton = findViewById(R.id.delete_movie_seen_bt)

        val movieId = intent.getIntExtra("movieId", 0)
        this.deleteMovieSeenButton.setOnClickListener {
            if (movieId != 0) {
                movieSeenViewModel.deleteMovieSeenById(movieId)
                onMovieSeenDeleted()
            }
        }

        this.setMovieDetails()

        this.addMovieSeenButton.setOnClickListener {
            this.showModalFormMovieSeen()
        }
    }

    private fun showModalFormMovieSeen() {
        val movieTitle = intent.getStringExtra("movieTitle")
        val movieImageUrl = intent.getStringExtra("movieImageUrl")
        val movieId = intent.getIntExtra("movieId", 0)

        val bottomSheet = FormRegisterMovieSeenBottomSheet(this).apply {
            arguments = Bundle().apply {
                putString("movieTitle", movieTitle)
                putString("movieImageUrl", movieImageUrl)
                putInt("movieId", movieId)
            }
        }
        bottomSheet.show(supportFragmentManager, "BottomSheetDialog")
    }


    private fun setMovieDetails() {
        val movieTitle = intent.getStringExtra("movieTitle")
        val movieImageUrl = intent.getStringExtra("movieImageUrl")
        val movieId = intent.getIntExtra("movieId", 0)
        val movieOverview = intent.getStringExtra("movieOverview")
        val movieReleasedAt = intent.getStringExtra("movieReleasedAt")

        this.movieTitleTextView.text = movieTitle ?: "No title"
        this.movieDescriptionTextView.text = movieOverview ?: "No overview"
        this.movieReleasedAtDate.text = movieReleasedAt ?: "No date"

        this.checkIfMovieSeen(movieId)
        Picasso.get()
            .load(BuildConfig.TMDB_MEDIA_URL + "w300" + movieImageUrl)
            .placeholder(R.drawable.no_image)
            .into(moviePosterImageView)
    }

    private fun checkIfMovieSeen(movieId: Int) {
        val isMovieSeen = movieSeenViewModel.isMovieSeen(movieId)
        if (isMovieSeen) {
            deleteMovieSeenButton.visibility = View.VISIBLE
            addMovieSeenButton.visibility = View.GONE
        } else {
            deleteMovieSeenButton.visibility = View.GONE
        }
    }

    override fun onMovieSeenRegistered() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }

    override fun onMovieSeenDeleted() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }

}