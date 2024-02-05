package com.cineschoolproject.di.modules

import kotlinx.coroutines.runBlocking
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        val database = provideDatabase(androidContext())
        runBlocking {
            database.prepopulateDatabase()
        }

        database
    }
    single { provideDao(get()) }
}