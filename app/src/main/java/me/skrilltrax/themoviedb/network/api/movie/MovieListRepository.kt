package me.skrilltrax.themoviedb.network.api.movie

import me.skrilltrax.themoviedb.BuildConfig
import me.skrilltrax.themoviedb.model.movie.lists.MovieListResponse
import me.skrilltrax.themoviedb.network.BaseRepository

class MovieListRepository : BaseRepository() {

    suspend fun getPopularMovieList(): MovieListResponse? {

        return safeApiCall(
            call = { MovieApiInterface.getClient().getPopularMovies(BuildConfig.API_KEY) },
            errorMessage = "Error fetching popular movies"
        )
    }

    suspend fun getUpcomingMovieList(): MovieListResponse? {

        return safeApiCall(
            call = { MovieApiInterface.getClient().getUpcomingMovies(BuildConfig.API_KEY) },
            errorMessage = "Error fetching upcoming movies"
        )
    }

    suspend fun getPlayingMovieList(): MovieListResponse? {

        return safeApiCall(
            call = { MovieApiInterface.getClient().getNowPlayingMovies(BuildConfig.API_KEY) },
            errorMessage = "Error fetching now playing movies"
        )
    }

    suspend fun getTopRatedMovieList(): MovieListResponse? {

        return safeApiCall(
            call = { MovieApiInterface.getClient().getTopRatedMovies(BuildConfig.API_KEY) },
            errorMessage = "Error fetching top rated movies"
        )
    }

}