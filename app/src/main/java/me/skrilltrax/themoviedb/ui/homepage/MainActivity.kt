package me.skrilltrax.themoviedb.ui.homepage

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.ui.BaseActivity
import me.skrilltrax.themoviedb.utils.SystemLayoutUtils
import me.skrilltrax.themoviedb.utils.SystemLayoutUtils.setNavigationBarColor

class MainActivity : BaseActivity() {
    private lateinit var bottomNav: BottomNavigationView
    private var previousSelection: Int = 0
    val isMovieSelected: MutableLiveData<Boolean> = MutableLiveData(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNavigationBarColor(ContextCompat.getColor(this, R.color.background))
        setContentView(R.layout.activity_main)
        if (supportFragmentManager.backStackEntryCount == 0 && savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frame, HomeFragment.newInstance())
                .commit()
            previousSelection = R.id.movie
        }
        bottomNav = findViewById(R.id.bottomNavigationView)
        bottomNav.setOnNavigationItemSelectedListener {
            if (it.itemId != previousSelection) {
                when (it.itemId) {
                    R.id.movie -> {
                        it.isChecked = true
                        previousSelection = it.itemId
                        isMovieSelected.postValue(true)
                    }
                    R.id.tv -> {
                        it.isChecked = true
                        previousSelection = it.itemId
                        isMovieSelected.postValue(false)
                    }
                    R.id.profile -> {
                        it.isChecked = true
                        previousSelection = it.itemId
                    }
                }
            }
            false
        }
    }

    override fun onPause() {
        if (dialog?.isShowing == true) {
            hideLoading()
            dialog?.dismiss()
        }
        super.onPause()
    }
}
