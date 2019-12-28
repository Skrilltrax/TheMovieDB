package me.skrilltrax.themoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.skrilltrax.themoviedb.databinding.ItemVideoBinding
import me.skrilltrax.themoviedb.interfaces.MovieDetailItemClickListener
import me.skrilltrax.themoviedb.model.videos.VideoResultsItem

class VideoAdapter(private val videoList: List<VideoResultsItem>, private val listener: MovieDetailItemClickListener) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    lateinit var binding: ItemVideoBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemVideoBinding.inflate(inflater, parent, false)
        return VideoViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            binding.videoData = videoList[position]
            itemView.setOnClickListener {
                listener.onVideoItemClick(videoList[position])
            }
        }
    }
}
