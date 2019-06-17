package me.skrilltrax.themoviedb.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.skrilltrax.themoviedb.BuildConfig
import me.skrilltrax.themoviedb.MovieAdapter
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.model.MovieResultsItem
import me.skrilltrax.themoviedb.network.api.MovieApiInterface

class HomeFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieList: ArrayList<MovieResultsItem>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
            adapter = MovieAdapter(listOf())
        }
        tabLayout = view.findViewById(R.id.tabLayout)
        /*tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        movieList = ArrayList()
                        CoroutineScope(Dispatchers.IO).launch {
                            val movieDataItem = MovieApiInterface.getClient().getPopularMovies(BuildConfig.API_KEY)
                            movieList.addAll(movieDataItem.results as Collection<MovieResultsItem>)
                            withContext(Dispatchers.Main) {
                                recyclerView.adapter = MovieAdapter(movieList)
                                (recyclerView.adapter as MovieAdapter).notifyDataSetChanged()
                            }
                        }
                    }
                    1 -> {

                    }
                    2 -> {

                    }
                    3 -> {

                    }
                    4 -> {

                    }
                }
            }

        })*/
    }
}