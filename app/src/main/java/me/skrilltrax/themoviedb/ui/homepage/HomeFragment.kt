package me.skrilltrax.themoviedb.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateVMFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.adapter.ViewPagerAdapter
import me.skrilltrax.themoviedb.constants.MovieTabs
import me.skrilltrax.themoviedb.model.movie.lists.MovieResultsItem
import me.skrilltrax.themoviedb.ui.BaseFragment

class HomeFragment : BaseFragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private val viewModel: MovieListViewModel by lazy {
        ViewModelProviders.of(activity!!).get(MovieListViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading()
        setupObservers()
        findViews(view)
        setupViewPager()
        val tab1 = tabLayout.getTabAt(MovieTabs.TAB_POPULAR.tabId)
        tab1?.select()
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it == false) hideLoading()
        })
    }


    private fun findViews(view: View) {
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager)
    }

    private fun setupViewPager() {
        viewPager.adapter = ViewPagerAdapter(fragmentManager!!)
        viewPager.offscreenPageLimit = 3
        tabLayout.setupWithViewPager(viewPager)
    }
}