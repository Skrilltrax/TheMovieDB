package me.skrilltrax.themoviedb.di.factory

import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

object HttpLoggingInterceptorFactory {

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        Timber.tag("Logging")
        val httpLoggingInterceptor = HttpLoggingInterceptor() //TODO: Use timber implementation
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}