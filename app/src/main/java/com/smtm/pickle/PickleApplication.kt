package com.smtm.pickle

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.smtm.pickle.services.AppNotificationManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class PickleApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var notificationHelper: AppNotificationManager

    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(hiltWorkerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        initTimber()
        notificationHelper.createNotificationChannels()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }
}
