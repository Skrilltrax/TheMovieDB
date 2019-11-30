package me.skrilltrax.themoviedb.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.utils.SystemLayoutUtils
import me.skrilltrax.themoviedb.adapter.CreditsAdapter
import me.skrilltrax.themoviedb.adapter.MovieGenreAdapter
import me.skrilltrax.themoviedb.constants.CreditsType
import me.skrilltrax.themoviedb.databinding.FragmentMovieDetailBinding
import me.skrilltrax.themoviedb.interfaces.MovieDetailItemClickListener
import me.skrilltrax.themoviedb.model.movie.credits.CastItem
import me.skrilltrax.themoviedb.model.movie.credits.CrewItem
import me.skrilltrax.themoviedb.model.movie.detail.GenresItem
import me.skrilltrax.themoviedb.model.movie.videos.VideoResultsItem
import timber.log.Timber
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.ViewTreeObserver
import android.widget.ScrollView
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import me.skrilltrax.themoviedb.adapter.MovieRecommendationAdapter
import me.skrilltrax.themoviedb.adapter.VideoAdapter
import me.skrilltrax.themoviedb.interfaces.MovieListItemClickListener
import me.skrilltrax.themoviedb.model.movie.list.MovieResultsItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : Fragment(), MovieDetailItemClickListener, MovieListItemClickListener {

    private val movieDetailViewModel: MovieDetailViewModel by viewModel()
    private val movieDetailActivity by lazy { requireActivity() as MovieDetailActivity }
    private var movieId: MutableLiveData<String> = MutableLiveData("")
    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var scrollChangedListener: ViewTreeObserver.OnScrollChangedListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        SystemLayoutUtils.makeFullScreenHideNavigation(movieDetailActivity)
        arguments?.let { movieId.postValue(it.getString("movie_id", "")) }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(view) { rootView, insets ->
            rootView.updatePadding(0, 0, 0, insets.systemWindowInsetBottom)
            insets
        }
        movieDetailActivity.showLoading()
        observeScroll(view)
        setupObservers(viewLifecycleOwner)
    }

    private fun observeScroll(view: View) {
        scrollChangedListener = SystemLayoutUtils.setStatusBarTint(movieDetailActivity, view, binding.movieHeader.root)
        view.viewTreeObserver.addOnScrollChangedListener(scrollChangedListener)
    }

    private fun setupObservers(viewLifecycleOwner: LifecycleOwner) {
        movieId.observe(viewLifecycleOwner, Observer {
            movieDetailViewModel.movieId.postValue(it)
        })

        movieDetailViewModel.movieId.observe(viewLifecycleOwner, Observer {
            movieDetailViewModel.fetchMovieDetails()
            movieDetailViewModel.fetchCastAndCrew()
            movieDetailViewModel.fetchVideos()
            movieDetailViewModel.fetchRecommendations()
        })

        movieDetailViewModel.movieDetail.observe(viewLifecycleOwner, Observer {
            binding.movieDetail = it
        })

        movieDetailViewModel.genres.observe(viewLifecycleOwner, Observer<List<GenresItem>> {
            binding.genreAdapter = MovieGenreAdapter(it)
        })

        movieDetailViewModel.cast.observe(viewLifecycleOwner, Observer<List<CastItem>> {
            val castList: ArrayList<CastItem> = arrayListOf()
            for (castListItem in it) {
                if (castListItem.profilePath != null) { castList.add(castListItem) }
            }
            binding.castAdapter = CreditsAdapter(castList as List<CastItem>, CreditsType.CAST)
        })

        movieDetailViewModel.crew.observe(viewLifecycleOwner, Observer<List<CrewItem>> {
            val crewList: ArrayList<CrewItem> = arrayListOf()
            for (crewListItem in it) {
                if (crewListItem.profilePath != null) { crewList.add(crewListItem) }
            }
            binding.crewAdapter =
                CreditsAdapter(crewList as List<CrewItem>, CreditsType.CREW)
        })

        movieDetailViewModel.videos.observe(viewLifecycleOwner, Observer {
            Timber.d("trailers : ${it.size}")
            binding.videoAdapter = VideoAdapter(it, this)
        })

        movieDetailViewModel.recommendations.observe(viewLifecycleOwner, Observer {
            binding.recommendationAdapter = MovieRecommendationAdapter(it, this)
        })

        movieDetailViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it == false) movieDetailActivity.hideLoading()
        })
    }

    override fun onVideoItemClick(videoResultsItem: VideoResultsItem) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${videoResultsItem.key}"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=${videoResultsItem.key}")
        )
        try {
            requireContext().startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            requireContext().startActivity(webIntent)
        }
    }

    override fun onMovieItemClick(movieResultsItem: MovieResultsItem) {
        movieDetailActivity.showLoading()
        movieId.postValue(movieResultsItem.id.toString())
        (binding.root as ScrollView).fullScroll(ScrollView.FOCUS_UP)
    }

    override fun onResume() {
        super.onResume()
        view?.viewTreeObserver?.addOnScrollChangedListener(scrollChangedListener)
    }

    override fun onPause() {
        view?.viewTreeObserver?.removeOnScrollChangedListener(scrollChangedListener)
        movieDetailActivity.dialog?.let {
            if (it.isShowing) { it.hide() }
            it.dismiss()
        }
        super.onPause()
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