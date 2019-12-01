package me.skrilltrax.themoviedb.model.videos

import com.google.gson.annotations.SerializedName

data class VideoResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("results")
	val results: List<VideoResultsItem?>? = null
)