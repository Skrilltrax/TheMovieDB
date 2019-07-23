package me.skrilltrax.themoviedb.network.api.movie

import android.app.Application
import androidx.room.Room
import me.skrilltrax.themoviedb.BuildConfig
//import me.skrilltrax.themoviedb.db.AppDatabase
import me.skrilltrax.themoviedb.model.movie.lists.MovieListResponse
import me.skrilltrax.themoviedb.network.BaseRepository

class MovieListRepository(/*application: Application*/) : BaseRepository() {


    init {
    }

    suspend fun getPopularMovieList(): MovieListResponse? {

        return safeApiCall(
            call = { MovieApiInterface.getClient().getPopularMovies(BuildConfig.API_KEY) },
            errorMessage = "Error fetching popular movies"
        ).apply {
            this?.results?.forEach {
                it?.type = "popular"
            }
        }
    }

    suspend fun getUpcomingMovieList(): MovieListResponse? {

        return safeApiCall(
            call = { MovieApiInterface.getClient().getUpcomingMovies(BuildConfig.API_KEY) },
            errorMessage = "Error fetching upcoming movies"
        ).apply {
            this?.results?.forEach {
                it?.type = "upcoming"
            }
        }
    }

    suspend fun getPlayingMovieList(): MovieListResponse? {

        return safeApiCall(
            call = { MovieApiInterface.getClient().getNowPlayingMovies(BuildConfig.API_KEY) },
            errorMessage = "Error fetching now playing movies"
        ).apply {
            this?.results?.forEach {
                it?.type = "playing"
            }
        }
    }

    suspend fun getTopRatedMovieList(): MovieListResponse? {

        return safeApiCall(
            call = { MovieApiInterface.getClient().getTopRatedMovies(BuildConfig.API_KEY) },
            errorMessage = "Error fetching top rated movies"
        ).apply {
            this?.results?.forEach {
                it?.type = "top_rated"
            }
        }
    }

    fun checkDB(listName: String) {

    }

}