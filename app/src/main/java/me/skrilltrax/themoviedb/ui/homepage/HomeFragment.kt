package me.skrilltrax.themoviedb.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import me.skrilltrax.themoviedb.adapter.ViewPagerAdapter
import me.skrilltrax.themoviedb.constants.Tabs
import me.skrilltrax.themoviedb.databinding.FragmentHomeBinding
import me.skrilltrax.themoviedb.ui.homepage.movie.MovieListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val movieListViewModel by sharedViewModel<MovieListViewModel>()
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("Config Changed")
        mainActivity = requireActivity() as MainActivity
        mainActivity.showLoading()
        setupViewPager()
        setupTabLayout()
        setupObservers()
    }

    private fun setupTabLayout() {
        binding.appBar.tabLayout.apply {
            getTabAt(Tabs.TAB_POPULAR.tabId)?.select() //Select first tab of viewpager
            setupWithViewPager(binding.viewPager)
        }
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
        mainActivity.isMovieSelected.observe(viewLifecycleOwner, Observer {
            (binding.viewPager.adapter as ViewPagerAdapter).selectAdapterType(it)
        })
    }

    private fun setupViewPager() {
        binding.viewPager.apply {
            adapter = ViewPagerAdapter(childFragmentManager, true)
            offscreenPageLimit = 3
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