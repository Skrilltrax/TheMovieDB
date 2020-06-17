package me.skrilltrax.themoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.skrilltrax.themoviedb.databinding.ItemGenreBinding
import me.skrilltrax.themoviedb.model.detail.GenresItem

class GenreAdapter(val genreList: List<GenresItem>) : RecyclerView.Adapter<GenreAdapter.MovieGenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGenreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGenreBinding.inflate(inflater, parent, false)
        return MovieGenreViewHolder(binding, binding.root)
    }

    override fun onBindViewHolder(holder: MovieGenreViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    inner class MovieGenreViewHolder(private val binding: ItemGenreBinding, itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            binding.genreButton.text = genreList[position].name
        }
    }
}
