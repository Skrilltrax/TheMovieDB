package me.skrilltrax.themoviedb.network.api.movie

import me.skrilltrax.themoviedb.BuildConfig
import me.skrilltrax.themoviedb.model.movie.credits.MovieCreditsResponse
import me.skrilltrax.themoviedb.model.movie.detail.MovieDetailResponse
import me.skrilltrax.themoviedb.model.movie.list.MovieListResponse
import me.skrilltrax.themoviedb.model.movie.videos.MovieVideoResponse
import me.skrilltrax.themoviedb.network.BaseRepository

class MovieDetailRepository(private val client: MovieApiInterface) : BaseRepository() {
    
    suspend fun getMovieDetails(movieId: String): MovieDetailResponse? {

        return safeApiCall(
            call = { client.getMovieDetails(movieId, BuildConfig.API_KEY) },
            errorMessage = "Error fetching movie details"
        )
    }

    suspend fun getCastCrew(movieId: String): MovieCreditsResponse? {

        return safeApiCall(
            call = { client.getMovieCredits(movieId, BuildConfig.API_KEY) },
            errorMessage = "Error fetching movie credits"
        )
    }

    suspend fun getVideos(movieId: String): MovieVideoResponse? {

        return safeApiCall(
            call = {client.getMovieVideos(movieId, BuildConfig.API_KEY)},
            errorMessage = "Error fetching movie videos"
        )
    }

    suspend fun getRecommendations(movieId: String): MovieListResponse? {

        return safeApiCall(
            call = {client.getMovieRecommendations(movieId, BuildConfig.API_KEY)},
            errorMessage = "Error fetching recommendations"
        )
    }
}