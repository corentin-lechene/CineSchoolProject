package com.cineschoolproject.di.modules

import com.cineschoolproject.repositories.MovieSeenRepository
import com.cineschoolproject.repositories.TheMovieDbRepository
import com.cineschoolproject.viewModel.MovieSeenViewModel
import com.cineschoolproject.viewModel.MovieViewModel
import org.koin.dsl.module

/**
 * Module where all the core classes to inject must be declared
 */
internal val coreModule = module {
    single { MovieViewModel(get()) }

    single { TheMovieDbRepository(get()) }

    single { MovieSeenViewModel(get()) }

    single { MovieSeenRepository(get()) }
}