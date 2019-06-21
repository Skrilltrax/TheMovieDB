package me.skrilltrax.themoviedb.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.skrilltrax.themoviedb.BuildConfig
import me.skrilltrax.themoviedb.MovieAdapter
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.model.MovieResultsItem
import me.skrilltrax.themoviedb.network.api.MovieApiInterface
import retrofit2.HttpException
import timber.log.Timber

class CommonViewPagerFragment : BaseFragment() {

    private var fragmentType: Int? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieList: ArrayList<MovieResultsItem>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val args = arguments
        if (args != null) {
            fragmentType = args.getInt("fragmentType", 0)
            Timber.d(fragmentType.toString())
        }
        return inflater.inflate(R.layout.fragment_common_viewpager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading()
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = MovieAdapter(arrayListOf())
        getMovies(fragmentType ?: 0)
    }

    private fun getMovies(position: Int) {
        movieList = ArrayList()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val movieResponse = when (position) {
                    0 -> MovieApiInterface.getClient().getPopularMovies(BuildConfig.API_KEY)
                    1 -> MovieApiInterface.getClient().getNowPlayingMovies(BuildConfig.API_KEY)
                    2 -> MovieApiInterface.getClient().getUpcomingMovies(BuildConfig.API_KEY)
                    3 -> MovieApiInterface.getClient().getTopRatedMovies(BuildConfig.API_KEY)
                    else -> null
                }
                if (movieResponse != null) {
                    if (movieResponse.isSuccessful) {
                        movieList.addAll(movieResponse.body()?.results as Collection<MovieResultsItem>)
                        withContext(Dispatchers.Main) {
                            recyclerView.adapter = MovieAdapter(movieList)
                            (recyclerView.adapter as MovieAdapter).notifyDataSetChanged()
                            hideLoading()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            hideLoading()
                            Snackbar.make(recyclerView, "Please check your network connection", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    hideLoading()
                }
                e.printStackTrace()

            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    hideLoading()
                }
                e.printStackTrace()
            }
        }
    }

    companion object {
        fun newInstance(fragmentType: Int): CommonViewPagerFragment {
            val bundle = Bundle()
            bundle.putInt("fragmentType", fragmentType)
            val commonViewPagerFragment = CommonViewPagerFragment()

            commonViewPagerFragment.arguments = bundle
            return commonViewPagerFragment
        }
    }
}