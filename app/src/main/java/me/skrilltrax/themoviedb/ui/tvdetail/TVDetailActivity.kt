package me.skrilltrax.themoviedb.ui.tvdetail

import android.os.Bundle
import androidx.core.content.ContextCompat
import me.skrilltrax.themoviedb.R
import me.skrilltrax.themoviedb.ui.BaseActivity
import me.skrilltrax.themoviedb.utils.SystemLayoutUtils.setNavigationBarColor
import timber.log.Timber

class TVDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNavigationBarColor(ContextCompat.getColor(this, R.color.background))
        setContentView(R.layout.activity_detail)
        val showId = intent.getStringExtra("show_id") ?: null
        if (showId != null) {
            Timber.d(showId)
            if (supportFragmentManager.backStackEntryCount == 0) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.frame, TVDetailFragment.newInstance(showId), "FRAGMENT_SHOW_DETAIL")
                    .commit()
            }
        }
    }

    override fun onPause() {
        if (dialog?.isShowing!!) {
            dialog?.hide()
        }
        dialog?.dismiss()
        super.onPause()
    }
}
