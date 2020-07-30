package me.skrilltrax.themoviedb.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import me.skrilltrax.themoviedb.adapter.MovieViewPagerAdapter
import me.skrilltrax.themoviedb.adapter.TVViewPagerAdapter
import me.skrilltrax.themoviedb.constants.Tabs
import me.skrilltrax.themoviedb.databinding.FragmentHomeBinding
import me.skrilltrax.themoviedb.ui.homepage.movie.MovieListViewModel
import me.skrilltrax.themoviedb.ui.homepage.tv.TVListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var movieAdapter: MovieViewPagerAdapter
    private lateinit var tvAdapter: TVViewPagerAdapter

    private val movieListViewModel by sharedViewModel<MovieListViewModel>()
    private val tvListViewModel by sharedViewModel<TVListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        movieAdapter = MovieViewPagerAdapter(this)
        tvAdapter = TVViewPagerAdapter(this)
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
            if (it == false) {
                if (mainActivity.dialog?.isShowing == true) {
                    mainActivity.hideLoading()
                }
                mainActivity.dialog?.dismiss()
            }
        })

        tvListViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it == false) {
                if (mainActivity.dialog?.isShowing == true) {
                    mainActivity.hideLoading()
                }
                mainActivity.dialog?.dismiss()
            }
        })

        mainActivity.isMovieSelected.observe(viewLifecycleOwner, Observer {
            init(it)
        })
    }

    private fun setupTabLayout() {
        binding.appBar.tabLayout.getTabAt(Tabs.TAB_POPULAR.tabId)
            ?.select() // Select first tab of viewpager

        TabLayoutMediator(binding.appBar.tabLayout, binding.viewPager) { tab, position ->
            tab.text = Tabs.getTabById(position)!!.tabName
        }.attach()
    }

    private fun setupViewPager(isMovieSelected: Boolean) {
        binding.viewPager.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 3
            adapter = if (isMovieSelected) movieAdapter else tvAdapter
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
