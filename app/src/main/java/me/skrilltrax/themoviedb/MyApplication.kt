package me.skrilltrax.themoviedb

import android.app.Application
import me.skrilltrax.themoviedb.di.appModule
import me.skrilltrax.themoviedb.di.networkModule
import me.skrilltrax.themoviedb.di.repositoryModule
import me.skrilltrax.themoviedb.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
//            androidLogger()
            androidContext(this@MyApplication)
            modules(listOf(appModule, viewModelModule, repositoryModule, networkModule))
        }
    }
}
