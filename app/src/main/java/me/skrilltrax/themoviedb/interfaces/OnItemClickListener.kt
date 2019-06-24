package me.skrilltrax.themoviedb.interfaces

import me.skrilltrax.themoviedb.model.movie.lists.MovieResultsItem

interface OnItemClickListener {
    fun onMovieItemClick(movieResultsItem: MovieResultsItem)
}