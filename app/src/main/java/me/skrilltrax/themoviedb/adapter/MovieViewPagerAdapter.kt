package me.skrilltrax.themoviedb.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import me.skrilltrax.themoviedb.constants.Tabs
import me.skrilltrax.themoviedb.ui.homepage.movie.MovieViewPagerFragment
import me.skrilltrax.themoviedb.ui.homepage.tv.TVViewPagerFragment

class MovieViewPagerAdapter(private val fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        val tab = Tabs.getTabById(position)
        return if (tab != null) {
            MovieViewPagerFragment.newInstance(tab.tabId)
        } else {
            MovieViewPagerFragment.newInstance(Tabs.TAB_POPULAR.tabId)
        }
    }

    override fun getItemCount(): Int {
        return 4
    }
}
