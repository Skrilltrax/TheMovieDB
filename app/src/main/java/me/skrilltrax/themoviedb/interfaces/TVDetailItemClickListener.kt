package me.skrilltrax.themoviedb.interfaces

import me.skrilltrax.themoviedb.model.videos.VideoResultsItem

interface TVDetailItemClickListener {
    fun onVideoItemClick(videoResultsItem: VideoResultsItem)
}
