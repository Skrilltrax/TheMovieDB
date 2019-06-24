package me.skrilltrax.themoviedb.ui.activity

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.ui.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    )

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

