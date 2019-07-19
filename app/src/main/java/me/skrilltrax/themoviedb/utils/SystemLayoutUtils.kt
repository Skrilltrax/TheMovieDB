package me.skrilltrax.themoviedb.utils

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

object SystemLayoutUtils {

    fun setStatusBarColor(activity: AppCompatActivity, color: Int) {
        activity.window.statusBarColor = color
    }

    fun makeFullScreen(activity: AppCompatActivity) {
        activity.window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    )
    }

    fun makeFullScreenHideNavigation(activity: AppCompatActivity) {
        activity.window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    )
    }

    fun setNavigationBarColor(activity: AppCompatActivity, color: Int) {
        activity.window.navigationBarColor = color
    }

    fun setStatusBarColor(activity: FragmentActivity, color: Int) {
        activity.window.statusBarColor = color
    }

    fun makeFullScreen(activity: FragmentActivity) {
        activity.window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    )
    }

    fun makeFullScreenHideNavigation(activity: FragmentActivity) {
        activity.window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    )
    }

    fun setNavigationBarColor(activity: FragmentActivity, color: Int) {
        activity.window.navigationBarColor = color
    }
}