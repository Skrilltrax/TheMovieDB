package me.skrilltrax.themoviedb.interfaces

import me.skrilltrax.themoviedb.model.list.ListResultItem

interface ListItemClickListener {
    fun onItemClick(resultsItem: ListResultItem)
}
