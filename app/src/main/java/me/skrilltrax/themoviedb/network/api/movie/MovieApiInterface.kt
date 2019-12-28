package me.skrilltrax.themoviedb.network.api.movie

import me.skrilltrax.themoviedb.model.credits.CreditsResponse
import me.skrilltrax.themoviedb.model.list.ListResponse
import me.skrilltrax.themoviedb.model.movie.detail.MovieDetailResponse
import me.skrilltrax.themoviedb.model.videos.VideoResponse
import me.skrilltrax.themoviedb.network.RetrofitInstance
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiInterface {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): Response<ListResponse>

    @GET("movie/latest")
    suspend fun getLatestMovies(@Query("api_key") apiKey: String): Response<ListResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") apiKey: String): Response<ListResponse>

    // TODO : Pass region parameter to get proper upcoming movies
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String): Response<ListResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String): Response<ListResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: String, @Query("api_key") apiKey: String): Response<MovieDetailResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(@Path("movie_id") id: String, @Query("api_key") apiKey: String): Response<CreditsResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(@Path("movie_id") id: String, @Query("api_key") apiKey: String): Response<VideoResponse>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getMovieRecommendations(@Path("movie_id") id: String, @Query("api_key") apiKey: String): Response<ListResponse>

    companion object {
        fun getClient(): MovieApiInterface {
            val retrofit = RetrofitInstance.getInstance()
            return retrofit.create(MovieApiInterface::class.java)
        }
    }
}
