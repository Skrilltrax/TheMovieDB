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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.Utils
import me.skrilltrax.themoviedb.adapter.CastCrewAdapter
import me.skrilltrax.themoviedb.adapter.MovieGenreAdapter
import me.skrilltrax.themoviedb.constants.CastCrewAdapterType
import me.skrilltrax.themoviedb.databinding.FragmentMovieDetailBinding
import me.skrilltrax.themoviedb.model.movie.credits.CastItem
import me.skrilltrax.themoviedb.model.movie.credits.CrewItem
import me.skrilltrax.themoviedb.model.movie.detail.GenresItem
import me.skrilltrax.themoviedb.ui.BaseFragment
import timber.log.Timber


class MovieDetailFragment : BaseFragment() {

    private val viewModel: MovieDetailViewModel by viewModels(factoryProducer = { SavedStateVMFactory(this) })

    private lateinit var movieId: String
    private lateinit var binding: FragmentMovieDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Utils.makeFullScreen(activity!!)
        Utils.setStatusBarColor(activity!!, Color.TRANSPARENT)
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
        setupObserves(viewLifecycleOwner)
        setupRecyclerView()
        viewModel.movieId.postValue(movieId)
        viewModel.fetchMovieDetails()
        viewModel.fetchCastAndCrew()
    }

    private fun observeScroll(view: View) {
        var oldScrollY = 0F
            view.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = view.scrollY.toFloat()
            if (scrollY <= 0) {
                Utils.setStatusBarColor(activity!!, Color.TRANSPARENT)
                oldScrollY = 0F
            } else if (scrollY > 0 && scrollY <= binding.movieHeader.root.height) {
                if (Math.abs(scrollY - oldScrollY) > 30) {
                    oldScrollY = scrollY
                    Utils.setStatusBarColor(
                        activity!!,
                        Color.argb(((scrollY / binding.movieHeader.root.height) * 255).toInt(), 25, 27, 27)
                    )
                    Timber.d("scrollY : ${((scrollY / binding.movieHeader.root.height) * 255).toInt()}")
                }
            } else {
                Utils.setStatusBarColor(activity!!, Color.argb(255, 25, 27, 27))
                oldScrollY = binding.movieHeader.root.height.toFloat()
            }
        }
    }

    private fun setupObserves(viewLifecycleOwner: LifecycleOwner) {

        viewModel.movieDetail.observe(viewLifecycleOwner, Observer {
            binding.movieDetail = it
        })

        viewModel.genres.observe(viewLifecycleOwner, Observer<List<GenresItem>> {
            binding.genreRecyclerView.adapter = MovieGenreAdapter(it)
            (binding.genreRecyclerView.adapter as MovieGenreAdapter).notifyDataSetChanged()
        })

        viewModel.cast.observe(viewLifecycleOwner, Observer<List<CastItem>> {
            val castList: ArrayList<CastItem> = arrayListOf()
            for (castListItem in it) {
                if (castListItem.profilePath != null) {
                    castList.add(castListItem)
                }
            }
            binding.castRecyclerView.adapter = CastCrewAdapter(castList as List<CastItem>, CastCrewAdapterType.CAST)
            (binding.castRecyclerView.adapter as CastCrewAdapter).notifyDataSetChanged()
        })

        viewModel.crew.observe(viewLifecycleOwner, Observer<List<CrewItem>> {
            val crewList: ArrayList<CrewItem> = arrayListOf()
            for (crewListItem in it) {
                if (crewListItem.profilePath != null) {
                    crewList.add(crewListItem)
                }
            }
            binding.crewRecyclerView.adapter =
                CastCrewAdapter(crewList as List<CrewItem>, CastCrewAdapterType.CREW)
            (binding.crewRecyclerView.adapter as CastCrewAdapter).notifyDataSetChanged()
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it == false) {
                hideLoading()
            }
        })
    }

    private fun setupRecyclerView() {
        binding.genreRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MovieDetailFragment.context, RecyclerView.HORIZONTAL, false)
            adapter = MovieGenreAdapter(listOf())
        }
        binding.castRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MovieDetailFragment.context, RecyclerView.HORIZONTAL, false)
            adapter = CastCrewAdapter(listOf(), CastCrewAdapterType.CAST)
        }
        binding.crewRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MovieDetailFragment.context, RecyclerView.HORIZONTAL, false)
            adapter = CastCrewAdapter(listOf(), CastCrewAdapterType.CREW)

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