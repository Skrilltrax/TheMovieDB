package me.skrilltrax.themoviedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import me.skrilltrax.themoviedb.network.RetrofitInstance
import me.skrilltrax.themoviedb.network.api.MovieApiInterface
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CoroutineScope(Dispatchers.Main).launch {
            MovieApiInterface.getClient().getPopularMovies(BuildConfig.API_KEY)
        }
    }
}

