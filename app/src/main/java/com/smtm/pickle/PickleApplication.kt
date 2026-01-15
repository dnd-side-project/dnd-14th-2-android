package com.smtm.pickle

import android.app.Application
import com.smtm.pickle.services.AppNotificationManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class PickleApplication : Application() {

    @Inject
    lateinit var notificationHelper: AppNotificationManager

    override fun onCreate() {
        super.onCreate()

        notificationHelper.createNotificationChannels()
    }
}
