package me.skrilltrax.themoviedb.network.api.movie

import me.skrilltrax.themoviedb.BuildConfig
import me.skrilltrax.themoviedb.model.list.ListResponse
import me.skrilltrax.themoviedb.network.BaseRepository

class MovieListRepository(private val client: MovieApiInterface) : BaseRepository() {

    suspend fun getPopularMovieList(): ListResponse? {

        return safeApiCall(
            call = { client.getPopularMovies(BuildConfig.API_KEY) },
            errorMessage = "Error fetching popular movies"
        ).apply {
            this?.results?.forEach {
                it?.type = "popular"
            }
        }
    }

    suspend fun getUpcomingMovieList(): ListResponse? {

        return safeApiCall(
            call = { client.getUpcomingMovies(BuildConfig.API_KEY) },
            errorMessage = "Error fetching upcoming movies"
        ).apply {
            this?.results?.forEach {
                it?.type = "upcoming"
            }
        }
    }

    suspend fun getPlayingMovieList(): ListResponse? {

        return safeApiCall(
            call = { client.getNowPlayingMovies(BuildConfig.API_KEY) },
            errorMessage = "Error fetching now playing movies"
        ).apply {
            this?.results?.forEach {
                it?.type = "playing"
            }
        }
    }

    suspend fun getTopRatedMovieList(): ListResponse? {

        return safeApiCall(
            call = { client.getTopRatedMovies(BuildConfig.API_KEY) },
            errorMessage = "Error fetching top rated movies"
        ).apply {
            this?.results?.forEach {
                it?.type = "top_rated"
            }
        }
    }

}