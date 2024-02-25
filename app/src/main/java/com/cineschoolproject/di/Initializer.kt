package com.cineschoolproject.di
import android.content.Context
import com.cineschoolproject.BuildConfig
import com.cineschoolproject.di.modules.coreModule
import com.cineschoolproject.di.modules.databaseModule
import com.cineschoolproject.di.modules.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException
import org.koin.dsl.module

fun injectModuleDependencies(context: Context) {
    try {
        startKoin {
            androidContext(context)
            modules(modules)
        }
    } catch (alreadyStart: KoinAppAlreadyStartedException) {
        loadKoinModules(modules)
    }
}

/**
 * Function to parse current build config depending on build variant (debug/release)
 * and inject the structure representing it in the dependency tree
 */
fun parseAndInjectConfiguration() {
    val apiConf = TheMovieDb(
        apiUrl = BuildConfig.TMDB_API_URL,
        mediaUrl = BuildConfig.TMDB_MEDIA_URL,
        apiKey = BuildConfig.TMDB_API_KEY
    )
    modules.add(
        module {
            single { apiConf }
        }
    )
}

private val modules = mutableListOf(coreModule, remoteModule, databaseModule)

data class TheMovieDb(val apiUrl: String, val mediaUrl: String, val apiKey: String)