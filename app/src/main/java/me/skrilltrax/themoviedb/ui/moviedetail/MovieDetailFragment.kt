package me.skrilltrax.themoviedb.ui.moviedetail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateVMFactory
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.utils.StatusBarUtils
import me.skrilltrax.themoviedb.adapter.CreditsAdapter
import me.skrilltrax.themoviedb.adapter.MovieGenreAdapter
import me.skrilltrax.themoviedb.constants.CreditsType
import me.skrilltrax.themoviedb.databinding.FragmentMovieDetailBinding
import me.skrilltrax.themoviedb.interfaces.MovieDetailItemClickListener
import me.skrilltrax.themoviedb.model.movie.credits.CastItem
import me.skrilltrax.themoviedb.model.movie.credits.CrewItem
import me.skrilltrax.themoviedb.model.movie.detail.GenresItem
import me.skrilltrax.themoviedb.model.movie.videos.VideoResultsItem
import me.skrilltrax.themoviedb.ui.BaseFragment
import timber.log.Timber
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import me.skrilltrax.themoviedb.adapter.VideoAdapter


class MovieDetailFragment : BaseFragment(), MovieDetailItemClickListener {

    private val viewModel: MovieDetailViewModel by viewModels(factoryProducer = { SavedStateVMFactory(this) })

    private lateinit var movieId: String
    private lateinit var binding: FragmentMovieDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        StatusBarUtils.makeFullScreen(activity!!)
        StatusBarUtils.setStatusBarColor(activity!!, Color.TRANSPARENT)
        if (arguments != null) {
            movieId = arguments!!.getString("movie_id", "")
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading()
        observeScroll(view)
        setupObservers(viewLifecycleOwner)
        viewModel.movieId.postValue(movieId)
        viewModel.fetchMovieDetails()
        viewModel.fetchCastAndCrew()
        viewModel.fetchVideos()
    }

    private fun observeScroll(view: View) {
        var oldScrollY = 0F
        view.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = view.scrollY.toFloat()
            if (scrollY <= 0) {
                StatusBarUtils.setStatusBarColor(activity!!, Color.TRANSPARENT)
                oldScrollY = 0F
            } else if (scrollY > 0 && scrollY <= binding.movieHeader.root.height) {
                if (Math.abs(scrollY - oldScrollY) > 30) {
                    oldScrollY = scrollY
                    StatusBarUtils.setStatusBarColor(
                        activity!!,
                        Color.argb(((scrollY / binding.movieHeader.root.height) * 255).toInt(), 25, 27, 27)
                    )
                    Timber.d("scrollY : ${((scrollY / binding.movieHeader.root.height) * 255).toInt()}")
                }
            } else {
                StatusBarUtils.setStatusBarColor(activity!!, Color.argb(255, 25, 27, 27))
                oldScrollY = binding.movieHeader.root.height.toFloat()
            }
        }
    }

    private fun setupObservers(viewLifecycleOwner: LifecycleOwner) {

        viewModel.movieDetail.observe(viewLifecycleOwner, Observer {
            binding.movieDetail = it
        })

        viewModel.genres.observe(viewLifecycleOwner, Observer<List<GenresItem>> {
            binding.genreAdapter = MovieGenreAdapter(it)
        })

        viewModel.cast.observe(viewLifecycleOwner, Observer<List<CastItem>> {
            val castList: ArrayList<CastItem> = arrayListOf()
            for (castListItem in it) {
                if (castListItem.profilePath != null) {
                    castList.add(castListItem)
                }
            }
            binding.castAdapter = CreditsAdapter(castList as List<CastItem>, CreditsType.CAST)
        })

        viewModel.crew.observe(viewLifecycleOwner, Observer<List<CrewItem>> {
            val crewList: ArrayList<CrewItem> = arrayListOf()
            for (crewListItem in it) {
                if (crewListItem.profilePath != null) {
                    crewList.add(crewListItem)
                }
            }
            binding.crewAdapter =
                CreditsAdapter(crewList as List<CrewItem>, CreditsType.CREW)
        })

        viewModel.videos.observe(viewLifecycleOwner, Observer {
            Timber.d("trailers : ${it.size}")
            binding.videoAdapter = VideoAdapter(it, this)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it == false) hideLoading()
        })
    }

    override fun onVideoItemClick(videoResultsItem: VideoResultsItem) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${videoResultsItem.key}"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=${videoResultsItem.key}")
        )
        try {
            context?.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            context?.startActivity(webIntent)
        }
    }

    override fun onPause() {
        super.onPause()
        dialog?.dismiss()
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