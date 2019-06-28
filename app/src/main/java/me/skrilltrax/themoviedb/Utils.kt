package me.skrilltrax.themoviedb

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

object Utils {

    fun setStatusBarColor(activity: AppCompatActivity, color: Int) {
        activity.window.statusBarColor = color
    }

    fun makeFullScreen(activity: AppCompatActivity) {
        activity.window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    )
    }

    fun setStatusBarColor(activity: FragmentActivity, color: Int) {
        activity.window.statusBarColor = color
    }

    fun makeFullScreen(activity: FragmentActivity) {
        activity.window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    )
    }
}