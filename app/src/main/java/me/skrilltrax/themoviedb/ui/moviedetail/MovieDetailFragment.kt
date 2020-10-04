package me.skrilltrax.themoviedb.ui.moviedetail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ScrollView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import java.util.Stack
import kotlin.collections.ArrayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.adapter.CreditsAdapter
import me.skrilltrax.themoviedb.adapter.GenreAdapter
import me.skrilltrax.themoviedb.adapter.RecommendationAdapter
import me.skrilltrax.themoviedb.adapter.VideoAdapter
import me.skrilltrax.themoviedb.constants.CreditsType
import me.skrilltrax.themoviedb.databinding.FragmentMovieDetailBinding
import me.skrilltrax.themoviedb.interfaces.ListItemClickListener
import me.skrilltrax.themoviedb.model.credits.CastItem
import me.skrilltrax.themoviedb.model.credits.CrewItem
import me.skrilltrax.themoviedb.model.list.movie.MovieListResultItem
import me.skrilltrax.themoviedb.model.videos.VideoResultsItem
import me.skrilltrax.themoviedb.utils.SystemLayoutUtils.makeFullScreenHideNavigation
import me.skrilltrax.themoviedb.utils.SystemLayoutUtils.setStatusBarTint
import me.skrilltrax.themoviedb.utils.gone
import me.skrilltrax.themoviedb.utils.setHeroImage
import me.skrilltrax.themoviedb.utils.setPosterImage
import me.skrilltrax.themoviedb.utils.visible

class MovieDetailFragment : Fragment(), ListItemClickListener {

    private val movieDetailViewModel by activityViewModels<MovieDetailViewModel>()
    private val movieDetailActivity by lazy { requireActivity() as MovieDetailActivity }

    private lateinit var movieStack: Stack<String>
    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var scrollChangedListener: ViewTreeObserver.OnScrollChangedListener
    private lateinit var callback: OnBackPressedCallback

    private var movieId: MutableLiveData<String> = MutableLiveData("")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        makeFullScreenHideNavigation()
        arguments?.let { movieId.postValue(it.getString("movie_id", "")) }
        movieStack = Stack()
        movieStack.push(movieId.value)
        callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            movieId.postValue(movieStack.pop())
            isEnabled = movieStack.size > 1
            movieDetailActivity.showLoading()
            binding.root.fullScroll(ScrollView.FOCUS_UP)
        }
        callback.isEnabled = false
        binding = FragmentMovieDetailBinding.inflate(layoutInflater)
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
        scrollChangedListener = setStatusBarTint(view, binding.movieHeader.root)
        view.viewTreeObserver.addOnScrollChangedListener(scrollChangedListener)
    }

    private fun setupObservers(viewLifecycleOwner: LifecycleOwner) {
        movieId.observe(viewLifecycleOwner) {
            movieDetailViewModel.movieId.postValue(it)
        }

        movieDetailViewModel.movieId.observe(viewLifecycleOwner) {
//            movieDetailViewModel.fetchMovieDetails()
//            movieDetailViewModel.fetchCastAndCrew()
//            movieDetailViewModel.fetchVideos()
//            movieDetailViewModel.fetchRecommendations()
            movieDetailViewModel.fetchMovieDetailsWithExtras()
        }

        movieDetailViewModel.movieDetail.observe(viewLifecycleOwner) {
            with(binding) {
                if (it.overview.isNullOrEmpty()) {
                    synopsis.gone()
                    titleSynopsis.gone()
                } else {
                    synopsis.visible()
                    titleSynopsis.visible()
                }

                val voteAverage: Float = it.voteAverage?.toFloat() ?: 0.0f
                if (voteAverage == 0.0f) {
                    movieHeader.ratingText.text = "N/A"
                } else {
                    movieHeader.ratingText.text = voteAverage.toString()
                    movieHeader.ratingBar.rating = voteAverage / 2
                }

                synopsis.text = it.overview
                movieHeader.movieTitle.text = it.title
                movieHeader.releaseDate.text =
                    resources.getString(R.string.release_date_s, it.releaseDate)
                it.backdropPath?.let { url -> movieHeader.movieBackground.setHeroImage(url) }
                it.posterPath?.let { url -> movieHeader.moviePoster.setPosterImage(url) }
            }
        }

        movieDetailViewModel.genres.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.genreRecyclerView.gone()
                return@observe
            }
            binding.genreRecyclerView.visible()

            binding.genreRecyclerView.adapter = GenreAdapter(it)
        }

        movieDetailViewModel.cast.observe(viewLifecycleOwner) {
            val castList: ArrayList<CastItem> = arrayListOf()
            for (castListItem in it) {
                if (castListItem.profilePath != null) {
                    castList.add(castListItem)
                }
            }

            if (castList.isEmpty()) {
                binding.castRecyclerView.gone()
                binding.titleCast.gone()
                return@observe
            }
            binding.castRecyclerView.visible()
            binding.titleCast.visible()

            binding.castRecyclerView.adapter =
                CreditsAdapter(castList as List<CastItem>, CreditsType.CAST)
        }

        movieDetailViewModel.crew.observe(viewLifecycleOwner) {
            val crewList: ArrayList<CrewItem> = arrayListOf()
            for (crewListItem in it) {
                if (crewListItem.profilePath != null) {
                    crewList.add(crewListItem)
                }
            }

            if (crewList.isEmpty()) {
                binding.crewRecyclerView.gone()
                binding.titleCrew.gone()
                return@observe
            }
            binding.crewRecyclerView.visible()
            binding.titleCrew.visible()

            binding.crewRecyclerView.adapter =
                CreditsAdapter(crewList as List<CrewItem>, CreditsType.CREW)
        }

        movieDetailViewModel.videos.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.videosRecyclerView.gone()
                binding.titleVideos.gone()
                return@observe
            }
            binding.videosRecyclerView.visible()
            binding.titleVideos.visible()
            binding.videosRecyclerView.adapter = VideoAdapter(it) { videoResultItem ->
                onVideoItemClick(videoResultItem)
            }
        }

        movieDetailViewModel.recommendations.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                binding.recommendedRecyclerView.gone()
                binding.titleRecommended.gone()
                return@observe
            }
            binding.recommendedRecyclerView.visible()
            binding.titleRecommended.visible()

            val urlIdList: ArrayList<Pair<String, String>> = ArrayList()
            list.forEach {
                urlIdList.add(Pair(it.posterPath ?: "", it.id.toString()))
            }
            binding.recommendedRecyclerView.adapter = RecommendationAdapter(urlIdList, this)
        }

        movieDetailViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it == false) lifecycleScope.launch(Dispatchers.IO) {
                delay(300)
                withContext(Dispatchers.Main) {
                    movieDetailActivity.hideLoading()
                }
            }
        }
    }

    private fun onVideoItemClick(videoResultsItem: VideoResultsItem) {
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

    override fun onItemClick(resultsItem: MovieListResultItem) {
        this.onItemClick(resultsItem.id.toString())
    }

    override fun onItemClick(id: String) {
        movieDetailActivity.showLoading()
        movieId.postValue(id)
        binding.root.fullScroll(ScrollView.FOCUS_UP)
        movieStack.push(movieId.value)
        callback.isEnabled = true
    }

    override fun onResume() {
        super.onResume()
        view?.viewTreeObserver?.addOnScrollChangedListener(scrollChangedListener)
    }

    override fun onPause() {
        view?.viewTreeObserver?.removeOnScrollChangedListener(scrollChangedListener)
        movieDetailActivity.dialog?.let {
            if (it.isShowing) {
                it.hide()
            }
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
