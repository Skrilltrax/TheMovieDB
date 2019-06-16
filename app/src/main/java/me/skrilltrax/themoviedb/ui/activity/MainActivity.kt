package me.skrilltrax.themoviedb.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.ui.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*CoroutineScope(Dispatchers.Main).launch {
            MovieApiInterface.getClient().getPopularMovies(BuildConfig.API_KEY)
        }*/
        if (supportFragmentManager.backStackEntryCount == 0) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frame, HomeFragment())
                .commit()
        }
    }
}

