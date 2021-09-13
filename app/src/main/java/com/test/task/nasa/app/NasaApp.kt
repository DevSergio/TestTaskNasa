package com.test.task.nasa.app

import android.app.Application
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class NasaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}