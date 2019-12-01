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
import me.skrilltrax.themoviedb.adapter.ListAdapter
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.interfaces.ListItemClickListener
import me.skrilltrax.themoviedb.model.list.ListResultItem
import me.skrilltrax.themoviedb.network.api.movie.MovieApiInterface
import org.koin.android.ext.android.inject
import retrofit2.HttpException

class SearchActivity : AppCompatActivity(), ListItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var collapsingToolbar: CollapsingToolbarLayout
    private val client: MovieApiInterface by inject()

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
            adapter = ListAdapter(listOf(), this@SearchActivity, true)
        }
    }

    private fun fetchDetails() {
        val movieList: ArrayList<ListResultItem> = ArrayList()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val movieResponse = client.getPopularMovies(BuildConfig.API_KEY)
                if (movieResponse.isSuccessful) {
                    movieList.addAll(movieResponse.body()?.results as Collection<ListResultItem>)
                    withContext(Dispatchers.Main) {
                        recyclerView.adapter =
                            ListAdapter(movieList, this@SearchActivity, true)
                        (recyclerView.adapter as ListAdapter).notifyDataSetChanged()
//                            hideLoading()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        //                            hideLoading()
                        Snackbar.make(recyclerView, "Please check your network connection", Snackbar.LENGTH_SHORT)
                            .show()
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

    override fun onItemClick(resultsItem: ListResultItem) {

    }
}