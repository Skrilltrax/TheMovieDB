package me.skrilltrax.themoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.skrilltrax.themoviedb.databinding.ItemMovieRecommendationBinding
import me.skrilltrax.themoviedb.interfaces.MovieListItemClickListener
import me.skrilltrax.themoviedb.model.list.ListResultItem

class RecommendationAdapter(
    private val list: List<ListResultItem>,
    private val listener: MovieListItemClickListener
) : RecyclerView.Adapter<RecommendationAdapter.MovieRecommendationViewHolder>() {

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

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MovieRecommendationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movieResultsItem: ListResultItem) {
            binding.movieData = movieResultsItem
            itemView.setOnClickListener {
                listener.onMovieItemClick(movieResultsItem)
            }
        }

    }
}