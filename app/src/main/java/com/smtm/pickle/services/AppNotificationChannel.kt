package com.smtm.pickle.services

import android.app.NotificationManager

enum class AppNotificationChannel(
    val channelId: String,
    val channelName: String,
    val description: String,
    val importance: Int,
) {
    VERDICT(
        channelId = "verdict_result",
        channelName = "소비 심판 결과",
        description = "소비 심판 결과와 절약 팁을 알려드려요",
        importance = NotificationManager.IMPORTANCE_HIGH
    ),
    DEFAULT(
        channelId = "default",
        channelName = "기본 알림",
        description = "기본 알림",
        importance = NotificationManager.IMPORTANCE_DEFAULT
    )
}