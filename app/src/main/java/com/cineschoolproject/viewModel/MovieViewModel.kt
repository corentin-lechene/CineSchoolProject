package com.cineschoolproject.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cineschoolproject.models.movie_model.dto.TheMovieDbDto
import com.cineschoolproject.repositories.TheMovieDbRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject

class MovieViewModel(
    private val theMovieDbRepository: TheMovieDbRepository
) : ViewModel() {
    /**
     * Bag used to clean Rx Observables observation to avoid memory leaks
     */
    private val disposeBag = CompositeDisposable()

    private val _popularMovies: BehaviorSubject<List<TheMovieDbDto>> =
        BehaviorSubject.createDefault(listOf())
    private val _resultMovies: BehaviorSubject<List<TheMovieDbDto>> =
        BehaviorSubject.createDefault(listOf())

    val popularMovies: MutableLiveData<List<TheMovieDbDto>> = MutableLiveData()
    val resultMovies: MutableLiveData<List<TheMovieDbDto>> = MutableLiveData()

    init {
        _popularMovies.subscribe { popularMovies.postValue(it) }.addTo(disposeBag)
        _resultMovies.subscribe { resultMovies.postValue(it) }.addTo(disposeBag)
    }

    fun getPopularMovies(page: Int) {
        this.theMovieDbRepository.getPopularMovies(page).subscribe(
            {
                this._popularMovies.onNext(it)
            },
            { error ->
                Log.d("getPopularMovies", error.message.toString())
            }
        ).addTo(disposeBag)
    }

    fun getMoviesByQuery(query: String, page: Int) {
        this.theMovieDbRepository.getMoviesByQuery(query, page).subscribe(
            {
                this._resultMovies.onNext(it)
            },
            { error ->
                Log.d("getMoviesByQuery", error.message.toString())
            }
        ).addTo(disposeBag)
    }


    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }
}