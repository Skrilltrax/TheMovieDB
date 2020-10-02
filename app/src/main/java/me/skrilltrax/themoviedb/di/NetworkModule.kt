package me.skrilltrax.themoviedb.di

import com.github.ajalt.timberkt.Timber.d
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton
import me.skrilltrax.themoviedb.constants.Constants
import me.skrilltrax.themoviedb.network.api.movie.MovieApiInterface
import me.skrilltrax.themoviedb.network.api.tv.TVApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(ApplicationComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor(logger = object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    d { message }
                }
            })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApiInterface(okHttpClient: OkHttpClient): MovieApiInterface {
        return Retrofit.Builder()
            .baseUrl(Constants.SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideTVApiInterface(okHttpClient: OkHttpClient): TVApiInterface {
        return Retrofit.Builder()
            .baseUrl(Constants.SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TVApiInterface::class.java)
    }
}
