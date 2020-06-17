package me.skrilltrax.themoviedb.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import me.skrilltrax.themoviedb.constants.Tabs
import me.skrilltrax.themoviedb.ui.homepage.tv.TVViewPagerFragment

class TVViewPagerAdapter(private val fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        val tab = Tabs.getTabById(position)
        return if (tab != null) {
            TVViewPagerFragment.newInstance(tab.tabId)
        } else {
            TVViewPagerFragment.newInstance(Tabs.TAB_POPULAR.tabId)
        }
    }

    override fun getItemCount(): Int {
        return 4
    }
}
