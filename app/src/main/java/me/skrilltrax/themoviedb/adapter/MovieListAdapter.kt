package me.skrilltrax.themoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.databinding.ItemListMovieBinding
import me.skrilltrax.themoviedb.interfaces.ListItemClickListener
import me.skrilltrax.themoviedb.model.list.movie.MovieListResultItem
import me.skrilltrax.themoviedb.utils.setPosterImage

class MovieListAdapter(
    private val list: List<MovieListResultItem>,
    val listener: ListItemClickListener
) :
    RecyclerView.Adapter<MovieListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListMovieBinding.inflate(inflater, parent, false)
        return ListViewHolder(binding, binding.root)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ListViewHolder(private val binding: ItemListMovieBinding, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(resultsItem: MovieListResultItem) {

            with(binding) {
                val voteAverage: Float = resultsItem.voteAverage?.toFloat() ?: 0.0f
                title.text = resultsItem.title
                resultsItem.posterPath?.let { image.setPosterImage(it) }
                releaseDate.text = itemView.context.resources.getString(
                    R.string.release_date_s,
                    resultsItem.releaseDate
                )
                if (voteAverage == 0.0f) {
                    ratingText.text = "N/A"
                } else {
                    ratingText.text = voteAverage.toString()
                    ratingBar.rating = voteAverage / 2
                }
            }

            itemView.setOnClickListener {
                listener.onItemClick(resultsItem)
            }
        }
    }
}
