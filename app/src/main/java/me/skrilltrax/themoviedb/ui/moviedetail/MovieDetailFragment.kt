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
import me.skrilltrax.themoviedb.model.list.ListResultItem
import me.skrilltrax.themoviedb.model.videos.VideoResultsItem
import me.skrilltrax.themoviedb.utils.SystemLayoutUtils.makeFullScreenHideNavigation
import me.skrilltrax.themoviedb.utils.SystemLayoutUtils.setStatusBarTint
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MovieDetailFragment : Fragment(), MovieDetailItemClickListener, ListItemClickListener {

    private val movieDetailViewModel: MovieDetailViewModel by viewModel()
    private val movieDetailActivity by lazy { requireActivity() as MovieDetailActivity }
    private var movieId: MutableLiveData<String> = MutableLiveData("")
    private lateinit var movieStack: Stack<String>
    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var scrollChangedListener: ViewTreeObserver.OnScrollChangedListener
    private lateinit var callback: OnBackPressedCallback

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
            //            binding.movieDetail = it
        })

        movieDetailViewModel.genres.observe(viewLifecycleOwner, Observer {
            binding.genreRecyclerView.adapter = GenreAdapter(it)
        })

        movieDetailViewModel.cast.observe(viewLifecycleOwner, Observer {
            val castList: ArrayList<CastItem> = arrayListOf()
            for (castListItem in it) {
                if (castListItem.profilePath != null) {
                    castList.add(castListItem)
                }
            }
            binding.castRecyclerView.adapter =
                CreditsAdapter(castList as List<CastItem>, CreditsType.CAST)
        })

        movieDetailViewModel.crew.observe(viewLifecycleOwner, Observer {
            val crewList: ArrayList<CrewItem> = arrayListOf()
            for (crewListItem in it) {
                if (crewListItem.profilePath != null) {
                    crewList.add(crewListItem)
                }
            }
            binding.crewRecyclerView.adapter =
                CreditsAdapter(crewList as List<CrewItem>, CreditsType.CREW)
        })

        movieDetailViewModel.videos.observe(viewLifecycleOwner, Observer {
            Timber.d("trailers : ${it.size}")
            binding.videosRecyclerView.adapter = VideoAdapter(it, this)
        })

        movieDetailViewModel.recommendations.observe(viewLifecycleOwner, Observer {
            binding.recommendedRecyclerView.adapter = RecommendationAdapter(it, this)
        })

        movieDetailViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it == false) lifecycleScope.launch(Dispatchers.IO) {
                delay(300)
                withContext(Dispatchers.Main) {
                    movieDetailActivity.hideLoading()
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

    override fun onItemClick(resultsItem: ListResultItem) {
        movieDetailActivity.showLoading()
        movieId.postValue(resultsItem.id.toString())
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
