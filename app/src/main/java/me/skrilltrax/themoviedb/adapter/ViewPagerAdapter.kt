package me.skrilltrax.themoviedb.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import me.skrilltrax.themoviedb.constants.Tabs
import me.skrilltrax.themoviedb.ui.homepage.movie.MovieViewPagerFragment
import me.skrilltrax.themoviedb.ui.homepage.tv.TVViewPagerFragment
import timber.log.Timber

class ViewPagerAdapter(private val fragment: Fragment, private var isMovieSelected: Boolean) :
    FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        val tab = Tabs.getTabById(position)
        return if (tab != null) {
            if (isMovieSelected) {
                MovieViewPagerFragment.newInstance(tab.tabId)
            } else {
                TVViewPagerFragment.newInstance(tab.tabId)
            }
        } else {
            if (isMovieSelected) {
                MovieViewPagerFragment.newInstance(Tabs.TAB_POPULAR.tabId)
            } else {
                TVViewPagerFragment.newInstance(Tabs.TAB_POPULAR.tabId)
            }
        }
    }

    override fun getItemCount(): Int {
        return 4
    }

    fun clearAll() {
        for (i in 0 until fragment.childFragmentManager.backStackEntryCount)
            fragment.childFragmentManager.beginTransaction().remove(fragment.childFragmentManager.fragments.last()).commit()
        fragment.childFragmentManager.fragments.clear()
    }
}
