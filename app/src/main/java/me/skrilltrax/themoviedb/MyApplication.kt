package me.skrilltrax.themoviedb

import android.app.Application
import androidx.room.Room
import com.facebook.stetho.Stetho
import me.skrilltrax.themoviedb.di.appModule
import me.skrilltrax.themoviedb.di.networkModule
import me.skrilltrax.themoviedb.di.repositoryModule
import me.skrilltrax.themoviedb.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
//import me.skrilltrax.themoviedb.db.AppDatabase
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(listOf(appModule, viewModelModule, repositoryModule, networkModule))
        }
        Stetho.initializeWithDefaults(this)
        /*val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-name"
        ).build()*/
    }
}