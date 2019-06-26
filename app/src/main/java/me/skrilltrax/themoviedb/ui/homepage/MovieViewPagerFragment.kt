package me.skrilltrax.themoviedb.ui.homepage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import me.skrilltrax.themoviedb.constants.MovieTabs
import me.skrilltrax.themoviedb.interfaces.OnItemClickListener
import me.skrilltrax.themoviedb.model.movie.lists.MovieResultsItem
import me.skrilltrax.themoviedb.network.api.MovieApiInterface
import me.skrilltrax.themoviedb.ui.BaseFragment
import me.skrilltrax.themoviedb.ui.moviedetail.MovieDetailActivity
import me.skrilltrax.themoviedb.ui.moviedetail.MovieDetailFragment
import me.skrilltrax.themoviedb.ui.search.SearchActivity
import retrofit2.HttpException
import timber.log.Timber

class MovieViewPagerFragment : BaseFragment(), OnItemClickListener {

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
        recyclerView.adapter = MovieAdapter(arrayListOf(), this)
        getMovies(fragmentType ?: 0)
    }

    private fun getMovies(position: Int) {
        movieList = ArrayList()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val movieResponse = when (position) {
                    MovieTabs.TAB_POPULAR.tabId -> MovieApiInterface.getClient().getPopularMovies(BuildConfig.API_KEY)
                    MovieTabs.TAB_PLAYING.tabId -> MovieApiInterface.getClient().getNowPlayingMovies(BuildConfig.API_KEY)
                    MovieTabs.TAB_UPCOMING.tabId -> MovieApiInterface.getClient().getUpcomingMovies(BuildConfig.API_KEY)
                    MovieTabs.TAB_TOP_RATED.tabId -> MovieApiInterface.getClient().getTopRatedMovies(BuildConfig.API_KEY)
                    else -> null
                }
                if (movieResponse != null) {
                    if (movieResponse.isSuccessful) {
                        movieList.addAll(movieResponse.body()?.results as Collection<MovieResultsItem>)
                        withContext(Dispatchers.Main) {
                            recyclerView.adapter = MovieAdapter(movieList, this@MovieViewPagerFragment)
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

    override fun onMovieItemClick(movieResultsItem: MovieResultsItem) {
        val intent = Intent(this.context, MovieDetailActivity::class.java)
        intent.putExtra("movie_id", movieResultsItem.id.toString())
        startActivity(intent)
    }

    companion object {
        fun newInstance(fragmentType: Int): MovieViewPagerFragment {
            val bundle = Bundle()
            val commonViewPagerFragment = MovieViewPagerFragment()

            bundle.putInt("fragmentType", fragmentType)
            commonViewPagerFragment.arguments = bundle
            return commonViewPagerFragment
        }
    }
}