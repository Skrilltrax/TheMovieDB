package me.skrilltrax.themoviedb.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import me.skrilltrax.themoviedb.adapter.ViewPagerAdapter
import me.skrilltrax.themoviedb.constants.MovieTabs
import me.skrilltrax.themoviedb.databinding.FragmentHomeBinding
import me.skrilltrax.themoviedb.ui.BaseFragment

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MovieListViewModel by lazy {
        ViewModelProviders.of(activity!!).get(MovieListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading()
        setupObservers()
        setupViewPager()
        val tab1 = binding.appBar.tabLayout.getTabAt(MovieTabs.TAB_POPULAR.tabId)
        tab1?.select()
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it == false) hideLoading()
        })
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = ViewPagerAdapter(fragmentManager!!)
        binding.viewPager.offscreenPageLimit = 3
        binding.appBar.tabLayout.setupWithViewPager(binding.viewPager)
    }
}