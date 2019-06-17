package me.skrilltrax.themoviedb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.skrilltrax.themoviedb.model.MovieResultsItem
import timber.log.Timber

class MovieAdapter(private val movieList: List<MovieResultsItem>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieName: TextView = itemView.findViewById(R.id.movieTitle)
        val movieImage: ImageView = itemView.findViewById(R.id.movieImage)
        fun bind(movieResultsItem: MovieResultsItem) {
            Timber.d("InOnBind")
            Timber.d(movieResultsItem.title)
            movieName.text = movieResultsItem.title
        }
    }
}