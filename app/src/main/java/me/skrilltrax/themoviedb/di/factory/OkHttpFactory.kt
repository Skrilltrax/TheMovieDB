package me.skrilltrax.themoviedb.di.factory

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object OkHttpFactory {

    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, stethoInterceptor: StethoInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(stethoInterceptor)
            .build()
    }
}