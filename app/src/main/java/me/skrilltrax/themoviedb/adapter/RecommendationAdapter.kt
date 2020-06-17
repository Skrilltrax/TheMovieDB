package me.skrilltrax.themoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.skrilltrax.themoviedb.databinding.ItemRecommendationBinding
import me.skrilltrax.themoviedb.interfaces.ListItemClickListener
import me.skrilltrax.themoviedb.utils.setPosterImage

class RecommendationAdapter(
    private val list: List<Pair<String, String>>,
    private val listener: ListItemClickListener
) : RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecommendationBinding.inflate(inflater)
        return RecommendationViewHolder(binding, binding.root)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class RecommendationViewHolder(
        private val binding: ItemRecommendationBinding,
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Pair<String, String>) {
            binding.recommendationImage.setPosterImage(item.first)
            itemView.setOnClickListener {
                listener.onItemClick(item.second)
            }
        }
    }
}
