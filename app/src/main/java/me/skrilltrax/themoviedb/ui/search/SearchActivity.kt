package me.skrilltrax.themoviedb.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
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
import retrofit2.HttpException

class SearchActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var collapsingToolbar: CollapsingToolbarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        findViews()
//        collapsingToolbar.title = "Comedy"
        setupRecyclerView()
        fetchDetails()
    }

    private fun findViews() {
        recyclerView = findViewById(R.id.search_recycler_view)
        collapsingToolbar = findViewById(R.id.search_collapsing_toolbar)
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity, RecyclerView.VERTICAL, false)
            adapter = MovieAdapter(listOf(), this@SearchActivity)
        }
    }

    private fun fetchDetails() {
        val movieList: ArrayList<MovieResultsItem> = ArrayList()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val movieResponse = MovieApiInterface.getClient().getPopularMovies(BuildConfig.API_KEY)
                if (movieResponse != null) {
                    if (movieResponse.isSuccessful) {
                        movieList.addAll(movieResponse.body()?.results as Collection<MovieResultsItem>)
                        withContext(Dispatchers.Main) {
                            recyclerView.adapter = MovieAdapter(movieList, this@SearchActivity)
                            (recyclerView.adapter as MovieAdapter).notifyDataSetChanged()
//                            hideLoading()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            //                            hideLoading()
                            Snackbar.make(recyclerView, "Please check your network connection", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    //                    hideLoading()
                }
                e.printStackTrace()

            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    //                    hideLoading()
                }
                e.printStackTrace()
            }
        }
    }

    override fun onMovieItemClick(movieResultsItem: MovieResultsItem) {

    }
}