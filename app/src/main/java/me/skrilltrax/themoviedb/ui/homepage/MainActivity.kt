package me.skrilltrax.themoviedb.ui.homepage

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.ui.search.SearchActivity

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportFragmentManager.backStackEntryCount == 0) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frame, HomeFragment())
                .commit()
        }

        bottomNav = findViewById(R.id.bottomNavigationView)
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.movie -> {
                    it.isChecked = true
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        it.iconTintList = ContextCompat.getColorStateList(this, R.color.colorAccent)
                    }
                }
                R.id.tv -> {
                    it.isChecked = true
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        it.iconTintList = ContextCompat.getColorStateList(this, R.color.colorGreen)
                    }
                }
                R.id.profile -> {
                    it.isChecked = true
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        it.iconTintList = ContextCompat.getColorStateList(this, R.color.colorBlue)
                    }
                }
            }
            false
        }
    }
}
