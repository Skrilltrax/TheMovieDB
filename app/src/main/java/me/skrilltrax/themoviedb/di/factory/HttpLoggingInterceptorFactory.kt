package me.skrilltrax.themoviedb.di.factory

import okhttp3.logging.HttpLoggingInterceptor

object HttpLoggingInterceptorFactory {
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor() // TODO: Use timber implementation
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}
