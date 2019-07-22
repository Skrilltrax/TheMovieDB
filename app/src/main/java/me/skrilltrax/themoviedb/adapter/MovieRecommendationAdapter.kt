package me.skrilltrax.themoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.skrilltrax.themoviedb.databinding.ItemMovieRecommendationBinding
import me.skrilltrax.themoviedb.interfaces.MovieListItemClickListener
import me.skrilltrax.themoviedb.model.movie.lists.MovieResultsItem

class MovieRecommendationAdapter(
    private val list: List<MovieResultsItem>,
    private val listener: MovieListItemClickListener
) : RecyclerView.Adapter<MovieRecommendationAdapter.MovieRecommendationViewHolder>() {

    private lateinit var binding: ItemMovieRecommendationBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRecommendationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemMovieRecommendationBinding.inflate(inflater)
        return MovieRecommendationViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MovieRecommendationViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class MovieRecommendationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movieResultsItem: MovieResultsItem) {
            binding.movieData = movieResultsItem
            itemView.setOnClickListener {
                listener.onMovieItemClick(movieResultsItem)
            }
        }

    }
}