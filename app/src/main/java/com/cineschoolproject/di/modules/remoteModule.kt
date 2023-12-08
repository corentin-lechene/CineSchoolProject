package com.cineschoolproject.di.modules

import com.cineschoolproject.di.TheMovieDb
import com.cineschoolproject.services.TheMovieDbApiService

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Module where all the remote (network) classes to inject must be declared
 */
internal val remoteModule = module {
    single(named(fakeApiRetrofitClient)) { createRetrofitClient(get(), get<TheMovieDb>().baseUrl, get<TheMovieDb>().apiKey) }
    single { createOkHttpClient() }


    single {
        createWebService<TheMovieDbApiService>(
            get(named(fakeApiRetrofitClient))
        )
    }
}

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()
}

fun createRetrofitClient(okhttpClient: OkHttpClient, baseUrl: String, apiKey: String): Retrofit {
    val gsonConverter =
        GsonConverterFactory.create(
            GsonBuilder().create()
        )

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okhttpClient)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(gsonConverter)
        .apply {
            client(okhttpClient.newBuilder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val request = original.newBuilder()
                        .header("Authorization", "Bearer $apiKey")
                        .method(original.method, original.body)
                        .build()
                    chain.proceed(request)
                }
                .build())
        }
        .build()
}

inline fun <reified T> createWebService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}

const val fakeApiRetrofitClient = "fakeApiRetrofitClient"