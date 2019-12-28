package me.skrilltrax.themoviedb.ui.homepage.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.adapter.ListAdapter
import me.skrilltrax.themoviedb.constants.Tabs
import me.skrilltrax.themoviedb.databinding.FragmentCommonViewpagerBinding
import me.skrilltrax.themoviedb.interfaces.ListItemClickListener
import me.skrilltrax.themoviedb.model.list.ListResultItem
import me.skrilltrax.themoviedb.ui.moviedetail.MovieDetailActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class MovieViewPagerFragment : Fragment(), ListItemClickListener {

    private val movieListViewModel by sharedViewModel<MovieListViewModel>()

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
    }

    private fun setupObservers(viewLifecycleOwner: LifecycleOwner, position: Int) {
        when (position) {
            Tabs.TAB_POPULAR.tabId -> movieListViewModel.popularMovieList.observe(viewLifecycleOwner, Observer {
                binding.listAdapter = ListAdapter(it, this, true)
            })
            Tabs.TAB_PLAYING.tabId -> movieListViewModel.playingMovieList.observe(viewLifecycleOwner, Observer {
                binding.listAdapter = ListAdapter(it, this, true)
            })
            Tabs.TAB_UPCOMING.tabId -> movieListViewModel.upcomingMovieList.observe(viewLifecycleOwner, Observer {
                binding.listAdapter = ListAdapter(it, this, true)
            })
            Tabs.TAB_TOP_RATED.tabId -> movieListViewModel.topRatedMovieList.observe(viewLifecycleOwner, Observer {
                binding.listAdapter = ListAdapter(it, this, true)
            })
        }
    }

    override fun onItemClick(resultsItem: ListResultItem) {
        val intent = Intent(this.context, MovieDetailActivity::class.java)
        intent.putExtra("movie_id", resultsItem.id.toString())
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
