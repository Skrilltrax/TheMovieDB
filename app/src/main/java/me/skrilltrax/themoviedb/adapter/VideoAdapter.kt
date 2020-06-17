package me.skrilltrax.themoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.skrilltrax.themoviedb.databinding.ItemVideoBinding
import me.skrilltrax.themoviedb.interfaces.MovieDetailItemClickListener
import me.skrilltrax.themoviedb.model.videos.VideoResultsItem
import me.skrilltrax.themoviedb.utils.setThumbnail

class VideoAdapter(private val videoList: List<VideoResultsItem>, private val listener: MovieDetailItemClickListener) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVideoBinding.inflate(inflater, parent, false)
        return VideoViewHolder(binding, binding.root)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    inner class VideoViewHolder(private val  binding: ItemVideoBinding, itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val videoData = videoList[position]

            with(binding) {
                videoData.key?.let { videoPoster.setThumbnail(it) }
                videoData.name?.let { videoTitle.text = it }
            }

            itemView.setOnClickListener {
                listener.onVideoItemClick(videoList[position])
            }
        }
    }
}
