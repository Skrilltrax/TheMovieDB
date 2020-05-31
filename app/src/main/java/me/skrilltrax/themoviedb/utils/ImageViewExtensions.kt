package me.skrilltrax.themoviedb.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.constants.Constants

fun ImageView.setPosterImage(url: String) {
    Glide.with(this.context)
        .load(Constants.POSTER_W500_IMAGE_PATH + url)
        .transform(RoundedCorners(16))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.setHeroImage(url: String) {
    Glide.with(this.context)
        .load(Constants.POSTER_W500_IMAGE_PATH + url)
        .transform(FitCenter())
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.setCreditImage(url: String) {
    Glide.with(this.context)
        .load(Constants.POSTER_W185_IMAGE_PATH + url)
        .transform(MultiTransformation(FitCenter(), RoundedCorners(8)))
        .error(R.drawable.ic_person_outline_black_16dp)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.setThumbnail(movieId: String) {
    Glide.with(this.context)
        .load(YoutubeUtils.getMediumQualityThumbnail(movieId))
        .transform(MultiTransformation(FitCenter(), RoundedCorners(24)))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
