package me.skrilltrax.themoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.skrilltrax.themoviedb.databinding.ItemMovieBinding
import me.skrilltrax.themoviedb.interfaces.MovieListItemClickListener
import me.skrilltrax.themoviedb.model.movie.lists.MovieResultsItem
import timber.log.Timber

class MovieListAdapter(private val movieList: List<MovieResultsItem>, val listener: MovieListItemClickListener) :
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    private lateinit var binding: ItemMovieBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemMovieBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movieResultsItem: MovieResultsItem) {
            Timber.d("InOnBind")
            Timber.d(movieResultsItem.title)
            itemView.setOnClickListener {
                listener.onMovieItemClick(movieResultsItem)
            }
            binding.movieData = movieResultsItem
        }
    }
}