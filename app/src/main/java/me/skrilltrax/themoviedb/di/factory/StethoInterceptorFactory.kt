package me.skrilltrax.themoviedb.di.factory

import com.facebook.stetho.okhttp3.StethoInterceptor

object StethoInterceptorFactory {
    fun provideStethoInterceptor() : StethoInterceptor {
        return StethoInterceptor()
    }
}