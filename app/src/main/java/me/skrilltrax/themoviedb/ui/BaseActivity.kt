package me.skrilltrax.themoviedb.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import me.skrilltrax.themoviedb.R

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    var dialog: Dialog? = null

    fun showLoading() {
        dialog = Dialog(this)
        dialog?.let {
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setContentView(R.layout.progress_lottie)
            it.setCanceledOnTouchOutside(false)
            it.show()
        }
    }

    fun hideLoading() {
        dialog?.let {
            if (it.isShowing) it.hide()
        }
    }
}
