package me.skrilltrax.themoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.databinding.ItemListTvBinding
import me.skrilltrax.themoviedb.interfaces.ListItemClickListener
import me.skrilltrax.themoviedb.model.list.tv.TVListResultItem
import me.skrilltrax.themoviedb.utils.setPosterImage

class TVListAdapter(
    private val list: List<TVListResultItem>,
    val listener: ListItemClickListener
) : RecyclerView.Adapter<TVListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListTvBinding.inflate(inflater, parent, false)
        return ListViewHolder(binding, binding.root)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ListViewHolder(private val binding: ItemListTvBinding, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(resultsItem: TVListResultItem) {

            with(binding) {
                val voteAverage: Float = resultsItem.voteAverage?.toFloat() ?: 0.0f
                title.text = resultsItem.name
                resultsItem.posterPath?.let { image.setPosterImage(it) }
                releaseDate.text = itemView.context.resources.getString(
                    R.string.release_date_s,
                    resultsItem.firstAirDate
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
