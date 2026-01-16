package com.smtm.pickle.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.smtm.pickle.domain.usecase.fcm.RegisterFcmTokenUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class FcmTokenUploadWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val registerFcmTokenUseCase: RegisterFcmTokenUseCase
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val token = inputData.getString(KEY_TOKEN)

        if (token.isNullOrEmpty()) {
            Timber.e("FCM 토큰이 없습니다")
            return Result.failure()
        }

        return registerFcmTokenUseCase(token = token).fold(
            onSuccess = {
                Timber.d("FCM 토큰 서버 등록 성공")
                Result.success()
            },
            onFailure = { exception ->
                Timber.e(exception, "FCM 토큰 서버 등록 실패")
                if (runAttemptCount < MAX_RETRY_COUNT) {
                    Result.retry()
                } else {
                    Result.failure()
                }
            }
        )
    }

    companion object {
        const val KEY_TOKEN = "fcm_token"
        private const val MAX_RETRY_COUNT = 3
    }
}