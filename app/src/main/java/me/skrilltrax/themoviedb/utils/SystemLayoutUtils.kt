package me.skrilltrax.themoviedb.utils

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import kotlin.math.abs

object SystemLayoutUtils {

    fun Activity.setStatusBarColor(color: Int) = run {
        window.statusBarColor = color
    }

    fun Activity.makeFullScreen() = run {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    fun Activity.makeFullScreenHideNavigation() = run {
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }

    fun Activity.setNavigationBarColor(color: Int) = run {
        window.navigationBarColor = color
    }

    fun Activity.setStatusBarTint(rootView: View, customView: View): ViewTreeObserver.OnScrollChangedListener {
        var oldScrollY = 0F
        return ViewTreeObserver.OnScrollChangedListener {
                val scrollY = rootView.scrollY.toFloat()
                if (scrollY <= 0) {
                    setStatusBarColor(Color.TRANSPARENT)
                    oldScrollY = 0F
                } else if (scrollY > 0 && scrollY <= customView.height) {
                    if (abs(scrollY - oldScrollY) > 30) {
                        oldScrollY = scrollY
                        setStatusBarColor(Color.argb(((scrollY / customView.height) * 255).toInt(), 25, 27, 27))
                    }
                } else {
                    setStatusBarColor(Color.argb(255, 25, 27, 27))
                    oldScrollY = customView.height.toFloat()
            }
        }
    }

    fun Fragment.setStatusBarColor(color: Int) = run {
        requireActivity().setStatusBarColor(color)
    }

    fun Fragment.makeFullScreen() = run {
        requireActivity().makeFullScreen()
    }

    fun Fragment.makeFullScreenHideNavigation() = run {
        requireActivity().makeFullScreenHideNavigation()
    }

    fun Fragment.setNavigationBarColor(color: Int) = run {
        requireActivity().setNavigationBarColor(color)
    }

    fun Fragment.setStatusBarTint(rootView: View, customView: View): ViewTreeObserver.OnScrollChangedListener {
        return requireActivity().setStatusBarTint(rootView, customView)
    }
}
