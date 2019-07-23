package me.skrilltrax.themoviedb

import android.app.Application
import androidx.room.Room
//import me.skrilltrax.themoviedb.db.AppDatabase
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        /*val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-name"
        ).build()*/
    }
}