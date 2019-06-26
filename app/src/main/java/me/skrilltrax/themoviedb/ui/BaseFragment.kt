package me.skrilltrax.themoviedb.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieCompositionFactory
import me.skrilltrax.themoviedb.R

open class BaseFragment : Fragment() {

    var dialog: Dialog? = null

    fun showLoading() {
        if (context != null) {
            dialog = Dialog(context!!)
            if (null != dialog!!.window) {
                dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            dialog!!.setContentView(R.layout.progress_lottie)
            dialog!!.setCanceledOnTouchOutside(false)
            dialog!!.show()
        }

    }

    fun hideLoading() {
        if (dialog?.isShowing!!) {
            dialog?.hide()
        }
    }
}