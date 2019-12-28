package me.skrilltrax.themoviedb.model.list

import com.google.gson.annotations.SerializedName

data class ListResponse(

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("results")
    val results: List<ListResultItem?>? = null,

    @field:SerializedName("total_results")
    val totalResults: Int? = null
)
