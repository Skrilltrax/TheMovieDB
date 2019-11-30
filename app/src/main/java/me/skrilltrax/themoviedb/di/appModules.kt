package me.skrilltrax.themoviedb.di

import me.skrilltrax.themoviedb.Constants.SERVER_URL
import me.skrilltrax.themoviedb.di.factory.HttpLoggingInterceptorFactory
import me.skrilltrax.themoviedb.di.factory.OkHttpFactory
import me.skrilltrax.themoviedb.di.factory.RetrofitFactory
import me.skrilltrax.themoviedb.di.factory.StethoInterceptorFactory
import me.skrilltrax.themoviedb.network.api.movie.MovieApiInterface
import me.skrilltrax.themoviedb.network.api.movie.MovieDetailRepository
import me.skrilltrax.themoviedb.network.api.movie.MovieListRepository
import me.skrilltrax.themoviedb.ui.homepage.movie.MovieListViewModel
import me.skrilltrax.themoviedb.ui.moviedetail.MovieDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
}

val networkModule = module {
    single { HttpLoggingInterceptorFactory.provideHttpLoggingInterceptor() }
    single { StethoInterceptorFactory.provideStethoInterceptor() }
    single { OkHttpFactory.provideOkHttpClient(get(), get()) }
    single { RetrofitFactory.createWebService<MovieApiInterface>(get(), SERVER_URL) }
}

val repositoryModule = module {
    single { MovieListRepository(get()) }
    single { MovieDetailRepository(get()) }
}

val viewModelModule = module {
    viewModel { MovieListViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
}
