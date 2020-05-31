package me.skrilltrax.themoviedb.network.api.tv

import me.skrilltrax.themoviedb.model.credits.CreditsResponse
import me.skrilltrax.themoviedb.model.detail.tv.TVDetailResponse
import me.skrilltrax.themoviedb.model.list.tv.TVListResponse
import me.skrilltrax.themoviedb.model.videos.VideoResponse
import me.skrilltrax.themoviedb.network.RetrofitInstance
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TVApiInterface {
    @GET("tv/popular")
    suspend fun getPopularShows(@Query("api_key") apiKey: String): Response<TVListResponse>

    @GET("tv/latest")
    suspend fun getLatestShow(@Query("api_key") apiKey: String): Response<TVListResponse>

    @GET("tv/on_the_air")
    suspend fun getOnAirShows(@Query("api_key") apiKey: String): Response<TVListResponse>

    @GET("tv/airing_today")
    suspend fun getAiringShows(@Query("api_key") apiKey: String): Response<TVListResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedShows(@Query("api_key") apiKey: String): Response<TVListResponse>

    @GET("tv/{show_id}")
    suspend fun getShowDetails(@Path("show_id") id: String, @Query("api_key") apiKey: String): Response<TVDetailResponse>

    @GET("tv/{show_id}/credits")
    suspend fun getShowCredits(@Path("show_id") id: String, @Query("api_key") apiKey: String): Response<CreditsResponse>

    @GET("tv/{show_id}/videos")
    suspend fun getShowVideos(@Path("show_id") id: String, @Query("api_key") apiKey: String): Response<VideoResponse>

    @GET("tv/{show_id}/recommendations")
    suspend fun getShowRecommendations(@Path("show_id") id: String, @Query("api_key") apiKey: String): Response<TVListResponse>

    companion object {
        fun getClient(): TVApiInterface {
            val retrofit = RetrofitInstance.getInstance()
            return retrofit.create(TVApiInterface::class.java)
        }
    }
}
