package me.skrilltrax.themoviedb.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import me.skrilltrax.themoviedb.adapter.ViewPagerAdapter
import me.skrilltrax.themoviedb.constants.Tabs
import me.skrilltrax.themoviedb.databinding.FragmentHomeBinding
import me.skrilltrax.themoviedb.ui.homepage.movie.MovieListViewModel
import me.skrilltrax.themoviedb.ui.homepage.tv.TVListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val movieListViewModel by sharedViewModel<MovieListViewModel>()
    private val tvListViewModel by sharedViewModel<TVListViewModel>()
    private lateinit var mainActivity: MainActivity
    private lateinit var movieAdapter: ViewPagerAdapter
    private lateinit var tvAdapter: ViewPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        movieAdapter = ViewPagerAdapter(this, true)
        tvAdapter = ViewPagerAdapter(this, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = requireActivity() as MainActivity
        mainActivity.showLoading()
        setupObservers()
    }

    private fun init(isMovieSelected: Boolean) {
        setupViewPager(isMovieSelected)
        setupTabLayout()
    }

    private fun setupObservers() {
        movieListViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            Timber.d("isLoading $it")
            if (it == false) {
                if (mainActivity.dialog?.isShowing == true) {
                    mainActivity.hideLoading()
                }
                mainActivity.dialog?.dismiss()
            }
        })
        tvListViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            Timber.d("isLoading $it")
            if (it == false) {
                if (mainActivity.dialog?.isShowing == true) {
                    mainActivity.hideLoading()
                }
                mainActivity.dialog?.dismiss()
            }
        })
        mainActivity.isMovieSelected.observe(viewLifecycleOwner, Observer {
            Timber.d("isMovieSelected: $it")
            init(it)
        })
    }

    private fun setupTabLayout() {
        binding.appBar.tabLayout.getTabAt(Tabs.TAB_POPULAR.tabId)?.select() //Select first tab of viewpager
        TabLayoutMediator(binding.appBar.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Popular"
                1 -> "Now Playing"
                2 -> "Upcoming"
                3 -> "Top Rated"
                else -> null
            }
        }.attach()
    }

    private fun setupViewPager(isMovieSelected: Boolean) {
        binding.viewPager.adapter.let {
            if (it is ViewPagerAdapter) {
                it.clearAll()
            }
        }
        binding.viewPager.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
//            removeAllViews()
            offscreenPageLimit = 3
            adapter = if (isMovieSelected) { movieAdapter } else { tvAdapter }
        }
    }

    override fun onStop() {
        if (mainActivity.dialog?.isShowing == true) {
            mainActivity.hideLoading()
        }
        mainActivity.dialog?.dismiss()
        super.onStop()
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}