package me.skrilltrax.themoviedb.databinding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.constants.Constants
import me.zhanghai.android.materialratingbar.MaterialRatingBar

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

    @BindingAdapter("creditImageUrl")
    @JvmStatic
    fun setCreditImage(view: ImageView, url: String?) {
        if (url != null) {
            Glide.with(view.context)
                .load(Constants.POSTER_W185_IMAGE_PATH + url)
                .transform(MultiTransformation(FitCenter(), RoundedCorners(8)))
                .error(R.drawable.ic_person_outline_black_16dp)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }
    }

    @BindingAdapter("rating")
    @JvmStatic
    fun setRating(view: MaterialRatingBar, rating: Double) {
        view.rating = rating.toFloat()/2
    }
}