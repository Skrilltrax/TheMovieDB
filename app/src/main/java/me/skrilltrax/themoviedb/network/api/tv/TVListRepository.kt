package me.skrilltrax.themoviedb.network.api.tv

import me.skrilltrax.themoviedb.BuildConfig
import me.skrilltrax.themoviedb.model.list.ListResponse
import me.skrilltrax.themoviedb.network.BaseRepository

class TVListRepository(private val client: TVApiInterface) : BaseRepository() {

    suspend fun getPopularShowsList(): ListResponse? {

        return safeApiCall(
            call = { client.getPopularShows(BuildConfig.API_KEY) },
            errorMessage = "Error fetching popular shows"
        ).apply {
            this?.results?.forEach {
                it?.type = "popular"
            }
        }
    }

    suspend fun getAiringShowsList(): ListResponse? {

        return safeApiCall(
            call = { client.getAiringShows(BuildConfig.API_KEY) },
            errorMessage = "Error fetching upcoming movies"
        ).apply {
            this?.results?.forEach {
                it?.type = "upcoming"
            }
        }
    }

    suspend fun getOnAirShowsList(): ListResponse? {

        return safeApiCall(
            call = { client.getOnAirShows(BuildConfig.API_KEY) },
            errorMessage = "Error fetching now playing movies"
        ).apply {
            this?.results?.forEach {
                it?.type = "playing"
            }
        }
    }

    suspend fun getTopRatedShowsList(): ListResponse? {

        return safeApiCall(
            call = { client.getTopRatedShows(BuildConfig.API_KEY) },
            errorMessage = "Error fetching top rated movies"
        ).apply {
            this?.results?.forEach {
                it?.type = "top_rated"
            }
        }
    }

}