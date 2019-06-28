package me.skrilltrax.themoviedb.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import me.skrilltrax.themoviedb.constants.Constants

object BindingAdapters {

    @BindingAdapter("heroImageUrl")
    @JvmStatic
    fun setHeroImage(view: ImageView, url: String?) {
        if (url != null) {
            Glide.with(view.context)
                .load(Constants.POSTER_W500_IMAGE_PATH + url)
                .transform(FitCenter())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }
    }

    @BindingAdapter("posterImageUrl")
    @JvmStatic
    fun setPosterImage(view: ImageView, url: String?) {
        if (url != null) {
            Glide.with(view.context!!)
                .load(Constants.POSTER_W500_IMAGE_PATH + url)
                .transform(RoundedCorners(16))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }
    }
}