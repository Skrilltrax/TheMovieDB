package me.skrilltrax.themoviedb.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

object RetrofitInstance {

    private lateinit var retrofit: Retrofit

    fun getInstance(): Retrofit {

        if (!this::retrofit.isInitialized) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        Timber.tag("Logging")
        val httpLoggingInterceptor = HttpLoggingInterceptor { message -> Timber.i(message) }
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(provideHttpLoggingInterceptor())
            .build()
    }
}