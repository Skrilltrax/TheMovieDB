package me.skrilltrax.themoviedb.interfaces

import me.skrilltrax.themoviedb.model.movie.lists.MovieResultsItem

interface MovieListItemClickListener {
    fun onMovieItemClick(movieResultsItem: MovieResultsItem)
}