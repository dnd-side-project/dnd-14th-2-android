package com.smtm.pickle.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PickleFirebaseMessagingService: FirebaseMessagingService() {

    @Inject
    lateinit var notificationManager: AppNotificationManager

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("PickleFirebaseMessagingService", "Refreshed token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val title = message.data["title"] ?: return
        val body = message.data["body"] ?: ""
        val channelName = message.data["channel"] ?: "VERDICT"
        val channel = getChannel(channelName)

        notificationManager.showNotification(title, body, channel)
    }

    private fun getChannel(channelName: String): AppNotificationChannel {
        return when (channelName) {
            AppNotificationChannel.VERDICT.name -> {
                AppNotificationChannel.VERDICT
            }
            else -> {
                AppNotificationChannel.DEFAULT
            }
        }
    }
}