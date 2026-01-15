package com.smtm.pickle.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val notificationManager: NotificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun createNotificationChannels() {
        AppNotificationChannel.entries.forEach { channel ->
            createChannel(channel)
        }
    }

    private fun createChannel(channel: AppNotificationChannel) {
        val notificationChannel = NotificationChannel(
            channel.channelId,
            channel.channelName,
            channel.importance
        ).apply {
            description = channel.description
            setShowBadge(true)
        }

        notificationManager.createNotificationChannel(notificationChannel)
    }

    fun showNotification(title: String, message: String, channel: AppNotificationChannel) {
        val notificationId = System.currentTimeMillis().toInt()
        val notification = NotificationCompat.Builder(context, channel.channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(channel.importance)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}