package me.skrilltrax.themoviedb.interfaces

import me.skrilltrax.themoviedb.model.list.movie.MovieListResultItem
import me.skrilltrax.themoviedb.model.list.tv.TVListResultItem

interface ListItemClickListener {
    fun onItemClick(resultsItem: MovieListResultItem) {
    }

    fun onItemClick(resultsItem: TVListResultItem) {
    }

    fun onItemClick(id: String) {
    }
}
