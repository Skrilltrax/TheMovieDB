package me.skrilltrax.themoviedb.utils

import me.zhanghai.android.materialratingbar.MaterialRatingBar

fun MaterialRatingBar.setRating(rating: Double) {
    this.rating = rating.toFloat() / 2
}