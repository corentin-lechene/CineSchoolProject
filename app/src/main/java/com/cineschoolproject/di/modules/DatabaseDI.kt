package com.cineschoolproject.di.modules

import android.content.Context
import androidx.room.Room
import com.cineschoolproject.db.AppDatabase

fun provideDatabase(context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, "App_database")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
        

fun provideDao(db: AppDatabase) = db.movieSeenDao()