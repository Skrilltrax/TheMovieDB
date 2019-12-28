package me.skrilltrax.themoviedb.network.api.tv

import me.skrilltrax.themoviedb.model.credits.CreditsResponse
import me.skrilltrax.themoviedb.model.list.ListResponse
import me.skrilltrax.themoviedb.model.videos.VideoResponse
import me.skrilltrax.themoviedb.network.RetrofitInstance
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TVApiInterface {
    @GET("tv/popular")
    suspend fun getPopularShows(@Query("api_key") apiKey: String): Response<ListResponse>

    @GET("tv/latest")
    suspend fun getLatestShows(@Query("api_key") apiKey: String): Response<ListResponse>

    @GET("tv/on_the_air")
    suspend fun getOnAirShows(@Query("api_key") apiKey: String): Response<ListResponse>

    @GET("tv/airing_today")
    suspend fun getAiringShows(@Query("api_key") apiKey: String): Response<ListResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedShows(@Query("api_key") apiKey: String): Response<ListResponse>

//    @GET("tv/{tv_id}")
//    suspend fun getMovieDetails(@Path("movie_id") id: String, @Query("api_key") apiKey: String): Response<MovieDetailResponse>

    @GET("tv/{tv_id}/credits")
    suspend fun getMovieCredits(@Path("movie_id") id: String, @Query("api_key") apiKey: String): Response<CreditsResponse>

    @GET("tv/{tv_id}/videos")
    suspend fun getMovieVideos(@Path("movie_id") id: String, @Query("api_key") apiKey: String): Response<VideoResponse>

    @GET("tv/{tv_id}/recommendations")
    suspend fun getMovieRecommendations(@Path("tv_id") id: String, @Query("api_key") apiKey: String): Response<ListResponse>

    companion object {
        fun getClient(): TVApiInterface {
            val retrofit = RetrofitInstance.getInstance()
            return retrofit.create(TVApiInterface::class.java)
        }
    }
}
