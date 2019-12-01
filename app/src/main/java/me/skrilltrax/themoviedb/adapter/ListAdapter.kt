package me.skrilltrax.themoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.skrilltrax.themoviedb.databinding.ItemListBinding
import me.skrilltrax.themoviedb.interfaces.ListItemClickListener
import me.skrilltrax.themoviedb.model.list.ListResultItem

class ListAdapter(private val list: List<ListResultItem>, val listener: ListItemClickListener, val isMovieSelected: Boolean) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private lateinit var binding: ItemListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemListBinding.inflate(inflater, parent, false)
        return ListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(resultsItem: ListResultItem) {
            itemView.setOnClickListener {
                listener.onItemClick(resultsItem)
            }
            binding.data = resultsItem
            binding.isMovie = isMovieSelected
        }
    }
}