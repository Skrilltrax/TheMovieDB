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
import me.skrilltrax.themoviedb.interfaces.OnItemClickListener
import me.skrilltrax.themoviedb.model.movie.lists.MovieResultsItem
import me.zhanghai.android.materialratingbar.MaterialRatingBar
import timber.log.Timber

class MovieListAdapter(private val movieList: List<MovieResultsItem>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieName: TextView = itemView.findViewById(R.id.movieTitle)
        private val movieImage: ImageView = itemView.findViewById(R.id.movieImage)
        private val ratingsBar: MaterialRatingBar = itemView.findViewById(R.id.ratingBar)
        private val ratingsText: TextView = itemView.findViewById(R.id.ratingText)
        private val releaseDate: TextView = itemView.findViewById(R.id.releaseDate)

        fun bind(movieResultsItem: MovieResultsItem) {
            Timber.d("InOnBind")
            Timber.d(movieResultsItem.title)
            itemView.setOnClickListener {
                listener.onMovieItemClick(movieResultsItem)
            }
            movieName.text = movieResultsItem.title
            releaseDate.text = movieResultsItem.releaseDate
            ratingsText.text = movieResultsItem.voteAverage.toString()
            ratingsBar.rating = (movieResultsItem.voteAverage!!.toFloat() / 2)
            Glide.with(itemView)
                .load(Constants.POSTER_W500_IMAGE_PATH + movieResultsItem.posterPath)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(movieImage)
        }
    }
}