package me.skrilltrax.themoviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.constants.Constants
import me.skrilltrax.themoviedb.databinding.ItemMovieBinding
import me.skrilltrax.themoviedb.interfaces.OnItemClickListener
import me.skrilltrax.themoviedb.model.movie.lists.MovieResultsItem
import me.zhanghai.android.materialratingbar.MaterialRatingBar
import timber.log.Timber

class MovieListAdapter(private val movieList: List<MovieResultsItem>, val listener: OnItemClickListener) :
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