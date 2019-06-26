package me.skrilltrax.themoviedb.model.movie.credits

import com.google.gson.annotations.SerializedName

data class MovieCreditsResponse(

	@field:SerializedName("cast")
	val cast: List<CastItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("crew")
	val crew: List<CrewItem?>? = null
)