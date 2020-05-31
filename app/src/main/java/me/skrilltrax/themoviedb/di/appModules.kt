package me.skrilltrax.themoviedb.di

import me.skrilltrax.themoviedb.constants.Constants.SERVER_URL
import me.skrilltrax.themoviedb.di.factory.HttpLoggingInterceptorFactory
import me.skrilltrax.themoviedb.di.factory.OkHttpFactory
import me.skrilltrax.themoviedb.di.factory.RetrofitFactory
import me.skrilltrax.themoviedb.network.api.movie.MovieApiInterface
import me.skrilltrax.themoviedb.network.api.movie.MovieDetailRepository
import me.skrilltrax.themoviedb.network.api.movie.MovieListRepository
import me.skrilltrax.themoviedb.network.api.tv.TVApiInterface
import me.skrilltrax.themoviedb.network.api.tv.TVDetailRepository
import me.skrilltrax.themoviedb.network.api.tv.TVListRepository
import me.skrilltrax.themoviedb.ui.homepage.movie.MovieListViewModel
import me.skrilltrax.themoviedb.ui.homepage.tv.TVListViewModel
import me.skrilltrax.themoviedb.ui.moviedetail.MovieDetailViewModel
import me.skrilltrax.themoviedb.ui.tvdetail.TVDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
}

val networkModule = module {
    single { HttpLoggingInterceptorFactory.provideHttpLoggingInterceptor() }
    single { OkHttpFactory.provideOkHttpClient(get()) }
    single { RetrofitFactory.createWebService<MovieApiInterface>(get(), SERVER_URL) }
    single { RetrofitFactory.createWebService<TVApiInterface>(get(), SERVER_URL) }
}

val repositoryModule = module {
    single { MovieListRepository(get()) }
    single { TVListRepository(get()) }
    single { MovieDetailRepository(get()) }
    single { TVDetailRepository(get()) }
}

val viewModelModule = module {
    viewModel { MovieListViewModel(get()) }
    viewModel { TVListViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
    viewModel { TVDetailViewModel(get()) }
}
