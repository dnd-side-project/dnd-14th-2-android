package com.smtm.pickle.services

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.smtm.pickle.workers.FcmTokenWorkerManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class PickleFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationManager: AppNotificationManager

    @Inject
    lateinit var fcmTokenWorkManager: FcmTokenWorkerManager

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("FCM 토큰 갱신: $token")
        fcmTokenWorkManager.enqueueTokenUpload(token)
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