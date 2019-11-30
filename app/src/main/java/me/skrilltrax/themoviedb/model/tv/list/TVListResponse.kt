package me.skrilltrax.themoviedb.model.tv.list

import com.google.gson.annotations.SerializedName

data class TVListResponse(

    @field:SerializedName("page")
	val page: Int? = null,

    @field:SerializedName("total_pages")
	val totalPages: Int? = null,

    @field:SerializedName("results")
	val results: List<TVResultsItem?>? = null,

    @field:SerializedName("total_results")
	val totalResults: Int? = null
)