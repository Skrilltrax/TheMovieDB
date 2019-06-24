package me.skrilltrax.themoviedb.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.adapter.ViewPagerAdapter
import me.skrilltrax.themoviedb.model.movie.lists.MovieResultsItem

class HomeFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewPager: ViewPager
    private lateinit var movieList: ArrayList<MovieResultsItem>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findViews(view)
        setupViewPager()

        val tab1 = tabLayout.getTabAt(0)
        tab1?.select()
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