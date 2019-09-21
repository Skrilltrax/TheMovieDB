package me.skrilltrax.themoviedb.ui.moviedetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.ui.BaseActivity
import timber.log.Timber

class MovieDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        else {
        }
    }

    override fun onPause() {
        if (dialog?.isShowing!!) {
            dialog?.hide()
        }
        dialog?.dismiss()
        super.onPause()
    }

    override fun onStop() {
        if (dialog?.isShowing!!) {
            dialog?.hide()
        }
        dialog?.dismiss()
        super.onStop()
    }
}