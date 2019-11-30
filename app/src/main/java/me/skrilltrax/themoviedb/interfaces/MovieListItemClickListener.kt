package me.skrilltrax.themoviedb.interfaces

import me.skrilltrax.themoviedb.model.movie.list.MovieResultsItem

interface MovieListItemClickListener {
    fun onMovieItemClick(movieResultsItem: MovieResultsItem)
}