package me.skrilltrax.themoviedb.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import me.skrilltrax.themoviedb.adapter.ViewPagerAdapter
import me.skrilltrax.themoviedb.constants.MovieTabs
import me.skrilltrax.themoviedb.databinding.FragmentHomeBinding
import timber.log.Timber
import java.lang.ref.WeakReference

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MovieListViewModel by lazy {
        ViewModelProviders.of(activity!!).get(MovieListViewModel::class.java)
    }
    private lateinit var mainActivity: WeakReference<MainActivity>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("Config Changed")
        mainActivity = WeakReference(activity as MainActivity)
        mainActivity.get()?.showLoading()
        setupObservers()
        setupViewPager()
        setupTabLayout()
    }

    private fun setupTabLayout() {
        binding.appBar.tabLayout.apply {
            getTabAt(MovieTabs.TAB_POPULAR.tabId)?.select() //Select first tab of viewpager
            setupWithViewPager(binding.viewPager)
        }
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it == false) {
                if (mainActivity.get()?.dialog?.isShowing == true) {
                    mainActivity.get()?.hideLoading()
                }
                mainActivity.get()?.dialog?.dismiss()
            }
        })
    }

    private fun setupViewPager() {
        binding.viewPager.apply {
            adapter = ViewPagerAdapter(childFragmentManager)
            offscreenPageLimit = 3
        }
    }

    override fun onStop() {
        if (mainActivity.get()?.dialog?.isShowing == true) {
            mainActivity.get()?.hideLoading()
        }
        mainActivity.get()?.dialog?.dismiss()
        super.onStop()
    }

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}