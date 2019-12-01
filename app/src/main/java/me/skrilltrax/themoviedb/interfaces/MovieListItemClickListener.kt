package me.skrilltrax.themoviedb.interfaces

import me.skrilltrax.themoviedb.model.list.ListResultItem

interface MovieListItemClickListener {
    fun onMovieItemClick(movieResultsItem: ListResultItem)
}