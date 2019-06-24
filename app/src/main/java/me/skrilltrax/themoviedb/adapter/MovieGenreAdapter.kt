package me.skrilltrax.themoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.model.movie.detail.GenresItem

class MovieGenreAdapter(val genreList: List<GenresItem>) : RecyclerView.Adapter<MovieGenreAdapter.MovieGenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return MovieGenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieGenreViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    inner class MovieGenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val genreButton: MaterialButton = itemView.findViewById(R.id.genre_button)
        private var genreID: Int? = null
        fun bind(position: Int) {
            genreID = genreList[position].id!!
            genreButton.text = genreList[position].name
        }
    }
}