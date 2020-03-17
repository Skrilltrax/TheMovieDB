package me.skrilltrax.themoviedb.ui.moviedetail

import android.os.Bundle
import androidx.core.content.ContextCompat
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.ui.BaseActivity
import me.skrilltrax.themoviedb.utils.SystemLayoutUtils
import me.skrilltrax.themoviedb.utils.SystemLayoutUtils.setNavigationBarColor
import timber.log.Timber

class MovieDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNavigationBarColor(ContextCompat.getColor(this, R.color.background))
        setContentView(R.layout.activity_movie_detail)
        val movieId = intent.getStringExtra("movie_id") ?: null
        if (movieId != null) {
            Timber.d(movieId)
            if (supportFragmentManager.backStackEntryCount == 0) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.frame, MovieDetailFragment.newInstance(movieId), "FRAGMENT_MOVIE_DETAIL")
                    .commit()
            }
        }
    }

    override fun onPause() {
        if (dialog?.isShowing!!) {
            dialog?.hide()
        }
        dialog?.dismiss()
        super.onPause()
    }
}
