package me.skrilltrax.themoviedb.ui.homepage.tv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import me.skrilltrax.themoviedb.adapter.TVListAdapter
import me.skrilltrax.themoviedb.constants.Tabs
import me.skrilltrax.themoviedb.databinding.FragmentCommonViewpagerBinding
import me.skrilltrax.themoviedb.interfaces.ListItemClickListener
import me.skrilltrax.themoviedb.model.list.tv.TVListResultItem
import me.skrilltrax.themoviedb.ui.tvdetail.TVDetailActivity
import timber.log.Timber

class TVViewPagerFragment : Fragment(), ListItemClickListener {

    private val tvListViewModel by activityViewModels<TVListViewModel>()

    private lateinit var binding: FragmentCommonViewpagerBinding
    private var fragmentType: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = arguments
        if (args is Bundle) {
            fragmentType = args.getInt("fragmentType", 0)
            Timber.d(fragmentType.toString())
        }
        binding = FragmentCommonViewpagerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers(viewLifecycleOwner, fragmentType ?: 0)
    }

    private fun setupObservers(viewLifecycleOwner: LifecycleOwner, position: Int) {
        when (position) {
            Tabs.TAB_POPULAR.tabId -> tvListViewModel.popularShowsList.observe(viewLifecycleOwner) {
                binding.recyclerView.adapter = TVListAdapter(it, this)
            }
            Tabs.TAB_PLAYING.tabId -> tvListViewModel.playingShowsList.observe(viewLifecycleOwner) {
                binding.recyclerView.adapter = TVListAdapter(it, this)
            }
            Tabs.TAB_UPCOMING.tabId -> tvListViewModel.upcomingShowsList.observe(viewLifecycleOwner) {
                binding.recyclerView.adapter = TVListAdapter(it, this)
            }
            Tabs.TAB_TOP_RATED.tabId -> tvListViewModel.topRatedShowsList.observe(viewLifecycleOwner) {
                binding.recyclerView.adapter = TVListAdapter(it, this)
            }
        }
    }

    override fun onItemClick(resultsItem: TVListResultItem) {
        val intent = Intent(this.context, TVDetailActivity::class.java)
        intent.putExtra("show_id", resultsItem.id.toString())
        startActivity(intent)
    }

    companion object {
        fun newInstance(fragmentType: Int): TVViewPagerFragment {
            return TVViewPagerFragment().apply {
                val bundle = Bundle()
                bundle.putInt("fragmentType", fragmentType)
                arguments = bundle
            }
        }
    }
}
