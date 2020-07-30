package me.skrilltrax.themoviedb.utils

import android.view.View
import okhttp3.logging.HttpLoggingInterceptor

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun httpLoggingInterceptor(
    level: HttpLoggingInterceptor.Level,
    block: (message: String) -> Unit
): HttpLoggingInterceptor {
    return HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            block.invoke(message)
        }
    }).apply {
        this.level = level;
    }
}