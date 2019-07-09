package me.skrilltrax.themoviedb.network.api.movie

import me.skrilltrax.themoviedb.BuildConfig
import me.skrilltrax.themoviedb.model.movie.credits.MovieCreditsResponse
import me.skrilltrax.themoviedb.model.movie.detail.MovieDetailResponse
import me.skrilltrax.themoviedb.model.movie.videos.MovieVideoResponse
import me.skrilltrax.themoviedb.network.BaseRepository

class MovieDetailRepository : BaseRepository() {

    suspend fun getMovieDetails(movieId: String): MovieDetailResponse? {

        return safeApiCall(
            call = { MovieApiInterface.getClient().getMovieDetails(movieId, BuildConfig.API_KEY) },
            errorMessage = "Error fetching movie details"
        )
    }

    suspend fun getCastCrew(movieId: String): MovieCreditsResponse? {

        return safeApiCall(
            call = { MovieApiInterface.getClient().getMovieCredits(movieId, BuildConfig.API_KEY) },
            errorMessage = "Error fetching movie credits"
        )
    }

    suspend fun getVideos(movieId: String): MovieVideoResponse? {

        return safeApiCall(
            call = {MovieApiInterface.getClient().getMovieVideos(movieId, BuildConfig.API_KEY)},
            errorMessage = "Error fetching movie videos"
        )
    }
}