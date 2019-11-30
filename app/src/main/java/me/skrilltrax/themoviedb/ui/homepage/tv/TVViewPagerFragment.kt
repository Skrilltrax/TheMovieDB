package me.skrilltrax.themoviedb.ui.homepage.tv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import me.skrilltrax.themoviedb.adapter.MovieListAdapter
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.constants.Tabs
import me.skrilltrax.themoviedb.databinding.FragmentCommonViewpagerBinding
import me.skrilltrax.themoviedb.interfaces.MovieListItemClickListener
import me.skrilltrax.themoviedb.model.movie.list.MovieResultsItem
import me.skrilltrax.themoviedb.ui.moviedetail.MovieDetailActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class TVViewPagerFragment : Fragment(), MovieListItemClickListener {

    private val tvListViewModel by sharedViewModel<TVListViewModel>()

    private lateinit var binding: FragmentCommonViewpagerBinding
    private var fragmentType: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val args = arguments
        if (args != null) {
            fragmentType = args.getInt("fragmentType", 0)
            Timber.d(fragmentType.toString())
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_common_viewpager, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("Config Changed")
        setupObservers(viewLifecycleOwner, fragmentType ?: 0)
        getMovies(fragmentType ?: 0)
    }

    private fun setupObservers(viewLifecycleOwner: LifecycleOwner, position: Int) {
        when (position) {
            Tabs.TAB_POPULAR.tabId -> tvListViewModel.popularMovieList.observe(viewLifecycleOwner, Observer {
                binding.movieListAdapter = MovieListAdapter(it, this)
            })
            Tabs.TAB_PLAYING.tabId -> tvListViewModel.playingMovieList.observe(viewLifecycleOwner, Observer {
                binding.movieListAdapter = MovieListAdapter(it, this)
            })
            Tabs.TAB_UPCOMING.tabId -> tvListViewModel.upcomingMovieList.observe(viewLifecycleOwner, Observer {
                binding.movieListAdapter = MovieListAdapter(it, this)
            })
            Tabs.TAB_TOP_RATED.tabId -> tvListViewModel.topRatedMovieList.observe(viewLifecycleOwner, Observer {
                binding.movieListAdapter = MovieListAdapter(it, this)
            })
        }
    }

    private fun getMovies(position: Int) {
        when (position) {
            Tabs.TAB_POPULAR.tabId -> tvListViewModel.fetchPopularMovieList()
            Tabs.TAB_PLAYING.tabId -> tvListViewModel.fetchPlayingMovieList()
            Tabs.TAB_UPCOMING.tabId -> tvListViewModel.fetchUpcomingMovieList()
            Tabs.TAB_TOP_RATED.tabId -> tvListViewModel.fetchTopRatedMovieList()
        }
    }

    override fun onMovieItemClick(tvResultsItem: MovieResultsItem) {
        val intent = Intent(this.context, MovieDetailActivity::class.java)
        intent.putExtra("movie_id", tvResultsItem.id.toString())
        startActivity(intent)
    }

    companion object {
        fun newInstance(fragmentType: Int): TVViewPagerFragment {
            val bundle = Bundle()
            val commonViewPagerFragment =
                TVViewPagerFragment()

            bundle.putInt("fragmentType", fragmentType)
            commonViewPagerFragment.arguments = bundle
            return commonViewPagerFragment
        }
    }
}