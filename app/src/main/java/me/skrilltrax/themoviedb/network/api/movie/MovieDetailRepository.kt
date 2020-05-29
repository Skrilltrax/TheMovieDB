package me.skrilltrax.themoviedb.network.api.movie

import me.skrilltrax.themoviedb.BuildConfig
import me.skrilltrax.themoviedb.model.credits.CreditsResponse
import me.skrilltrax.themoviedb.model.list.movie.MovieListResponse
import me.skrilltrax.themoviedb.model.detail.movie.MovieDetailResponse
import me.skrilltrax.themoviedb.model.videos.VideoResponse
import me.skrilltrax.themoviedb.network.BaseRepository

class MovieDetailRepository(private val client: MovieApiInterface) : BaseRepository() {

    suspend fun getMovieDetails(movieId: String): MovieDetailResponse? {

        return safeApiCall(
            call = { client.getMovieDetails(movieId, BuildConfig.API_KEY) },
            errorMessage = "Error fetching movie details"
        )
    }

    suspend fun getCastCrew(movieId: String): CreditsResponse? {

        return safeApiCall(
            call = { client.getMovieCredits(movieId, BuildConfig.API_KEY) },
            errorMessage = "Error fetching movie credits"
        )
    }

    suspend fun getVideos(movieId: String): VideoResponse? {

        return safeApiCall(
            call = { client.getMovieVideos(movieId, BuildConfig.API_KEY) },
            errorMessage = "Error fetching movie videos"
        )
    }

    suspend fun getRecommendations(movieId: String): MovieListResponse? {

        return safeApiCall(
            call = { client.getMovieRecommendations(movieId, BuildConfig.API_KEY) },
            errorMessage = "Error fetching recommendations"
        )
    }
}
