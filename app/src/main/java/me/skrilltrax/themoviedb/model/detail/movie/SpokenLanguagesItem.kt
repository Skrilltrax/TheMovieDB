package me.skrilltrax.themoviedb.model.detail.movie

import com.google.gson.annotations.SerializedName

data class SpokenLanguagesItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("iso_639_1")
    val iso6391: String? = null
)
