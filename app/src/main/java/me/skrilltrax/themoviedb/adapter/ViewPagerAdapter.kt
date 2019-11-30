package me.skrilltrax.themoviedb.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import me.skrilltrax.themoviedb.constants.Tabs
import me.skrilltrax.themoviedb.ui.homepage.movie.MovieViewPagerFragment
import timber.log.Timber

class ViewPagerAdapter(fragmentManager: FragmentManager, private var isMovieSelected: Boolean) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        val movieTab = Tabs.getTabById(position)
        return if (movieTab != null) {
            Timber.d("Tab ID : ${movieTab.tabId}")
            MovieViewPagerFragment.newInstance(movieTab.tabId)
        } else {
            MovieViewPagerFragment.newInstance(Tabs.TAB_POPULAR.tabId)
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Popular"
            1 -> "Now Playing"
            2 -> "Upcoming"
            3 -> "Top Rated"
            else -> null
        }
    }

    fun selectAdapterType(bool: Boolean) {
        isMovieSelected = bool
    }
}
