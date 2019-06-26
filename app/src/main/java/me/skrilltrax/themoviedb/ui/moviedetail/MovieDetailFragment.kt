package me.skrilltrax.themoviedb.ui.moviedetail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.skrilltrax.themoviedb.BuildConfig
import me.skrilltrax.themoviedb.constants.Constants
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.adapter.CastCrewAdapter
import me.skrilltrax.themoviedb.adapter.MovieGenreAdapter
import me.skrilltrax.themoviedb.constants.CastCrewAdapterType
import me.skrilltrax.themoviedb.model.movie.credits.CastItem
import me.skrilltrax.themoviedb.model.movie.credits.CrewItem
import me.skrilltrax.themoviedb.model.movie.detail.GenresItem
import me.skrilltrax.themoviedb.model.movie.detail.MovieDetailResponse
import me.skrilltrax.themoviedb.network.api.MovieApiInterface
import me.skrilltrax.themoviedb.ui.BaseFragment
import me.zhanghai.android.materialratingbar.MaterialRatingBar
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber


class MovieDetailFragment : BaseFragment() {

    private lateinit var movieId: String
    private lateinit var movieBackground: ImageView
    private lateinit var moviePoster: ImageView
    private lateinit var synopsis: TextView
    private lateinit var movieTitle: TextView
    private lateinit var releaseDate: TextView
    private lateinit var ratingText: TextView
    private lateinit var ratingBar: MaterialRatingBar
    private lateinit var genreRecyclerView: RecyclerView
    private lateinit var castRecyclerView: RecyclerView
    private lateinit var crewRecyclerView: RecyclerView
    private lateinit var movieHeader: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (arguments != null) {
            movieId = arguments!!.getString("movie_id", "")
        }
        activity?.window?.statusBarColor = Color.TRANSPARENT
        activity?.window?.decorView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
        return if (movieId.isEmpty()) {
            super.onCreateView(inflater, container, savedInstanceState)
        } else {
            return inflater.inflate(R.layout.fragment_movie_detail, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading()
        findViews(view)
        setupRecyclerView()
        setupUI()
        view.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = view.scrollY
            if (scrollY > movieHeader.height.times(0.5)) {
                activity?.window?.statusBarColor = ContextCompat.getColor(this.context!!, R.color.background)
            } else {
                activity?.window?.statusBarColor = Color.TRANSPARENT
            }
        }
    }

    override fun onPause() {
        super.onPause()
        dialog?.dismiss()
    }

    private fun findViews(view: View) {
        synopsis = view.findViewById(R.id.synopsis)
        genreRecyclerView = view.findViewById(R.id.recycler_view_genre)
        castRecyclerView = view.findViewById(R.id.cast_recycler_view)
        crewRecyclerView = view.findViewById(R.id.crew_recycler_view)
        ratingBar = view.findViewById(R.id.ratingBar)
        ratingText = view.findViewById(R.id.ratingText)
        movieTitle = view.findViewById(R.id.movieTitle)
        releaseDate = view.findViewById(R.id.releaseDate)
        moviePoster = view.findViewById(R.id.moviePoster)
        movieBackground = view.findViewById(R.id.movieBackground)
        movieHeader = view.findViewById(R.id.movie_header)
    }

    private fun setupRecyclerView() {
        genreRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MovieDetailFragment.context, RecyclerView.HORIZONTAL, false)
            adapter = MovieGenreAdapter(listOf())
        }
        castRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MovieDetailFragment.context, RecyclerView.HORIZONTAL, false)
            adapter = CastCrewAdapter(listOf(), CastCrewAdapterType.CAST)
        }
        crewRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MovieDetailFragment.context, RecyclerView.HORIZONTAL, false)
            adapter = CastCrewAdapter(listOf(), CastCrewAdapterType.CREW)

        }
    }

    private fun setupUI() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val movieDetailResponse = MovieApiInterface.getClient().getMovieDetails(movieId, BuildConfig.API_KEY)
                if (movieDetailResponse != null) {
                    if (movieDetailResponse.isSuccessful) {
                        loadCastCrew()
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
            .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(moviePoster)
        Glide.with(this@MovieDetailFragment.context!!)
            .load(Constants.POSTER_W500_IMAGE_PATH + movieDetailResponse.body()?.backdropPath)
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

    private fun loadCastCrew() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val creditsResponse = MovieApiInterface.getClient().getMovieCredits(movieId, BuildConfig.API_KEY)
                if (creditsResponse.isSuccessful) {
                    withContext(Dispatchers.Main) {


                        castRecyclerView.adapter =
                            CastCrewAdapter(creditsResponse.body()?.cast as List<CastItem>, CastCrewAdapterType.CAST)
                        (castRecyclerView.adapter as CastCrewAdapter).notifyDataSetChanged()

                        crewRecyclerView.adapter =
                            CastCrewAdapter(creditsResponse.body()?.crew as List<CrewItem>, CastCrewAdapterType.CREW)
                        (crewRecyclerView.adapter as CastCrewAdapter).notifyDataSetChanged()
                        hideLoading()
                    }
                }
            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
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