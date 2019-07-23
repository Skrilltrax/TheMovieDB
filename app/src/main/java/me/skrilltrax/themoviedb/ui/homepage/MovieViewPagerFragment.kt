package me.skrilltrax.themoviedb.ui.homepage

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
import me.skrilltrax.themoviedb.constants.MovieTabs
import me.skrilltrax.themoviedb.databinding.FragmentCommonViewpagerBinding
import me.skrilltrax.themoviedb.interfaces.MovieListItemClickListener
import me.skrilltrax.themoviedb.model.movie.lists.MovieResultsItem
import me.skrilltrax.themoviedb.ui.moviedetail.MovieDetailActivity
import timber.log.Timber

class MovieViewPagerFragment : Fragment(), MovieListItemClickListener {

    private lateinit var viewModel: MovieListViewModel

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
        viewModel = ViewModelProviders.of(activity!!).get(MovieListViewModel::class.java)
        setupObservers(viewLifecycleOwner, fragmentType ?: 0)
        getMovies(fragmentType ?: 0)
    }

    private fun setupObservers(viewLifecycleOwner: LifecycleOwner, position: Int) {
        when (position) {
            MovieTabs.TAB_POPULAR.tabId -> viewModel.popularMovieList.observe(viewLifecycleOwner, Observer {
                binding.movieListAdapter = MovieListAdapter(it, this)
            })
            MovieTabs.TAB_PLAYING.tabId -> viewModel.playingMovieList.observe(viewLifecycleOwner, Observer {
                binding.movieListAdapter = MovieListAdapter(it, this)
            })
            MovieTabs.TAB_UPCOMING.tabId -> viewModel.upcomingMovieList.observe(viewLifecycleOwner, Observer {
                binding.movieListAdapter = MovieListAdapter(it, this)
            })
            MovieTabs.TAB_TOP_RATED.tabId -> viewModel.topRatedMovieList.observe(viewLifecycleOwner, Observer {
                binding.movieListAdapter = MovieListAdapter(it, this)
            })
        }
    }

    private fun getMovies(position: Int) {
        when (position) {
            MovieTabs.TAB_POPULAR.tabId -> viewModel.fetchPopularMovieList()
            MovieTabs.TAB_PLAYING.tabId -> viewModel.fetchPlayingMovieList()
            MovieTabs.TAB_UPCOMING.tabId -> viewModel.fetchUpcomingMovieList()
            MovieTabs.TAB_TOP_RATED.tabId -> viewModel.fetchTopRatedMovieList()
        }
    }

    override fun onMovieItemClick(movieResultsItem: MovieResultsItem) {
        val intent = Intent(this.context, MovieDetailActivity::class.java)
        intent.putExtra("movie_id", movieResultsItem.id.toString())
        startActivity(intent)
    }

    companion object {
        fun newInstance(fragmentType: Int): MovieViewPagerFragment {
            val bundle = Bundle()
            val commonViewPagerFragment = MovieViewPagerFragment()

            bundle.putInt("fragmentType", fragmentType)
            commonViewPagerFragment.arguments = bundle
            return commonViewPagerFragment
        }
    }
}