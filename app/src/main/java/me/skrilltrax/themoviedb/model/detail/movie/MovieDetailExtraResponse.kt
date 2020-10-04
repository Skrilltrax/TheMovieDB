package me.skrilltrax.themoviedb.model.detail.movie

import com.google.gson.annotations.SerializedName
import me.skrilltrax.themoviedb.model.credits.CreditsResponse
import me.skrilltrax.themoviedb.model.detail.GenresItem
import me.skrilltrax.themoviedb.model.detail.ProductionCompaniesItem
import me.skrilltrax.themoviedb.model.list.movie.MovieListResponse
import me.skrilltrax.themoviedb.model.videos.VideoResponse

data class MovieDetailExtraResponse(
    @field:SerializedName("videos")
    val videos: VideoResponse,

    @field:SerializedName("credits")
    val credits: CreditsResponse,

    @field:SerializedName("recommendations")
    val recommendations: MovieListResponse,
) : MovieDetailResponse()
