package me.skrilltrax.themoviedb.model.movie.videos

import com.google.gson.annotations.SerializedName

data class MovieVideoResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("results")
	val results: List<VideoResultsItem?>? = null
)