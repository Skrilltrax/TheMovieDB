package me.skrilltrax.themoviedb.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import me.skrilltrax.themoviedb.ui.fragment.CommonViewPagerFragment

class ViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CommonViewPagerFragment.newInstance(0)
            1 -> CommonViewPagerFragment.newInstance(1)
            2 -> CommonViewPagerFragment.newInstance(2)
            3 -> CommonViewPagerFragment.newInstance(3)
            4 -> CommonViewPagerFragment.newInstance(4)
            else -> CommonViewPagerFragment.newInstance(0)
        }
    }

    override fun getCount(): Int {
        return 5
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Latest"
            1 -> "Popular"
            2 -> "Playing"
            3 -> "Upcoming"
            4 -> "Top Rated"
            else -> null
        }
    }
}
