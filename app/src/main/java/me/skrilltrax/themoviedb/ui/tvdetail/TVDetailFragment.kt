package me.skrilltrax.themoviedb.ui.tvdetail

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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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
import me.skrilltrax.themoviedb.interfaces.MovieDetailItemClickListener
import me.skrilltrax.themoviedb.model.credits.CastItem
import me.skrilltrax.themoviedb.model.credits.CrewItem
import me.skrilltrax.themoviedb.model.list.tv.TVListResultItem
import me.skrilltrax.themoviedb.model.videos.VideoResultsItem
import me.skrilltrax.themoviedb.utils.SystemLayoutUtils.makeFullScreenHideNavigation
import me.skrilltrax.themoviedb.utils.SystemLayoutUtils.setStatusBarTint
import me.skrilltrax.themoviedb.utils.gone
import me.skrilltrax.themoviedb.utils.setHeroImage
import me.skrilltrax.themoviedb.utils.setPosterImage
import me.skrilltrax.themoviedb.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class TVDetailFragment : Fragment(), MovieDetailItemClickListener, ListItemClickListener {

    private val tvDetailViewModel: TVDetailViewModel by viewModel()
    private val tvDetailActivity by lazy { requireActivity() as TVDetailActivity }
    private var showId: MutableLiveData<String> = MutableLiveData("")
    private lateinit var showStack: Stack<String>
    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var scrollChangedListener: ViewTreeObserver.OnScrollChangedListener
    private lateinit var callback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        makeFullScreenHideNavigation()
        arguments?.let { showId.postValue(it.getString("show_id", "")) }
        showStack = Stack()
        showStack.push(showId.value)
        callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            showId.postValue(showStack.pop())
            isEnabled = showStack.size > 1
            tvDetailActivity.showLoading()
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
        tvDetailActivity.showLoading()
        observeScroll(view)
        setupObservers(viewLifecycleOwner)
    }

    private fun observeScroll(view: View) {
        scrollChangedListener = setStatusBarTint(view, binding.movieHeader.root)
        view.viewTreeObserver.addOnScrollChangedListener(scrollChangedListener)
    }

    private fun setupObservers(viewLifecycleOwner: LifecycleOwner) {
        showId.observe(viewLifecycleOwner, Observer {
            tvDetailViewModel.showId.postValue(it)
        })

        tvDetailViewModel.showId.observe(viewLifecycleOwner, Observer {
            tvDetailViewModel.fetchShowDetails()
            tvDetailViewModel.fetchCastAndCrew()
            tvDetailViewModel.fetchVideos()
            tvDetailViewModel.fetchRecommendations()
        })

        tvDetailViewModel.tvDetail.observe(viewLifecycleOwner, Observer {
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
                movieHeader.movieTitle.text = it.name
                movieHeader.releaseDate.text =
                    resources.getString(R.string.release_date_s, it.firstAirDate)
                it.backdropPath?.let { url -> movieHeader.movieBackground.setHeroImage(url) }
                it.posterPath?.let { url -> movieHeader.moviePoster.setPosterImage(url) }
            }
        })

        tvDetailViewModel.genres.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.genreRecyclerView.gone()
                return@Observer
            }
            binding.genreRecyclerView.visible()

            binding.genreRecyclerView.adapter = GenreAdapter(it)
        })

        tvDetailViewModel.cast.observe(viewLifecycleOwner, Observer {
            val castList: ArrayList<CastItem> = arrayListOf()
            for (castListItem in it) {
                if (castListItem.profilePath != null) {
                    castList.add(castListItem)
                }
            }

            if (castList.isEmpty()) {
                binding.castRecyclerView.gone()
                binding.titleCast.gone()
                return@Observer
            }
            binding.castRecyclerView.visible()
            binding.titleCast.visible()
            binding.castRecyclerView.adapter =
                CreditsAdapter(castList as List<CastItem>, CreditsType.CAST)
        })

        tvDetailViewModel.crew.observe(viewLifecycleOwner, Observer {
            val crewList: ArrayList<CrewItem> = arrayListOf()
            for (crewListItem in it) {
                if (crewListItem.profilePath != null) {
                    crewList.add(crewListItem)
                }
            }

            if (crewList.isEmpty()) {
                binding.crewRecyclerView.gone()
                binding.titleCrew.gone()
                return@Observer
            }
            binding.crewRecyclerView.visible()
            binding.titleCrew.visible()

            binding.crewRecyclerView.adapter =
                CreditsAdapter(crewList as List<CrewItem>, CreditsType.CREW)
        })

        tvDetailViewModel.videos.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.videosRecyclerView.gone()
                binding.titleVideos.gone()
                return@Observer
            }
            binding.videosRecyclerView.visible()
            binding.titleVideos.visible()

            binding.videosRecyclerView.adapter = VideoAdapter(it, this)
        })

        tvDetailViewModel.recommendations.observe(viewLifecycleOwner, Observer { list ->
            if (list.isEmpty()) {
                binding.recommendedRecyclerView.gone()
                binding.titleRecommended.gone()
                return@Observer
            }
            binding.recommendedRecyclerView.visible()
            binding.titleRecommended.visible()

            val urlIdList: ArrayList<Pair<String, String>> = ArrayList()
            list.forEach {
                urlIdList.add(Pair(it.posterPath ?: "", it.id.toString()))
            }
            binding.recommendedRecyclerView.adapter = RecommendationAdapter(urlIdList, this)
        })

        tvDetailViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it == false) lifecycleScope.launch(Dispatchers.IO) {
                delay(300)
                withContext(Dispatchers.Main) {
                    tvDetailActivity.hideLoading()
                }
            }
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

    override fun onItemClick(resultsItem: TVListResultItem) {
        this.onItemClick(resultsItem.id.toString())
    }

    override fun onItemClick(id: String) {
        tvDetailActivity.showLoading()
        showId.postValue(id)
        binding.root.fullScroll(ScrollView.FOCUS_UP)
        showStack.push(showId.value)
        callback.isEnabled = true
    }

    override fun onResume() {
        super.onResume()
        view?.viewTreeObserver?.addOnScrollChangedListener(scrollChangedListener)
    }

    override fun onPause() {
        view?.viewTreeObserver?.removeOnScrollChangedListener(scrollChangedListener)
        tvDetailActivity.dialog?.let {
            if (it.isShowing) {
                it.hide()
            }
            it.dismiss()
        }
        super.onPause()
    }

    companion object {
        fun newInstance(id: String): TVDetailFragment {
            return TVDetailFragment().also {
                val bundle = Bundle()
                bundle.putString("show_id", id)
                it.arguments = bundle
            }
        }
    }
}
