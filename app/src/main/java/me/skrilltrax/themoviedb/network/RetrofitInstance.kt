package me.skrilltrax.themoviedb.network

import me.skrilltrax.themoviedb.BuildConfig
import me.skrilltrax.themoviedb.utils.httpLoggingInterceptor
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
        return httpLoggingInterceptor(HttpLoggingInterceptor.Level.BASIC) { message ->
            Timber.tag("okhttp").d(message)
        }
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.IS_DEBUG) {
            okHttpBuilder.addInterceptor(provideHttpLoggingInterceptor())
        }
        return okHttpBuilder.build()
    }
}
