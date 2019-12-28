package me.skrilltrax.themoviedb.interfaces

import me.skrilltrax.themoviedb.model.videos.VideoResultsItem

interface MovieDetailItemClickListener {
    fun onVideoItemClick(videoResultsItem: VideoResultsItem)
}
