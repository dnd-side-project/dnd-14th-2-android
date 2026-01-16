package com.smtm.pickle.workers

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FcmTokenWorkerManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun enqueueTokenUpload(token: String) {
        val workRequest = OneTimeWorkRequestBuilder<FcmTokenUploadWorker>()
            .setInputData(
                workDataOf(FcmTokenUploadWorker.KEY_TOKEN to token)
            )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                30,
                TimeUnit.SECONDS
            )
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            WORK_NAME_TOKEN_UPLOAD,
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    companion object {
        private const val WORK_NAME_TOKEN_UPLOAD = "fcm_token_upload"
    }
}