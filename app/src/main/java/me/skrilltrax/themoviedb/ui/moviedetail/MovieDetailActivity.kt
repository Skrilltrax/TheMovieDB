package me.skrilltrax.themoviedb.ui.moviedetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.skrilltrax.themoviedb.R
import timber.log.Timber

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movieId = intent.getStringExtra("movie_id") ?: null
        if (movieId != null) {
            Timber.d(movieId)
            if (supportFragmentManager.backStackEntryCount == 0) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.frame, MovieDetailFragment.newInstance(movieId))
                    .commit()
            }
        }
        else {
        }
    }

}