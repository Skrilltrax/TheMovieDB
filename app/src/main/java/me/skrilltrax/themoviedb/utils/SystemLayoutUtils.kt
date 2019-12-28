package me.skrilltrax.themoviedb.utils

import android.graphics.Color
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import kotlin.math.abs

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

    fun setStatusBarTint(activity: FragmentActivity, rootView: View, customView: View): ViewTreeObserver.OnScrollChangedListener {
        var oldScrollY = 0F
        return ViewTreeObserver.OnScrollChangedListener {
                val scrollY = rootView.scrollY.toFloat()
                if (scrollY <= 0) {
                    setStatusBarColor(activity, Color.TRANSPARENT)
                    oldScrollY = 0F
                } else if (scrollY > 0 && scrollY <= customView.height) {
                    if (abs(scrollY - oldScrollY) > 30) {
                        oldScrollY = scrollY
                        setStatusBarColor(
                            activity,
                            Color.argb(((scrollY / customView.height) * 255).toInt(), 25, 27, 27)
                        )
//                        Timber.d("scrollY : ${((scrollY / binding.movieHeader.root.height) * 255).toInt()}")
                    }
                } else {
                    setStatusBarColor(activity, Color.argb(255, 25, 27, 27))
                    oldScrollY = customView.height.toFloat()
            }
        }
    }
}
