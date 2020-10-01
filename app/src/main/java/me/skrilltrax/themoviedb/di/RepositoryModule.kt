package me.skrilltrax.themoviedb.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton
import me.skrilltrax.themoviedb.network.api.movie.MovieApiInterface
import me.skrilltrax.themoviedb.network.api.movie.MovieDetailRepository
import me.skrilltrax.themoviedb.network.api.movie.MovieListRepository
import me.skrilltrax.themoviedb.network.api.tv.TVApiInterface
import me.skrilltrax.themoviedb.network.api.tv.TVDetailRepository
import me.skrilltrax.themoviedb.network.api.tv.TVListRepository

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieDetailRepository(movieApiInterface: MovieApiInterface): MovieDetailRepository {
        return MovieDetailRepository(movieApiInterface)
    }

    @Provides
    @Singleton
    fun provideMovieListRepository(movieApiInterface: MovieApiInterface): MovieListRepository {
        return MovieListRepository(movieApiInterface)
    }

    @Provides
    @Singleton
    fun provideTVDetailRepository(tvApiInterface: TVApiInterface): TVDetailRepository {
        return TVDetailRepository(tvApiInterface)
    }

    @Provides
    @Singleton
    fun provideTVListRepository(tvApiInterface: TVApiInterface): TVListRepository {
        return TVListRepository(tvApiInterface)
    }
}
