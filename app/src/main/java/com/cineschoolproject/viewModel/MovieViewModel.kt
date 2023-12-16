package com.cineschoolproject.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cineschoolproject.models.movie_model.TheMovieDbDto
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

    /**
     * Observables to communicate with the repository
     */
    private val theMovieDbDtoData: BehaviorSubject<List<TheMovieDbDto>> =
        BehaviorSubject.createDefault(listOf())
    val popularMovieListLiveData: MutableLiveData<List<TheMovieDbDto>> = MutableLiveData()
    val searchMovieListLiveData: MutableLiveData<List<TheMovieDbDto>> = MutableLiveData()

    /**
     * Observable to notify the view of new data
     */
//    val completeUsersList: MutableLiveData<List<CompleteUserDto>> = MutableLiveData()

    init {
        theMovieDbDtoData.subscribe { popularMovieListLiveData.postValue(it) }.addTo(disposeBag)
        theMovieDbDtoData.subscribe { searchMovieListLiveData.postValue(it) }.addTo(disposeBag)
    }

    fun getMoviesByInput(input: String, page: Int) {
        this.theMovieDbRepository.getMoviesByQuery(input, page).subscribe(
            {
                //todo map to movie at this step
                this.theMovieDbDtoData.onNext(it)
            },
            {
                Log.d("getMoviesByInput", it.message.toString())
            }
        ).addTo(disposeBag)
    }


    fun getPopularMovies(page: Int) {
        this.theMovieDbRepository.getPopularMovies(page).subscribe(
            {
                //todo map to movie at this step
                this.theMovieDbDtoData.onNext(it)
            },
            {
                Log.d("getPopularMovies", it.message.toString())
            }
        ).addTo(disposeBag)
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }
}