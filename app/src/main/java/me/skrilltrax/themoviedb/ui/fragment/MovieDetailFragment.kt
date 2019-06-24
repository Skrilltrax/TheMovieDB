package me.skrilltrax.themoviedb.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.skrilltrax.themoviedb.BuildConfig
import me.skrilltrax.themoviedb.Constants
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.adapter.MovieGenreAdapter
import me.skrilltrax.themoviedb.model.movie.detail.GenresItem
import me.skrilltrax.themoviedb.model.movie.detail.MovieDetailResponse
import me.skrilltrax.themoviedb.network.api.MovieApiInterface
import me.zhanghai.android.materialratingbar.MaterialRatingBar
import retrofit2.HttpException
import retrofit2.Response



class MovieDetailFragment : Fragment() {

    private lateinit var movieId: String
    private lateinit var genreRecyclerView: RecyclerView
    private lateinit var synopsis: TextView
    private lateinit var movieBackground: ImageView
    private lateinit var moviePoster: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var releaseDate: TextView
    private lateinit var ratingBar: MaterialRatingBar
    private lateinit var ratingText: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (arguments != null) {
            movieId = arguments!!.getString("movie_id", "")
        }
        return if (movieId.isEmpty()) {
            super.onCreateView(inflater, container, savedInstanceState)
        } else {
            return inflater.inflate(R.layout.fragment_movie_detail, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)
        setupRecyclerView()
        setupUI()
    }

    private fun findViews(view: View) {
        synopsis = view.findViewById(R.id.synopsis)
        genreRecyclerView = view.findViewById(R.id.recycler_view_genre)
        ratingBar = view.findViewById(R.id.ratingBar)
        ratingText = view.findViewById(R.id.ratingText)
        movieTitle = view.findViewById(R.id.movieTitle)
        releaseDate = view.findViewById(R.id.releaseDate)
        moviePoster = view.findViewById(R.id.moviePoster)
        movieBackground = view.findViewById(R.id.movieBackground)
    }

    private fun setupRecyclerView() {
        genreRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MovieDetailFragment.context, RecyclerView.HORIZONTAL, false)
            adapter = MovieGenreAdapter(listOf())
        }
    }

    private fun setupUI() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val movieDetailResponse = MovieApiInterface.getClient().getMovieDetails(movieId, BuildConfig.API_KEY)
                if (movieDetailResponse != null) {
                    if (movieDetailResponse.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            loadImages(movieDetailResponse)
                            setupViews(movieDetailResponse)
                        }
                    }
                }
            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadImages(movieDetailResponse: Response<MovieDetailResponse>) {
        Glide.with(this@MovieDetailFragment.context!!)
            .load(Constants.POSTER_W500_IMAGE_PATH + movieDetailResponse.body()?.posterPath)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(moviePoster)
        Glide.with(this@MovieDetailFragment.context!!)
            .load(Constants.POSTER_ORIGINAL_IMAGE_PATH + movieDetailResponse.body()?.backdropPath)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(movieBackground)
    }

    private fun setupViews(movieDetailResponse: Response<MovieDetailResponse>) {
        movieTitle.text = movieDetailResponse.body()?.title
        movieTitle.isSelected = true
        synopsis.text = movieDetailResponse.body()?.overview
        ratingText.text = movieDetailResponse.body()?.voteAverage.toString()
        ratingBar.rating = (movieDetailResponse.body()?.voteAverage!!.toFloat() / 2)
        releaseDate.text = movieDetailResponse.body()?.releaseDate
        genreRecyclerView.adapter = MovieGenreAdapter(movieDetailResponse.body()?.genres as List<GenresItem>)
        (genreRecyclerView.adapter as MovieGenreAdapter).notifyDataSetChanged()
    }

    companion object {

        fun newInstance(id: String): MovieDetailFragment {
            return MovieDetailFragment().also {
                val bundle = Bundle()
                bundle.putString("movie_id", id)
                it.arguments = bundle
            }
        }
    }
}