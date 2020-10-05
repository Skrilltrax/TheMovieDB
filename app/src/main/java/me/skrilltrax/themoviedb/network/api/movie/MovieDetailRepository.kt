package me.skrilltrax.themoviedb.network.api.movie

import me.skrilltrax.themoviedb.BuildConfig
import me.skrilltrax.themoviedb.model.credits.CreditsResponse
import me.skrilltrax.themoviedb.model.detail.movie.MovieDetailExtraResponse
import me.skrilltrax.themoviedb.model.detail.movie.MovieDetailResponse
import me.skrilltrax.themoviedb.model.list.movie.MovieListResponse
import me.skrilltrax.themoviedb.model.videos.VideoResponse
import me.skrilltrax.themoviedb.network.BaseRepository

class MovieDetailRepository(private val client: MovieApiInterface) : BaseRepository() {

    suspend fun getMovieDetailsWithExtras(
        movieId: String,
        includeVideos: Boolean = true,
        includeCredits: Boolean = true,
        includeRecommendations: Boolean = true
    ): MovieDetailExtraResponse? {
        var extras = ""
        if (includeVideos) extras += "videos,"
        if (includeCredits) extras += "credits,"
        if (includeRecommendations) extras += "recommendations"

        return safeApiCall(
            call = { client.getMovieDetailsWithExtras(movieId, BuildConfig.API_KEY, extras) },
            errorMessage = "Error fetching movie details"
        )
    }

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
