package me.skrilltrax.themoviedb.model.movie.detail

import com.google.gson.annotations.SerializedName

data class ProductionCompaniesItem(

	@field:SerializedName("logo_path")
	val logoPath: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("origin_country")
	val originCountry: String? = null
)