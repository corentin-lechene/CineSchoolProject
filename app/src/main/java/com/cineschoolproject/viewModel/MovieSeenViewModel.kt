package com.cineschoolproject.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cineschoolproject.models.movie_model.MovieSeen
import com.cineschoolproject.models.movie_model.dto.RegisterMovieSeenRequest
import com.cineschoolproject.models.movie_model.dto.ViewMovieSeenRequest
import com.cineschoolproject.models.movie_model.dto.toViewMovieSeenRequest
import com.cineschoolproject.repositories.MovieSeenRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject

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
        val movieSeen = MovieSeen(idMovie, title, imageUrl, registerMovieSeenRequest)
        this.movieSeenRepository.addMovieSeen(movieSeen)
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }

}