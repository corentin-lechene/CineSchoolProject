package com.cineschoolproject.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cineschoolproject.exceptions.FORMAT_DATE
import com.cineschoolproject.exceptions.MIN_YEAR
import com.cineschoolproject.exceptions.MAX_THRESHOLD_NOTE
import com.cineschoolproject.exceptions.MIN_THRESHOLD_NOTE
import com.cineschoolproject.exceptions.MovieSeenExceptionMessages
import com.cineschoolproject.models.movie_model.MovieSeen
import com.cineschoolproject.models.movie_model.dto.RegisterMovieSeenRequest
import com.cineschoolproject.models.movie_model.dto.ViewMovieSeenRequest
import com.cineschoolproject.models.movie_model.dto.toViewMovieSeenRequest
import com.cineschoolproject.repositories.MovieSeenRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale




class MovieSeenViewModel(
    private val movieSeenRepository: MovieSeenRepository
) : ViewModel(){
    private val disposeBag = CompositeDisposable()

    private val _moviesSeen: BehaviorSubject<List<ViewMovieSeenRequest>> = BehaviorSubject.createDefault(listOf())

    val moviesSeen: MutableLiveData<List<ViewMovieSeenRequest>> = MutableLiveData()

    init {
        _moviesSeen.subscribe {moviesSeen.postValue(it)}.addTo(disposeBag)
    }

    fun getMoviesSeen() {
        this.movieSeenRepository.getMoviesSeen().subscribe(
            { it ->
                this._moviesSeen.onNext(it.map { it.toViewMovieSeenRequest() })
            },
            {
                error ->
                Log.d("Get MoviesSeen", error.message.toString())
            }
        ).addTo(disposeBag)
    }

    fun addMovieSeen(registerMovieSeenRequest: RegisterMovieSeenRequest, title: String,
                     imageUrl: String, idMovie: Int) {

        checkRegisterMovieSeenRequest(registerMovieSeenRequest)
        val movieSeen = MovieSeen(idMovie, title, imageUrl, registerMovieSeenRequest)
        this.movieSeenRepository.addMovieSeen(movieSeen)
    }

    fun fetchById(id: Int): MovieSeen {
        return this.movieSeenRepository.fetchById(id)
    }

    fun update(movieSeen: MovieSeen) {
        checkRegisterMovieSeenRequest(movieSeen.registerMovieSeenRequest)

        return this.movieSeenRepository.update(movieSeen)
    }

    fun deleteMovieSeenById(movieId: Int) {
        movieSeenRepository.deleteMovieSeenById(movieId)
        getMoviesSeen()
    }

    fun isMovieSeen(movieId: Int): Boolean {
        return movieSeenRepository.isMovieSeen(movieId)
    }

    private fun checkRegisterMovieSeenRequest(registerMovieSeenRequest: RegisterMovieSeenRequest) {

        // check note
        if(registerMovieSeenRequest.note < MIN_THRESHOLD_NOTE || registerMovieSeenRequest.note > MAX_THRESHOLD_NOTE) {
            throw RuntimeException(MovieSeenExceptionMessages.INVALID_THRESHOLD_NOTE.message)
        }

        // check date
        val dateFormat = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
        val parsedDate: Date

        try {
            parsedDate = dateFormat.parse(registerMovieSeenRequest.dateSeen)!!
        } catch (e: Exception) {
            e.message?.let { Log.d("error", it) }
            throw RuntimeException(MovieSeenExceptionMessages.INVALID_FORMAT_DATE.message)
        }

        val calendar = Calendar.getInstance()
        calendar.time = parsedDate

        val year = calendar.get(Calendar.YEAR)

        if (year < MIN_YEAR || year > Calendar.getInstance().get(Calendar.YEAR)) {
            throw RuntimeException(MovieSeenExceptionMessages.INVALID_DATE_RANGE.message)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }

}