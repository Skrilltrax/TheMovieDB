package me.skrilltrax.themoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.skrilltrax.themoviedb.databinding.ItemGenreBinding
import me.skrilltrax.themoviedb.model.movie.detail.GenresItem

class GenreAdapter(val genreList: List<GenresItem>) : RecyclerView.Adapter<GenreAdapter.MovieGenreViewHolder>() {

    private lateinit var binding: ItemGenreBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGenreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemGenreBinding.inflate(inflater, parent, false)
        return MovieGenreViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MovieGenreViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MovieGenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var genreID: Int? = null
        fun bind(position: Int) {
            genreID = genreList[position].id!!
            binding.genreDetail = genreList[position]
        }
    }
}