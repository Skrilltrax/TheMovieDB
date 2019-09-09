package me.skrilltrax.themoviedb.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import me.skrilltrax.themoviedb.R

open class BaseActivity : AppCompatActivity() {

    var dialog: Dialog? = null

    fun showLoading() {
        dialog = Dialog(this)
        if (null != dialog!!.window) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog!!.setContentView(R.layout.progress_lottie)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.show()
    }

    fun hideLoading() {
        if (dialog?.isShowing!!) {
            dialog?.hide()
        }
    }
}